package net.bogus.scheduler;

public class Data {
	public long time;
	public Runnable task;

	public Data(long time, Runnable task) {
		super();
		this.time = time;
		this.task = task;
	}

	public long getTime() {
		return time;
	}

	public Runnable getTask() {
		return task;
	}
	
}