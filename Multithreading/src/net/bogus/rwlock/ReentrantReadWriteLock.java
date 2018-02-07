package net.bogus.rwlock;

import java.util.HashMap;

public class ReentrantReadWriteLock {

	int writers = 0;
	int writerRequests = 0;
	HashMap<Thread, Integer> readerThreads = new HashMap<>();
	Thread writerThread = null;
	
	public boolean canGrantReadAccess(Thread thread) {
		if (isWriter(thread)) {
			return true;
		}
		if (hasWriter()) {
			return false;
		}
		if (isReader(thread)) {
			return true;
		}
		if (hasWriterRequest()) {
			return false;
		}
		return true;
	}
	
	public boolean canGrantWriteAccess(Thread thread) {
		if (isOnlyReader(thread)) {
			return true;
		}
		if (hasReaders()) {
			return false;
		}
		if (!hasWriter()) {
			return true;
		}
		if (!isWriter(thread)) {
			return false;
		}
		return true;
	}
	
	private boolean hasWriterRequest() {
		return writerRequests > 0;
	}
	
	private boolean hasReaders() {
	    return readerThreads.size() > 0;
	}
	
	public boolean isReader(Thread thread) {
		return readerThreads.get(thread) != null;
	}
	
	public boolean isWriter(Thread thread) {
		return thread != null && thread == writerThread;
	}
	
	public boolean hasWriter() {
		return writerThread != null;
	}
	
	public Integer getReadAccessCount(Thread thread) {
		return readerThreads.getOrDefault(thread, 0);
	}
	
	public boolean isOnlyReader(Thread thread) {
		return readerThreads.size() == 1 && readerThreads.get(thread) != null;
	}
	
	public synchronized void readLock() throws InterruptedException {
		
		Thread currentThread = Thread.currentThread();
		while (!canGrantReadAccess(currentThread)) {
			wait();
		}
		
		readerThreads.put(currentThread, getReadAccessCount(currentThread) + 1);
		
	}
	
	public synchronized void readUnlock() {
		Thread callingThread = Thread.currentThread();
		if (!isReader(callingThread)) {
			// Can't unlock from a different thread
			throw new IllegalMonitorStateException();
		}
	    int accessCount = getReadAccessCount(callingThread);
	    if(accessCount == 1){ readerThreads.remove(callingThread); }
	    else { readerThreads.put(callingThread, (accessCount -1)); }
	    notifyAll();
	}
	
	public synchronized void writeLock() throws InterruptedException {
		writerRequests++;
		while (!canGrantWriteAccess(Thread.currentThread())) {
			wait();
		}
		writerRequests--;
		writers++;
		writerThread = Thread.currentThread();
	}
	
	public synchronized void writeUnlock() {
		if (!isWriter(Thread.currentThread())) {
			throw new IllegalMonitorStateException();
		}
		if(--writers == 0) {
			writerThread = null;
		}
		notifyAll();
	}
}
