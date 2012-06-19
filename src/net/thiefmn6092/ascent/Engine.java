package net.thiefmn6092.ascent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds the global executor services.
 * @author theifmn6092
 *
 */
public class Engine {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);
	
	/**
	 * The service for executing tasks related to the actual game.
	 */
	public static final ScheduledExecutorService LOGIC_SERVICE = Executors.newSingleThreadScheduledExecutor();

	/**
	 * The service for executing tasks in parallel.
	 */
	public static final ThreadPoolExecutor TASK_SERVICE = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	static {
		/*
		 * Schedule various tasks here.
		 */
		LOGIC_SERVICE.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("Performing garbage collection...");
				System.gc();
			}
		}, 0, 15, TimeUnit.MINUTES);
	}
	
}
