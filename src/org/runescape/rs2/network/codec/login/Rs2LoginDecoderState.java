package org.runescape.rs2.network.codec.login;

/**
 * An enum with the different states the Rs2LoginDecoder can be.
 *
 * @author Main Method
 */
public enum Rs2LoginDecoderState {

    /**
     * The login handshake state.
     */
    LOGIN_HANDSHAKE,

    /**
     * The logging in verification state.
     */
    LOGGING_IN;
}
