package org.runescape.rs2.network.packet;

import org.runescape.rs2.mobile.players.Player;

/**
 * Dispatches server packets to the client.
 *
 * @author Main Method
 */
public class PacketDispatcher {

    /**
     * The player object.
     */
    private Player player;

    /**
     * Constructs a new PacketDispatcher.
     *
     * @param player
     *      the player.
     */
    public PacketDispatcher(Player player) {
        this.player = player;
    }

    /**
     * Sends the login response.(response id, and rights).
     *
     * @param response
     *      the response id.
     * @return
     *      this class.
     */
    public PacketDispatcher dispatchLoginResponse(int response) {
        StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(3);
        out.writeByte(response);
        out.writeByte(player.getDetails().getPlayerRights().getValue());
        out.writeByte(0);
        player.dispatchPacket(out.getBuffer());
        return this;
    }

    /**
     * Sends a camera reset.
     *
     * @return
     *      this class.
     */
    public PacketDispatcher dispatchCameraReset() {
        StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(1);
        out.writeHeader(player.getOutCipher(), 41);
        player.dispatchPacket(out.getBuffer());
        return this;
    }

    /**
     * Sends more login details.
     *
     * @return
     *      this class.
     */
    public PacketDispatcher dispatchDetails() {
        StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
        out.writeHeader(player.getOutCipher(), 146);
        out.writeByte(1, StreamBuffer.ValueType.A);
        out.writeShort(player.getIndex(), StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
        player.dispatchPacket(out.getBuffer());
        return this;
    }

    /**
     * Sends a game message to the player.
     *
     * @param message
     *            the message to send.
     * @return
     *      this class.
     */
    public PacketDispatcher sendGameMessage(String message) {
        StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(message.length() + 3);
        out.writeVariablePacketHeader(player.getOutCipher(), 31);
        out.writeString(message);
        out.finishVariablePacketHeader();
        player.dispatchPacket(out.getBuffer());
        return this;
    }
}
