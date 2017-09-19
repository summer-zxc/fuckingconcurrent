package taskexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExectorDemo {

	public void fixPool() {
		ExecutorService es = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(index);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		es.shutdown();
	}
// ThreadFactory threadFactory 这个是产生线程来处理进来的runnale
	public void cachePool() {
		// ExecutorService es = new ThreadPoolExecutor(3, 5, 0,
		// TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		ExecutorService es = new ThreadPoolExecutor(3, 5, 0,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				new ThreadFactory() {
					private AtomicInteger count = new AtomicInteger(0);
					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						String threadName = count.addAndGet(1) + "__";
						System.out.println(threadName);
						t.setName(threadName);
						return t;
					}
				}, new ThreadPoolExecutor.DiscardOldestPolicy());
		for (int i = 0; i < 10; i++) {
			final int index = i;
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(index);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		es.shutdown();
	}

	public static void main(String[] args) {
		ExectorDemo s = new ExectorDemo();
		s.cachePool();
	}
}
