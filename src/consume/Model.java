package consume;

public interface Model {
	Runnable newRunnableConsumer();

	Runnable newRunnableProducer();
}