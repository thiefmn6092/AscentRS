package net.thiefmn6092.ascent.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A {@link Task} that consists of multiple child {@link Task}s that run in a guaranteed sequence.
 * @author thiefmn6092
 *
 */
public class SequentialTask extends Task {
	
	/**
	 * The child tasks.
	 */
	private final Collection<Task> tasks;
	
	/**
	 * Creates a new SequentialTask.
	 * @param tasks The child tasks.
	 */
	public SequentialTask(Task... tasks) {
		List<Task> taskList = new ArrayList<Task>();
		for(Task task : tasks) {
			taskList.add(task);
		}
		this.tasks = Collections.unmodifiableCollection(taskList);
	}

	@Override
	public void execute() {
		for(Task task : tasks) {
			task.run();
		}
	}

}
