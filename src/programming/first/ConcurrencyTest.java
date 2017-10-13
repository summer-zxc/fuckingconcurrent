package programming.first;

/**
 * @author lfw2917
 * @date 2017年10月13日
 */
public class ConcurrencyTest {
	public static final long count = 10000000;
 
	private static void currency() throws InterruptedException{
		long start = System.currentTimeMillis();
		Thread t = new Thread(new Runnable() {		
			@Override
			public void run() {
				int a = 0;
				for (long i = 0; i < count; i++) {
					a += 5;
				}
			}
		});
		t.start();
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		t.join();
		long time = System.currentTimeMillis() - start;
		System.out.println("currency:" + time + "ms,b=" + b );
	}
	
	private static void serial() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (long i = 0; i < count; i++) {
			a += 5;
		}
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("serial:" + time + "ms,b=" + b + ",a=" + a);
	}
	public static void main(String[] args) throws InterruptedException {
		currency();
		serial();
	}
}
