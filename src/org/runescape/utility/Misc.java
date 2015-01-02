package org.runescape.utility;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 *
 *
 * @author Blakeman8192
 */
public class Misc {

    /**
     * Gets a rs2 string, sent from the client.
     *
     * @param buf
     *      the buf of the client.
     * @return
     *      the rs2 string.
     */
    public static String getRS2String(final ChannelBuffer buf) {
        final StringBuilder bldr = new StringBuilder();
        byte b;
        while (buf.readable() && (b = buf.readByte()) != 10)
            bldr.append((char) b);
        return bldr.toString();
    }

    /**
     * Gets the players name.
     *
     * @param str
     *      the input.
     * @return
     *      the name of the players.
     */
    public static String formatPlayerName(String str) {
        str = ucFirst(str);
        str.replace("_", " ");
        return str;
    }

    /**
     *
     *
     * @param str
     * @return
     */
    public static String ucFirst(String str) {
        str = str.toLowerCase();
        if (str.length() > 1) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str.toUpperCase();
        }
        return str;
    }


}
