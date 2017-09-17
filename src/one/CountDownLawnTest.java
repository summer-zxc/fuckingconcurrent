package one;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLawnTest {
	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end = System.nanoTime();
		return start - end;
	}

	public static void main(String[] args) {
		final AtomicInteger ai = new AtomicInteger(0);
		CountDownLawnTest h = new CountDownLawnTest();
		try {
			Long s = h.timeTasks(10, new Runnable() {
				public void run() {
					System.out.println(ai.getAndIncrement());
					System.out.println(Thread.currentThread().getName());
				}
			});
			System.out.println(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
