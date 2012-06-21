package net.thiefmn6092.ascent.task.impl;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thiefmn6092.ascent.Engine;
import net.thiefmn6092.ascent.task.SequentialTask;
import net.thiefmn6092.ascent.task.Task;

/**
 * Prepares updating tasks for execution.
 * @author thiefmn6092
 *
 */
public class UpdateTask extends Task {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UpdateTask.class);

	@Override
	public void execute() {
		CountDownLatch latch = new CountDownLatch(1);
		PlayerUpdateTask playerUpdateTask = new PlayerUpdateTask(latch);
		Engine.getSingleton().submitTask(new SequentialTask(playerUpdateTask));
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("Thread interrupted!", e);
		}
	}

}
