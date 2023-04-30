package ru.skqwk.zettedelebackend.sync.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skqwk.zettedelebackend.sync.VectorVersionRepo;
import ru.skqwk.zettedelebackend.sync.VectorVersionService;
import ru.skqwk.zettedelebackend.sync.clock.HLC;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.sync.domain.VectorVersion;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Реализация сервиса синхронизации
 */
@Service
@RequiredArgsConstructor
public class VectorVersionServiceImpl implements VectorVersionService {
    private final VectorVersionRepo vectorVersionRepo;

    @Override
    public Map<UUID, HybridTimestamp> diffVectorVersion(Map<UUID, HybridTimestamp> local, Map<UUID, HybridTimestamp> remote) {
        Map<UUID, HybridTimestamp> diff = new HashMap<>();

        Stream<UUID> nodeIds = Stream.concat(
                local.keySet().stream(),
                remote.keySet().stream());

        nodeIds.forEach(nodeId -> {
            if (local.containsKey(nodeId)) {
                HybridTimestamp localTimestamp = local.get(nodeId);
                if (remote.containsKey(nodeId)) {
                    if (remote.get(nodeId).happenedBefore(localTimestamp)) {
                        diff.put(nodeId, remote.get(nodeId));
                    }
                } else {
                    diff.put(nodeId, new HybridTimestamp(0, 0, nodeId.toString()));
                }
            }
        });

        return diff;
    }

    @Override
    public Map<UUID, HybridTimestamp> mergeVectorVersion(Map<UUID, HybridTimestamp> local, Map<UUID, HybridTimestamp> remote) {
        Map<UUID, HybridTimestamp> merge = new HashMap<>();

        Stream<UUID> nodeIds = Stream.concat(
                local.keySet().stream(),
                remote.keySet().stream());

        nodeIds.forEach(nodeId -> {
            HybridTimestamp localTimestamp = local.get(nodeId);
            HybridTimestamp remoteTimestamp = remote.get(nodeId);
            merge.put(nodeId, HLC.max(localTimestamp, remoteTimestamp));
        });

        return merge;
    }

    @Override
    public VectorVersion findVectorVersionByUser(UserAccount userAccount) {
        return vectorVersionRepo
                .findByUser(userAccount)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Not found version vector for user = %s", userAccount.getId())));
    }

    @Override
    public void updateVectorVersion(VectorVersion vectorVersion, Map<UUID, HybridTimestamp> newVector) {
        vectorVersion.setVector(toString(newVector));
        vectorVersionRepo.save(vectorVersion);
    }

    private Map<String, String> toString(Map<UUID, HybridTimestamp> newVector) {
        return newVector.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> e.getValue().toString()
                ));
    }

    /**
     * Создание вектора версий для пользователя при первой инициализации
     * <p>
     * Здесь допускаем, что БД одна у всех серверов и в таком случае,
     * можно считать, что узловым идентификатором серверной метки времени
     * будет userId, сохраненный в БД
     *
     * @param user пользователь, для которого создается вектор версий
     */
    @Override
    public VectorVersion createNewVector(UserAccount user) {
        HybridTimestamp serverTimestamp =
                new HybridTimestamp(System.currentTimeMillis(), 0, user.getId().toString());

        VectorVersion newVectorVersion = VectorVersion.builder()
                .vector(Collections.singletonMap(user.getId().toString(), serverTimestamp.toString()))
                .user(user)
                .build();

        return vectorVersionRepo.save(newVectorVersion);
    }
}
