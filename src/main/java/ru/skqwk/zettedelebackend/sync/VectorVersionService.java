package ru.skqwk.zettedelebackend.sync;

import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.sync.domain.VectorVersion;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.Map;
import java.util.UUID;

/**
 * Сервис для синхронизации
 */
public interface VectorVersionService {
    /**
     * Рассчитывает вектор разницы
     * @param local вектор, хранящийся на сервере
     * @param remote вектор, пришедший от клиента
     * @return вектор разницы
     */
    Map<UUID, HybridTimestamp> diffVectorVersion(Map<UUID, HybridTimestamp> local, Map<UUID, HybridTimestamp> remote);

    /**
     * Рассчитывает вектор слияния
     * @param local вектор, хранящийся на сервере
     * @param remote вектор, пришедший от клиента
     * @return вектор слияния
     */
    Map<UUID, HybridTimestamp> mergeVectorVersion(Map<UUID, HybridTimestamp> local, Map<UUID, HybridTimestamp> remote);

    VectorVersion findVectorVersionByUser(UserAccount userAccount);

    void updateVectorVersion(VectorVersion vectorVersion, Map<UUID, HybridTimestamp> newVector);

    VectorVersion createNewVector(UserAccount userAccount);
}
