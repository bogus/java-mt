package net.bogus.blockingqueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> implements java.util.concurrent.BlockingQueue<E> {

	private Queue<E> queue;
	private ReentrantLock readLock;
	private ReentrantLock writeLock;
	private Condition pollCondition;
	private Condition offerCondition;
	
	public BlockingQueue() {
		super();
		queue = new LinkedList<E>();
		readLock = new ReentrantLock();
		writeLock = new ReentrantLock();
		readLock.lock();
	}

	@Override
	public E remove() {
		return queue.remove();
	}

	@Override
	public E poll() {
		return queue.poll();
	}

	@Override
	public E element() {
		return queue.element();
	}

	@Override
	public E peek() {
		return queue.peek();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return queue.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return queue.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return queue.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return queue.retainAll(c);
	}

	@Override
	public void clear() {
		queue.clear();
	}

	@Override
	public boolean add(E e) {
		return queue.add(e);
	}

	@Override
	public boolean offer(E e) {
		return queue.offer(e);
	}

	@Override
	public void put(E e) throws InterruptedException {
		try {
			queue.offer(e);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {		
		return false;
	}

	@Override
	public E take() throws InterruptedException {
		if (queue.isEmpty()) {
			readLock.lock();
		}
		try {
			return queue.poll();
		} finally {
			if (queue.isEmpty()) {
				readLock.lock();
			}
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		if (queue.isEmpty()) {
			pollCondition = readLock.newCondition();
			pollCondition.await(timeout, unit);
		}
		return queue.poll();
	}

	@Override
	public int remainingCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		// TODO Auto-generated method stub
		return 0;
	}

}
