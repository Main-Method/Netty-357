package org.runescape.rs2.task.impl;

import org.runescape.rs2.mobile.World;
import org.runescape.rs2.task.Task;

/**
 * The main world task.
 *
 * @author Main Method
 */
public class WorldProcessor extends Task {

    /**
     * The cycles, expressed in milliseconds.
     */
    private static final int CYCLES = 600;

    /**
     * Constructs a new WorldProcessor.
     */
    public WorldProcessor() {
        super(CYCLES);
    }

    @Override
    protected void execute() {
        World.getSingleton().execute();
    }
}
