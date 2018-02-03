package net.bogus.rwlock;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<>();
		//ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		//BasicReadWriteLock lock = new BasicReadWriteLock();
		ReadWriteLock lock = new ReadWriteLock();
		
		Thread t1 = new Thread(() -> {
		
			for (int i = 0; i < 10; i++) {
				try {
					lock.writeLock();
					list.add(i);
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					lock.writeUnlock();
				}
			}
			
		});
		
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					lock.readLock();
					System.out.print("I =");
					for (Integer val : list) {
						System.out.print(" " + val);
					}
					System.out.println(" ");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					lock.readUnlock();
				}
			}
		});
		
		t2.start();
		t1.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
