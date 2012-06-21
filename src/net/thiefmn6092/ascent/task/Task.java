package net.thiefmn6092.ascent.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A unit of work that is run on the task service in parallel.
 * 
 * @author thiefmn6092
 * 
 */
public abstract class Task implements Runnable {

	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(Task.class);

	/**
	 * The {@link java.util.concurrent.CountDownLatch} instance used for
	 * blocking.
	 */
	private final Collection<CountDownLatch> latches;

	/**
	 * Creates a new Task.
	 * 
	 * @param latche
	 *            The {@link java.util.concurrent.CountDownLatch} instance.
	 */
	public Task(CountDownLatch... latches) {
		List<CountDownLatch> latchList = new ArrayList<CountDownLatch>();
		for (CountDownLatch latch : latches) {
			latchList.add(latch);
		}
		this.latches = Collections.unmodifiableCollection(latchList);
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (Exception e) {
			logger.error("Exception caught while executing task!", e);
		} finally {
			for (CountDownLatch latch : latches) {
				latch.countDown();
			}
		}
	}

	/**
	 * Executes the Task.
	 */
	public abstract void execute();

}
