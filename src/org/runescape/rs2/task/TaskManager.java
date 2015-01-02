package org.runescape.rs2.task;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages the tasks.
 *
 * @author Main Method
 */
public class TaskManager implements Runnable {

    /**
     * A list that holds the tasks.
     */
    private static final List<Task> tasks = new LinkedList<Task>();

    /**
     * Registers a task, and initializes it.
     *
     * @param task
     *      the task.
     */
    public static void register(Task task) {
        tasks.add(task);
        task.initialize();
    }

    @Override
    public void run() {
        for(Task task: tasks) {
            if(!task.isRunning())
                tasks.remove(task);
            task.execute();
        }
    }
}
