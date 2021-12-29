package com.jdabtieu.DungeonEscape.component;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Weapon stores details about a weapon. A weapon deals 1 damage if it has no durability
 * left, and weapons cannot be switched out during battle.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Weapon extends JLabel implements Cloneable {
    /**
     * The name of the weapon
     */
    private final String name;
    
    /**
     * The amount of damage the weapon does
     */
    private final int damage;
    
    /**
     * The amount of durability the weapon has left
     */
    private int durability;
    
    /**
     * The name of the image file that represents this weapon
     */
    private final String fileName;
    
    /**
     * Create a weapon to be displayed
     * @param name          name of the weapon
     * @param damage        damage the weapon deals
     * @param durability    durability of the weapon
     * @param fileName      file path to find icon
     */
    public Weapon(final String name, final int damage, final int durability, final String fileName) {
        this.damage = damage;
        this.durability = durability;
        this.name = name;
        this.fileName = fileName;
        setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/weapon/" + fileName)));
    }
    
    /**
     * Calculates the score of the current weapon.
     * Mathematically, it is half the product of the damage and durability.
     * 
     * @return  this weapon's score
     */
    public int score() {
        return damage * durability / 2;
    }
    
    /**
     * Returns the durability of this weapon
     * 
     * @return this weapon's durability
     */
    public int getDurability() {
        return durability;
    }
    
    /**
     * Returns the damage of this weapon
     * 
     * @return this weapon's damage
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     * Performs an attack. If the weapon has more than 0 durability, it will deal between
     * 100-120% of the weapon's base damage for a normal hit. There is a 30% chance of a critical
     * hit, which would deal between 220-240% of the weapon's base damage. If the weapon has no
     * durability remaining, it deals 1 (fist) damage.
     * 
     * @param critIndicator  a JLabel that should be activated when the weapon deals a critical hit
     * @return  how much damage should be dealt
     */
    public int attack(JLabel critIndicator) {
        // no durability! fist attack
        if (durability == 0) {
            return 1;
        }
        
        durability--;
        if (Math.random() > 0.7) {
            critIndicator.setVisible(true);
            // 220-240% of weapon's damage
            return (int) (damage * (Math.random() / 5 + 2.2));
        } else {
            // 100-120% of weapon's damage
            return (int) (damage * (Math.random() / 5 + 1));
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s<br>Damage: %d<br>Durability: %d", name, damage, durability);
    }
    
    @Override
    public Weapon clone() {
        return new Weapon(name, damage, durability, fileName);
    }
}