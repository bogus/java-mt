package net.bogus.scheduler;

public class SchedulerMain {

	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();
		scheduler.schedule(5000, () -> { System.out.println("task 1"); });
		scheduler.schedule(1000, () -> { System.out.println("task 2"); });
		scheduler.schedule(8000, () -> { System.out.println("task 3"); });
		scheduler.schedule(8000, () -> { System.out.println("task 3.5"); });
		scheduler.schedule(2500, () -> { System.out.println("task 4"); });
	}
	
}
