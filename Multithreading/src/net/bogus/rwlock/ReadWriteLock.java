package net.bogus.rwlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// https://stackoverflow.com/questions/5667793/how-does-a-read-write-mutex-lock-work

public class ReadWriteLock {

	volatile int readAcquires = 0;
	volatile boolean writer = false;
	ReentrantLock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	
	void readLock() throws InterruptedException {
		lock.lock();
		try {
			while(writer) {
				condition.await();
			}
			readAcquires++;
		} finally {
			lock.unlock();
		}
	}
	
	void readUnlock() {
		lock.lock();
		try {
			readAcquires--;
			if (readAcquires == 0) {
				condition.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}
	
	void writeLock() throws InterruptedException {
		lock.lock();
		try {
			while(writer) {
				condition.await();
			}
			writer = true;
			while (readAcquires != 0) {
				condition.await();
			}
		} finally {
			lock.unlock();
		}
	}
	
	void writeUnlock() {
		writer = false;
		condition.signalAll();
	}
}
