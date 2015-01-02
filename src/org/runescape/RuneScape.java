package org.runescape;

import org.runescape.rs2.Rs2Server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Launches a RuneScape Server.
 *
 * @author Main Method
 */
public class RuneScape {

    /**
     * The logger utility for printing information.
     */
    private static final Logger logger =
            Logger.getLogger(RuneScape.class.getSimpleName());

    /**
     * Launches a new RuneScape Server.
     *
     * @param arguments
     *      any runtime arguments needed at startup.
     */
    public static void main(String[] arguments) {
        if (arguments.length != 4) {
            logger.log(Level.SEVERE, "Usage: [host] [port] [world-id] [console]");
            System.exit(1);
        }

        String host = arguments[0];
        int port = Integer.parseInt(arguments[1]);
        byte world = Byte.parseByte(arguments[2]);
        boolean console = Boolean.parseBoolean(arguments[3]);

        Rs2Server.setSingleton(new Rs2Server(host, port, world));
        new Thread(Rs2Server.getSingleton()).start();
    }
}
