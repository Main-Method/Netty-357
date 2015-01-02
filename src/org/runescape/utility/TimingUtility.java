package org.runescape.utility;

/**
 * A simple milliseconds timing utility.
 *
 * @author Main Method
 */
public class TimingUtility {

    /**
     * The cached time.
     */
    private long cachedTime;

    /**
     * Constructs a new TimingUtility.
     */
    public TimingUtility() {
        start();
    }

    /**
     * Initiates the cached time to the current time.
     *
     * @return
     *      this class.
     */
    public TimingUtility start() {
        cachedTime = System.currentTimeMillis();
        return this;
    }

    /**
     * Gets the time the cached time was last initiated, in milliseconds.
     *
     * @return
     *      the time, in milliseconds.
     */
    public long getElapsedTime() {
        return System.currentTimeMillis() - cachedTime;
    }

    @Override
    public String toString() {
        return "[took "+getElapsedTime()+" ms]";
    }
}
