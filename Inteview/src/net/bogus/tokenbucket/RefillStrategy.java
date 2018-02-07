package net.bogus.tokenbucket;

public interface RefillStrategy {
    long refill();
    long getIntervalInMillis();
}
