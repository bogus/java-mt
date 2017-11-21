package net.bogus.scheduler;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduler {

	private Data currentData;
	private Thread thread;
	
	PriorityBlockingQueue<Data> queue = new PriorityBlockingQueue<Data>(5, new Comparator<Data>() {
		@Override
		public int compare(Data d1, Data d2) {
			if (d2.getTime() > d1.getTime()) {
				return -1;
			} else if (d2.getTime() == d1.getTime()) {
				return 0;
			}
			return 1;
		}
		
	});
	
	public Scheduler() {
		super();
		thread = new Thread(() -> {
			while (true) {
				try {
					currentData = queue.take();
					long timeToSleep = currentData.getTime() - System.currentTimeMillis();
					if (timeToSleep > 0) {
						Thread.sleep(timeToSleep);
					}
					currentData.getTask().run();
				} catch (InterruptedException e) {
					// Interrupted - meaning we have some other task earlier
					queue.put(currentData);
				}
			}
		});
		thread.start();
	}

	public void schedule(long timeout, Runnable task) {
		Data data = new Data(timeout+System.currentTimeMillis(), task);
		queue.put(data);
		Data peekData = queue.peek();
		if (peekData != currentData) {
			thread.interrupt();
		}
	}
}
