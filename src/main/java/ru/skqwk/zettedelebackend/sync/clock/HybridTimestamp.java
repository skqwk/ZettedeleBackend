package ru.skqwk.zettedelebackend.sync.clock;

import ru.skqwk.zettedelebackend.util.StringUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;

/**
 * Реализация гибридной метки времени
 */
public class HybridTimestamp implements Comparable<HybridTimestamp>, Serializable {
    private final long wallClockTime;
    private final int ticks;
    private final String nodeId;
    private static final int SHIFT = 16;
    private static final int MAX_COUNTER = 0xFFFF;

    public HybridTimestamp(long wallClockTime,
                           int ticks,
                           String nodeId) {
        this.wallClockTime = wallClockTime;
        this.ticks = ticks;
        this.nodeId = nodeId;
    }

    public static HybridTimestamp parse(String timestamp) {
        int counterDash = timestamp.indexOf('-', timestamp.lastIndexOf(':'));
        int nodeIdDash = timestamp.indexOf('-', counterDash + 1);

        long wallClockTime = Instant.parse(timestamp.substring(0, counterDash)).toEpochMilli();
        int ticks = Integer.parseInt(timestamp.substring(counterDash + 1, nodeIdDash),16);
        String nodeId = timestamp.substring(nodeIdDash + 1);

        return new HybridTimestamp(wallClockTime, ticks, nodeId);
    }

    public static HybridTimestamp fromLogicalTime(long logicalTime, String nodeId) {
        return new HybridTimestamp(logicalTime >> SHIFT,
                BigInteger.valueOf(logicalTime & MAX_COUNTER).intValue() & SHIFT, nodeId);
    }

    @Override
    public String toString() {

        return String.format("%s-%s-%s",
                Instant.ofEpochMilli(wallClockTime).toString(),
                StringUtils.leftPad(Integer.toString(ticks, 16), 4, "0"),
                nodeId
        );
    }

    @Override
    public int compareTo(HybridTimestamp other) {
        if (Objects.isNull(other)) {
            return 1;
        }
        if (this.wallClockTime == other.getWallClockTime()) {
            if (this.ticks == other.ticks) {
                return this.nodeId.compareTo(other.nodeId);
            } else {
                return Integer.compare(this.ticks, other.ticks);
            }
        } else {
            return Long.compare(this.wallClockTime, other.wallClockTime);
        }
    }

    public long getWallClockTime() {
        return wallClockTime;
    }

    public String getNodeId() {return nodeId;}

    public HybridTimestamp addTicks(int ticks) {
        return new HybridTimestamp(
                wallClockTime,
                this.ticks + ticks,
                nodeId);
    }

    public HybridTimestamp max(HybridTimestamp other) {
        if (this.wallClockTime == other.wallClockTime) {
            return this.ticks > other.ticks ? this : other;
        }
        return (this.wallClockTime > other.wallClockTime) ? this : other;
    }

    public boolean happenedBefore(HybridTimestamp other) {
        return this.compareTo(other) < 0;
    }


    // инициализируем с -1, так что addTicks() вернет к 0
    public static HybridTimestamp fromSystemTime(Long systemTime, String nodeId) {
        return new HybridTimestamp(systemTime, -1, nodeId);
    }

    public long compact() {
        return wallClockTime << SHIFT + ticks;
    }

    public int getTicks() {
        return ticks;
    }
}
