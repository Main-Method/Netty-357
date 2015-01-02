package org.runescape.rs2.mobile;

/**
 * Represents an entity. (players, npc, etc..)
 *
 * @author Main Method
 */
public abstract class Entity {

    /**
     * The entity type.
     *
     * @return
     *      the entity type.
     */
    public abstract EntityType entityType();

    private int index = -1;

    /**
     * Gets the entity's index.
     *
     * @return
     *      the index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets a new entity index.
     *
     * @param index
     *      the new index.
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
