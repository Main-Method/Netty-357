package org.runescape.rs2.mobile.players;

import org.jboss.netty.channel.Channel;
import org.runescape.rs2.mobile.Entity;
import org.runescape.rs2.mobile.EntityType;
import org.runescape.rs2.network.isaacc.ISAACCipher;
import org.runescape.rs2.packet.PacketDispatcher;
import org.runescape.utility.Rs2Constants;

import java.net.InetSocketAddress;

/**
 * Represents an in-game player.
 *
 * @author Main Method
 */
public class Player extends Entity {

    /**
     * The player's details.
     */
    private PlayerDetails playerDetails;

    /**
     * The player's incoming packet handler.
     */
    private PacketDispatcher packetDispatcher = new PacketDispatcher(this);

    /**
     * The player's channel.
     */
    private Channel channel;

    private ISAACCipher inCipher;
    private ISAACCipher outCipher;

    /**
     * Constructs a new Player.
     *
     * @param playerDetails
     *      the player's details.
     */
    public Player(Channel channel, PlayerDetails playerDetails) {
        super();
        this.channel = channel;
        this.playerDetails = playerDetails;
    }

    public Player login() {
        packetDispatcher.dispatchLoginResponse(2);
        packetDispatcher.dispatchCameraReset();
        packetDispatcher.dispatchDetails();
        packetDispatcher.sendGameMessage("Welcome to "+ Rs2Constants.SERVER_NAME +".");
        System.out.println(playerDetails.getUsername() + " has logged in. [" + (InetSocketAddress) channel.getRemoteAddress() + "]");
        return this;
    }

    /**
     * Dispatches a packet.
     *
     * @param packet
     *      the packet.
     */
    public void dispatchPacket(Object packet) {
        channel.write(packet);
    }

    @Override
    public EntityType entityType() {
        return EntityType.PLAYER;
    }

    /**
     * Gets the player's details.
     *
     * @return
     *      the player's details.
     */
    public PlayerDetails getDetails() {
        return playerDetails;
    }

    /**
     * Gets the PacketDispatcher.
     *
     * @return
     *      the packetDispatcher object.
     */
    public PacketDispatcher getPacketDispatcher() {
        return packetDispatcher;
    }

    /**
     * Gets the channel.
     *
     * @return
     *      the channel object.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Gets the inCipher.
     *
     * @return
     *      the inCipher object.
     */
    public ISAACCipher getInCipher() {
        return inCipher;
    }

    /**
     * Gets the outCipher.
     *
     * @return
     *      the outCipher object.
     */
    public ISAACCipher getOutCipher() {
        return outCipher;
    }

    /**
     * Sets the inCipher.
     *
     * @param inCipher
     *      the inCipher.
     */
    public void setInCipher(ISAACCipher inCipher) {
        this.inCipher = inCipher;
    }

    /**
     * Sets the outCipher.
     *
     * @param outCipher
     *      the outCipher.
     */
    public void setOutCipher(ISAACCipher outCipher) {
        this.outCipher = outCipher;
    }
}
