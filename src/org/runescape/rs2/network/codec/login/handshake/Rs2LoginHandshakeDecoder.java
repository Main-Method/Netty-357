package org.runescape.rs2.network.codec.login.handshake;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.runescape.rs2.network.codec.login.Rs2LoginDecoder;
import org.runescape.rs2.network.codec.login.Rs2LoginDecoderState;
import org.runescape.rs2.packet.StreamBuffer;

import java.net.InetSocketAddress;
import java.security.SecureRandom;

/**
 * The decoder for the incoming login protocol.
 *
 * @author Main Method
 */
public class Rs2LoginHandshakeDecoder extends FrameDecoder {

    /**
     * The state of the decoding.
     */
    private Rs2LoginDecoderState state = Rs2LoginDecoderState.LOGIN_HANDSHAKE;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if(!channel.isConnected())
            return null;
        switch(state) {
            case LOGIN_HANDSHAKE:
                if (buffer.readableBytes() < 2)
                    return null;

                int request = buffer.readUnsignedByte();
                if (request != 14) {
                    System.out.println("Wrong log-in request; Request: "+ request +" [from client: "+(InetSocketAddress) channel.getRemoteAddress()+"]");
                    channel.close();
                    return null;
                }
                buffer.readUnsignedByte();

                StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(17);
                out.writeLong(0);
                out.writeByte(0);
                out.writeLong(new SecureRandom().nextLong());
                channel.write(out.getBuffer());

                channel.getPipeline().replace("rs2LoginHandshakeDecoder", "rs2LoginDecoder", new Rs2LoginDecoder());
                break;
        }
        return null;
    }
}
