package taskexecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureTaskDemo {
	public void cachePool() throws InterruptedException, ExecutionException {
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
						t.setName(threadName);
						return t;
					}
				}, new ThreadPoolExecutor.DiscardOldestPolicy());

		CompletionService<String> cs = new ExecutorCompletionService<String>(es);
		for (int i = 0; i < 10; i++) {
			cs.submit(new MyTask());
		}
		// 获取任务的结果
		for (int j = 0; j < 10; j++) {
			Future<String> future = cs.take();
			System.out.println(future.get());
		}
		es.shutdown();
	}

	public static void main(String[] args) {
		FutureTaskDemo s = new FutureTaskDemo();
		try {
			s.cachePool();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public class MyTask implements Callable<String> {

		@Override
		public String call() throws Exception {
			// 睡眠3秒，代表执行某段复杂业务逻辑
			Thread.sleep(3000);
			String result = "成功！" + Thread.currentThread().getName();
			return result;
		}

	}

}
