package two;

/**
 * 有界缓存实现的基类
 * 
 * @author liufanwen
 *
 * @param <V>
 */
public abstract class BaseBoundedBuffer<V> {
	private final V[] buff;
	private int tail;
	private int head;
	private int count;

	protected BaseBoundedBuffer(int capacity) {
		this.buff = (V[]) new Object[capacity];
	}

	protected synchronized final void doPut(V v) {
		buff[tail] = v;
		if (++tail == buff.length) {
			tail = 0;
		}
		++count;
	}

	protected synchronized final V doTake() {
		V v = buff[head];
		buff[head] = null;
		if (++head == buff.length) {
			head = 0;
		}
		--count;
		return v;
	}

	public synchronized final boolean isFull() {
		return count == buff.length;
	}

	public synchronized final boolean isEmpty() {
		return count == 0;
	}
}
