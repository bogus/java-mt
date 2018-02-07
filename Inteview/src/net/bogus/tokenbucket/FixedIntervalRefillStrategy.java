package net.bogus.tokenbucket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FixedIntervalRefillStrategy implements RefillStrategy {
	private final long numTokens;
	private final long intervalInMillis;
	private AtomicLong nextRefillTime;
	
	public FixedIntervalRefillStrategy(long numTokens, long interval, TimeUnit unit) {
		super();
		this.numTokens = numTokens;
		this.intervalInMillis = unit.toMillis(interval);
		this.nextRefillTime = new AtomicLong(-1L);
	}

	@Override
	public long refill() {
		final long now = System.currentTimeMillis();
        final long refillTime = nextRefillTime.get();
        
        if (now < refillTime) {
            return 0;
        }

        return nextRefillTime.compareAndSet(refillTime, now + intervalInMillis) ? numTokens : 0;
	}

	@Override
	public long getIntervalInMillis() {
		return intervalInMillis;
	}
	
	
}
