package net.thiefmn6092.ascent.task.impl;

import java.util.concurrent.CountDownLatch;

import net.thiefmn6092.ascent.task.Task;

/**
 * Performs player updating.
 * @author thiefmn6092
 *
 */
public class PlayerUpdateTask extends Task {

	/**
	 * Creates a new PlayerUpdateTask.
	 * @param latch The latch to be counted down for blocking.
	 */
	public PlayerUpdateTask(CountDownLatch latch) {
		super(latch);
	}

	@Override
	public void execute() {
		/*
		 * TODO Playing updating.
		 */
	}

}
