package two;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 带有中断的消费者模型
 * 
 * @author liufanwen
 *
 * @date:2017年9月22日
 */
public class BrokenPrimeProducee extends Thread {
	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;

	public BrokenPrimeProducee(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			BigInteger bi = BigInteger.ONE;
			while (!cancelled) {
				queue.put(bi.nextProbablePrime());
			}
			while (Thread.currentThread().isInterrupted()) {
				queue.put(bi.nextProbablePrime());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public void interruptu() {
		interrupt();
	}

	void consumePrimes() throws InterruptedException {
		BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<>();
		BrokenPrimeProducee bp = new BrokenPrimeProducee(primes);
		bp.start();
		try {
			primes.take();
		} finally {
			bp.cancel();
		}
	}
}
