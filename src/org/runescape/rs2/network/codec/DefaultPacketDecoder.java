package org.runescape.rs2.network.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.runescape.rs2.network.isaacc.ISAACCipher;
import org.runescape.rs2.packet.Packet;

/**
 * Decodes incoming packets(packets sent from the client).
 *
 * @author Main Method
 */
public class DefaultPacketDecoder extends FrameDecoder {

    /**
     * Constructs a new RS2Decoder.
     *
     * @param cipher
     *      the inCipher.
     */
    public DefaultPacketDecoder(ISAACCipher cipher) {
        this.cipher = cipher;
    }

    /**
     * The cipher.
     */
    private final ISAACCipher cipher;

    /**
     * Incoming packet sizes array.
     */
    public static final int PACKET_SIZES[] = {
            0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
            0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
            0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
            0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
            2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
            0, 0, 0, 12, 0, 0, 0, 0, 8, 0, //50
            0, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
            6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
            0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
            0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
            0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
            0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
            1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
            0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
            0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
            0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
            0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
            0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
            0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
            2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
            4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
            0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
            1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
            0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
            0, 0, 6, 6, 0, 0, 0            //250
    };


    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() > 0) {
            int opcode = (buffer.readUnsignedByte() - cipher.getNextValue()) & 0xFF;
            int size = PACKET_SIZES[opcode];
            if (size == -1)
                size = buffer.readUnsignedByte();
            if (buffer.readableBytes() >= size) {
                final byte[] data = new byte[size];
                buffer.readBytes(data);
                final ChannelBuffer payload = ChannelBuffers.buffer(size);
                payload.writeBytes(data);
                return new Packet(opcode, size, payload);
            }
        }
        return null;
    }

}
