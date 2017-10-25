package two;

/**
 * 阻塞式的缓存实现。和SLEEP_TIME_BUFFER长短有关，时间越短响应越高，但是cpu压力越大，
 * 
 * @author liufanwen
 *
 * @param <V>
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public static final int SLEEP_TIME_BUFFER = 100;

	public SleepyBoundedBuffer(int capacity) {
		super(capacity);
	}

	public void put(V v) throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isEmpty()) {
					doPut(v);
					return;
				}
			}
			Thread.sleep(SLEEP_TIME_BUFFER);
		}
	}

	public V take() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isEmpty()) {
					return doTake();
				}
			}
			Thread.sleep(SLEEP_TIME_BUFFER);
		}
	}

}
