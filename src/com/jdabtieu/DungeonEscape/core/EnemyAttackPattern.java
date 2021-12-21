package com.jdabtieu.DungeonEscape.core;

/**
 * <p>This interface specifies the attack pattern of an enemy during a turn.
 * 
 * <p>The attack method should return the amount of damage the enemy deals each
 * turn. For example, to deal 5 damage (randomly) on 50% of the turns, one can
 * use:
 * 
 * <blockquote><pre>
 * if (Math.random() > 0.5) {
 *     return 5;
 * } else {
 *     return 0;
 * }
 * </pre></blockquote>
 */
@FunctionalInterface
public interface EnemyAttackPattern {
    public abstract int attack();
}