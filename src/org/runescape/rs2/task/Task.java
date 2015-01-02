package org.runescape.rs2.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a periodic server task.
 *
 * @author Main Method
 */
public abstract class Task {

    protected abstract void execute();

    /**
     * The delay.
     */
    private final int delay;

    /**
     * The TimeUnit, in milliseconds.
     */
    private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * A flag to check if a task is running.
     */
    private boolean running;

    /**
     * Constructs a new Task.
     *
     * @param delay
     *      the delay.
     */
    public Task(int delay) {
        this.delay = delay;
    }

    /**
     * The ScheduledExecutorService of the task.
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * Starts a new task.
     */
    public void initialize() {
        if(running || scheduledExecutorService.isShutdown())
            return;
        else
            running = true;
        scheduledExecutorService.scheduleAtFixedRate(new TaskManager(), 0, delay, timeUnit);
    }

    /**
     * Stops a task.
     */
    public void terminate() {
        if (!running)
            return;
        else
            running = false;
        scheduledExecutorService.shutdown();
    }

    /**
     * Gets the running flag.
     *
     * @return
     *      the running flag.
     */
    public boolean isRunning() {
        return running;
    }
}
