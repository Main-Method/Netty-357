package org.runescape.rs2.network.codec.login;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.runescape.rs2.mobile.players.Player;
import org.runescape.rs2.mobile.players.PlayerDetails;
import org.runescape.rs2.network.codec.DefaultPacketDecoder;
import org.runescape.rs2.network.isaacc.ISAACCipher;
import org.runescape.utility.Misc;
import org.runescape.utility.Rs2Constants;

import java.net.InetSocketAddress;

/**
 * Decodes the RuneScape 2 #357 login protocol.
 *
 * @author Main Method
 */
public class Rs2LoginDecoder extends FrameDecoder {

    /**
     * The state of the decoding.
     */
    private Rs2LoginDecoderState state = Rs2LoginDecoderState.LOGGING_IN;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if(!channel.isConnected())
            return null;
        switch(state) {
            case LOGGING_IN:
                if (buffer.readableBytes() < 2)
                    return null;

                int type = buffer.readByte();
                if(type != 16 && type != 18) {
                    System.out.println("Wrong log-in type; Type: "+ type +" [from client: "+(InetSocketAddress) channel.getRemoteAddress()+"]");
                    channel.close();
                    return null;
                }

                int blockLength = buffer.readUnsignedByte();
                if (buffer.readableBytes() < blockLength)
                    return null;
                buffer.readByte();

                int loginProtocol = buffer.readShort();
                if (loginProtocol != Rs2Constants.CLIENT_VERSION) {
                    System.out.println("Wrong log-in protocol; Login Protocol: "+ loginProtocol +" [from client: "+(InetSocketAddress) channel.getRemoteAddress()+"]");
                    channel.close();
                    return null;
                }
                buffer.readByte();
                for (int i = 0; i < 9; i++)
                    buffer.readInt();

                buffer.readByte();
                int rsaCode = buffer.readByte();
                if (rsaCode != 10) {
                    System.out.println("Unable to decode rsa block; rsa: "+ rsaCode +" [from client: "+(InetSocketAddress) channel.getRemoteAddress()+"]");
                    channel.close();
                    return null;
                }

                long clientSessionKey = buffer.readLong();
                long serverSessionKey = buffer.readLong();
                final int[] isaacSeed = { (int) (clientSessionKey >> 32), (int) clientSessionKey, (int) (serverSessionKey >> 32), (int) serverSessionKey };
                final ISAACCipher inCipher = new ISAACCipher(isaacSeed);
                for (int i = 0; i < isaacSeed.length; i++)
                    isaacSeed[i] += 50;
                final ISAACCipher outCipher = new ISAACCipher(isaacSeed);
                buffer.readInt();

                String username = Misc.formatPlayerName(Misc.getRS2String(buffer));
                String password = Misc.getRS2String(buffer);
                return finalize(channel, username, password, inCipher, outCipher);
        }
        return null;
    }

    /**
     * Finishes the login protocol.
     *
     * @param channel
     *      the channel.
     * @param username
     *      the username.
     * @param password
     *      the password.
     * @param in
     *      the ISAACCipher.
     * @param out
     *      the ISAACipher.
     * @return
     */
    private Player finalize(Channel channel, String username, String password, ISAACCipher in, ISAACCipher out) {
        channel.getPipeline().replace("rs2LoginDecoder", "defaultDecoder", new DefaultPacketDecoder(in));
        Player player = new Player(channel, new PlayerDetails(username, password));
        player.setInCipher(in);
        player.setOutCipher(out);
        return player.login();
    }
}
