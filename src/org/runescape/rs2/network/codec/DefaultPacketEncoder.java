package org.runescape.rs2.network.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * The encoder for the outgoing packets.
 *
 * @author Main Method
 */
public class DefaultPacketEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        return ((ChannelBuffer) message);
    }

}
