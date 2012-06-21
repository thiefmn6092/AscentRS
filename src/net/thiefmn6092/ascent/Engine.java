package net.thiefmn6092.ascent;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.thiefmn6092.ascent.task.impl.UpdateTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds the global executor services.
 * 
 * @author theifmn6092
 * 
 */
public class Engine {

	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Engine.class);
	
	/**
	 * The Engine singleton instance.
	 */
	private static final Engine SINGLETON = new Engine();

	/**
	 * The service for executing tasks related to the actual game.
	 */
	private final ScheduledExecutorService logicService = Executors
			.newSingleThreadScheduledExecutor();

	/**
	 * The service for executing tasks in parallel.
	 */
	private final ThreadPoolExecutor taskService = (ThreadPoolExecutor) Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	/**
	 * Cannot be instantiated by other classes.
	 */
	private Engine() {
		logger.info("Scheduling and submitting tasks...");
		scheduleAtFixedRate(new UpdateTask(), 0, 600, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Submits a task into the task service.
	 * @param runnable The task.
	 * @return The {@link java.util.concurrent.Future} instance.
	 */
	public Future<?> submitTask(Runnable runnable) {
		return taskService.submit(runnable);
	}
	
	/**
	 * Submits a task into the logic service.
	 * @param runnable The task.
	 * @return The {@link java.util.concurrent.Future} instance.
	 */
	public Future<?> submitLogic(Runnable runnable) {
		return logicService.submit(runnable);
	}
	
	/**
	 * Schedules a task into the logic service to be run after the specified delay.
	 * @param runnable The task.
	 * @param delay The delay.
	 * @param unit The time unit the delay is under.
	 * @return The {@link java.util.concurrent.ScheduledFuture} instance.
	 */
	public ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
		return logicService.schedule(runnable, delay, unit);
	}
	
	/**
	 * Schedules a task into the logic service to be run after the initial delay and every <code>delay</code> time.
	 * @param runnable The task.
	 * @param initialDelay The initial delay.
	 * @param delay The delay.
	 * @param unit The time unit the delay is under.
	 * @return The {@link java.util.concurrent.ScheduledFuture} instance.
	 */
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
		return logicService.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
	}
	
	/**
	 * Gets the Engine singleton.
	 * @return The instance.
	 */
	public static Engine getSingleton() {
		return SINGLETON;
	}

}
