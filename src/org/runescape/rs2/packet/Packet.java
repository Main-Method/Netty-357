package org.runescape.rs2.packet;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Created by Main on 1/2/2015.
 */
public final class Packet {

    private final int opcode;
    private final int size;
    private final ChannelBuffer channelBuffer;

    /**
     * Constructs a new Packet.
     *
     * @param opcode
     *      the opcode.
     * @param size
     *      the size.
     * @param channelBuffer
     *      the channelBuffer.
     */
    public Packet(int opcode, int size, ChannelBuffer channelBuffer) {
        this.opcode = opcode;
        this.size = size;
        this.channelBuffer = channelBuffer;
    }

    /**
     * Gets the opcode of the packet.
     *
     * @return
     *      the opcode.
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * Gets the size of the packet.
     *
     * @return
     *      the size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the ChannelBuffer of the packet.
     *
     * @return
     *      the channelBuffer.
     */
    public ChannelBuffer getChannelBuffer() {
        return channelBuffer;
    }
}
