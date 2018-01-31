package net.bogus.blockingqueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {

	private Queue<T> queue = new LinkedList<>();
	private int capacity;
	
	public BlockingQueue(int capacity) {
		super();
		this.capacity = capacity;
	}
	
	public synchronized void put(T element) throws InterruptedException {
		while(queue.size() == capacity) {
			wait();
		}
		
		queue.offer(element);
		notify();
	}
	
	public synchronized T take() throws InterruptedException {
		while(queue.isEmpty()) {
			wait();
		}
		
		T element = queue.poll();
		notify();
		return element;
	}
}
