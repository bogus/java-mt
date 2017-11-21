package net.bogus.callable;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main2 {
	
	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		for (int i = 0; i < 100; i++) {
			final Future<String> future = executorService.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					int val = new Random().nextInt(3000);
					Thread.sleep(val);
					return "test " + val;
				}
			});

			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println(future.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
			
		}
		
				
		executorService.shutdown();
		
	}
	
}
