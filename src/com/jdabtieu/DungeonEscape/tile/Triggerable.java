package com.jdabtieu.DungeonEscape.tile;
/**
 * A tile that should perform an action when walked on. The action to be performed is defined
 * by the trigger() method.
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
@FunctionalInterface
public abstract interface Triggerable {
    /**
     * The action to be performed when the player walks on this tile
     */
    public abstract void trigger();
}
