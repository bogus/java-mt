package net.bogus.tokenbucket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

// source: http://crackingjavainterviews.blogspot.com/2013/06/what-do-you-understand-by-token-bucket.html

public final class TokenBuckets {
	
	public static TokenBucket newFixedIntervalRefill(long capacityTokens, long refillTokens, long period, TimeUnit unit)
    {
        RefillStrategy strategy = new FixedIntervalRefillStrategy(refillTokens, period, unit);
        return new TokenBucket(strategy, capacityTokens);
    }

}
