package org.runescape.rs2.mobile.players;

import org.jboss.netty.channel.Channel;

/**
 * The details of an in-game player.
 *
 * @author Main Method
 */
public class PlayerDetails {

    /**
     * Represents the rights of a player.
     *
     * @author Main Method
     */
    public enum PlayerRights {

        /**
         * A standard user.
         */
        REGULAR(0),

        /**
         * A player moderator user.
         */
        MODERATOR(1),

        /**
         * An administrator user.
         */
        ADMINISTRATOR(2);

        private int value;

        /**
         * Constructs new PlayerRights.
         *
         * @param value
         *      the rank.
         */
        private PlayerRights(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    private String username;
    private String password;
    private PlayerRights playerRights;

    /**
     * Constructs a new PlayerDetails.
     *
     * @param username
     *      the name of the player.
     * @param password
     *      the password of the player.
     */
    public PlayerDetails(String username, String password) {
        setUsername(username);
        setPassword(password);
        setPlayerRights(PlayerRights.REGULAR);
    }

    /**
     * Gets the username.
     *
     * @return
     *      the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     *
     * @return
     *      the
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the player's rights.
     *
     * @return
     *      the player's rights.
     */
    public PlayerRights getPlayerRights() {
        return playerRights;
    }

    /**
     * Sets a new username.
     *
     * @param username
     *      the new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets a new password.
     *
     * @param password
     *      the new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets a new player's rights.
     *
     * @param playerRights
     *      the new player's rights.
     */
    public void setPlayerRights(PlayerRights playerRights) {
        this.playerRights = playerRights;
    }
}
