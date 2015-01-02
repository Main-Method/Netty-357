package org.runescape.rs2.mobile;

import org.runescape.rs2.mobile.players.Player;

/**
 * Manages the game world.
 *
 * @author Main Method
 */
public class World {

    /**
     * The singleton of this class.
     */
    private static World singleton = new World();

    /**
     * An array of all registered players.
     */
    private static final Player[] players = new Player[2000];

    /**
     * The main world tick.
     */
    public static void execute() {
        System.out.println("task completed.");
    }

    /**
     * Gets the singleton of this class.
     *
     * @return
     *      the singleton object.
     */
    public static World getSingleton() {
        return singleton;
    }
}
