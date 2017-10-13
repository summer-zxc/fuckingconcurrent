package programming.second;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lfw2917
 * @date 2017年10月13日 下午4:00:27
 */
public class Counter {
	private int i = 0;
	private AtomicInteger ai = new AtomicInteger(0);
	private Lock lock = new ReentrantLock();

	private void safeCount() {
		ai.getAndIncrement();
	}

	private void safeCount1() {
		for (;;) {
			int i = ai.get();
			boolean suc = ai.compareAndSet(i, ++i);
			if (suc) {
				break;
			}
		}
	}
	private void safeCount2() {
		lock.lock();
		i++;
		lock.unlock();
	}

	private void count() {
		lock.lock();
		i++;
		lock.unlock();
	}

	public static void main(String[] args) throws InterruptedException {
		final Counter cas = new Counter();
		List<Thread> ts = new ArrayList<Thread>(600);
		long start = System.currentTimeMillis();
		for (int j = 0; j < 100; j++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						cas.count();
						cas.safeCount();

					}
				}
			});
			ts.add(t);
		}
		for (Thread t : ts) {
			t.start();
		}
		for (Thread t : ts) {
			t.join();
		}
		System.out.println(cas.i);
		System.out.println(cas.ai.get());
		System.out.println(System.currentTimeMillis() - start);
	}
}
