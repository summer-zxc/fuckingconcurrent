package race;

import java.util.concurrent.CountDownLatch;

public class Race {

	public static void race(int raceCount) throws InterruptedException {
		final CountDownLatch ready = new CountDownLatch(raceCount);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch end = new CountDownLatch(raceCount);
		for (int i = 0; i < raceCount; i++) {
			final int no = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000 + (long) (Math.random() * 3000));
						System.out.println("ready" + no);
						ready.countDown();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						start.await();
						System.out.println("start" + no);
						Thread.sleep(3000 + (long) (Math.random() * 3000));
					} catch (InterruptedException e) {
						System.out.println("hurt" + no);
					} finally {
						end.countDown();
						System.out.println("end" + no);
					}
				}
			}).start();
		}
		ready.await();
		System.out.println("all ready");
		start.countDown();
		System.out.println("run !!!");
		end.await();
		System.out.println("all end!");
	}
	public static void main(String[] args) {
		try {
			race(6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}