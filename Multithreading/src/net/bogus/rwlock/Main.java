package net.bogus.rwlock;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList();
		ReadWriteLock lock = new ReadWriteLock();
		
		Thread t1 = new Thread(() -> {
		
			for (int i = 0; i < 10; i++) {
				try {
					lock.writeLock();
					list.add(i);
					Thread.sleep(1000);
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
					for (Integer val : list) {
						System.out.println("I = " + val);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					lock.readUnlock();
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
