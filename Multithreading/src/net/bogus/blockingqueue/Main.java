package net.bogus.blockingqueue;

public class Main {
	
	public static void main(String[] args) {
		
		BlockingQueue<Integer> queue = new BlockingQueue<Integer>();
		
		Thread t1 = new Thread(() -> {
		
			for (int i = 0; i < 10; i++) {
				try {
					queue.put(i);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					System.out.println(queue.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
