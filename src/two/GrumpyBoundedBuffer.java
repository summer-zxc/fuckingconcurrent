package two;

/**
 * 抛出异常并不是最优
 * 
 * @author liufanwen
 *
 * @param <V>
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public GrumpyBoundedBuffer(int capacity) {
		super(capacity);
	}

	public synchronized void put(V v) throws Exception {
		if (isFull()) {
			throw new Exception("BufferFullException");
		}
		doPut(v);
	}

	public synchronized V task() throws Exception {
		if (isEmpty()) {
			throw new Exception("BufferEmptyException");
		}
		return doTake();
	}
}
