/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }
    
    @Test
    public void testFlowable() throws InterruptedException {
    		Flowable.just("Hello World").subscribe(System.out::println);
    		
    		Flowable.fromCallable(() -> {
    		    Thread.sleep(1000); //  imitate expensive computation
    		    return "Done";
    		})
    		  .subscribeOn(Schedulers.io())
    		  .observeOn(Schedulers.single())
    		  .subscribe(System.out::println, Throwable::printStackTrace);

    		Thread.sleep(1200);
    		
    		
    		// Map
    		Flowable.fromArray(1,2,3,4,5,6)
    		.map(val -> "MAP Val: " + val).subscribe(new Consumer<String>() {

				@Override
				public void accept(String t) throws Exception {
					System.out.println(t);
				}
    			    			
		});
    		
    		
    		// FlatMap
    		Flowable.range(1, 20)
    		.flatMap(val -> 
    					Flowable.just(val).subscribeOn(Schedulers.computation()).map(v -> "FLATMAP Val: " + val)
    				).subscribe(new Consumer<String>() {

				@Override
				public void accept(String t) throws Exception {
					System.out.println(t);
				}
    			    			
		});
    }
    
    static int F1 = 1;
	static int F3 = 101;
    
    @Test
    public void testMerge() throws InterruptedException {
    		// Map
		Flowable<Long> f1 = Flowable.interval(1, TimeUnit.SECONDS);
		Flowable<String> f2 = Flowable.fromArray("A", "B", "C", "D", "E", "F", "G").subscribeOn(Schedulers.io());
		Flowable<Long> f3 = Flowable.interval(750, TimeUnit.MILLISECONDS).map(id -> 100*id);
		
		
		Flowable.merge(f1, f3).subscribeOn(Schedulers.newThread()).subscribe(System.out::println);
		
		Thread.sleep(5000);
		
		Observable.merge(
	            Observable.interval(1, TimeUnit.SECONDS).map(id -> "A" + id).take(4),
	            Observable.interval(1, TimeUnit.SECONDS).map(id -> "B" + id).take(4))
	    .subscribe(System.out::println);
		
		Thread.sleep(5000);
		
		Observable.concat(
	            Observable.interval(1, TimeUnit.SECONDS).map(id -> "A" + id).take(4),
	            Observable.interval(1, TimeUnit.SECONDS).map(id -> "B" + id).take(4))
	    .subscribe(System.out::println);
		
		Thread.sleep(5000);
		
		Flowable.zip(f1, f3, new BiFunction<Long, Long, Long>() {

			@Override
			public Long apply(Long t1, Long t2) throws Exception {
				return t1 + t2;
			}
		
		}).subscribe(System.out::println);
		
		Thread.sleep(5000);
		
    }
    
    @Test
    public void testSwitch() throws InterruptedException {
    		
    		Flowable<Long> f1 = Flowable.interval(750, TimeUnit.MILLISECONDS);
		Flowable<Long> f3 = Flowable.interval(2100, TimeUnit.MILLISECONDS).map(id -> 100*id);
    	
		Flowable.switchOnNext(Flowable.just(f1, f3)).subscribe(System.out::println);
		
		Thread.sleep(5000);
    
    }
    
    @Test
    public void testThreads() throws InterruptedException {
    		Observable.just("")
    	        .subscribeOn(Schedulers.computation())
    	        .doOnNext (new Consumer<String>() {

					@Override
					public void accept(String t) throws Exception {
						System.out.println("[1] Which thread? -> " + Thread.currentThread().getName());
						
					}
				}) 
    	        .doOnNext (new Consumer<String>() {

					@Override
					public void accept(String t) throws Exception {
						System.out.println("[2] Which thread? -> " + Thread.currentThread().getName());  
					}
    			})
    	        .subscribeOn(Schedulers.computation())
    	        .observeOn(Schedulers.io())
    	        .doOnNext (new Consumer<String>() {

					@Override
					public void accept(String t) throws Exception {
						System.out.println("[3] Which thread? -> " + Thread.currentThread().getName());  
					}
    	        })
    	        .debounce(400L,TimeUnit.MILLISECONDS)
    	        .subscribe(new Consumer<String>() {

					@Override
					public void accept(String t) throws Exception {
						System.out.println("[4] Which thread? -> " + Thread.currentThread().getName());
						
					}
    	        		
				});
    		
    		Thread.sleep(5000);

    }
    
}
