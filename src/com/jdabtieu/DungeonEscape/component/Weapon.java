package com.jdabtieu.DungeonEscape.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * <p>A class storing details about a weapon
 * 
 * <p>A weapon deals 0 damage if it has no durability left, and weapons cannot
 * be switched out during battle.
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
    
    public Weapon(final String name, final int damage, final int durability, final String fileName) {
        this.damage = damage;
        this.durability = durability;
        this.name = name;
        this.fileName = fileName;
        try {
            BufferedImage titleText = ImageIO.read(new File("assets/weapon/" + fileName));
            setIcon(new ImageIcon(titleText));
        } catch (IOException e) {
            e.printStackTrace();
            BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setStroke(new BasicStroke(4));
            g.setColor(Color.RED);
            g.drawLine(0, 0, 40, 40);
            g.drawLine(0, 40, 40, 0);
            setIcon(new ImageIcon(img));
        }
    }
    
    /**
     * <p>Calculates the score of the current weapon.
     * 
     * <p>Mathematically, it is half the product of the damage and durability
     * @return  this weapon's score
     */
    public int score() {
        return damage * durability / 2;
    }
    
    /**
     * Returns the durability of this weapon
     * @return this weapon's durability
     */
    public int getDurability() {
        return durability;
    }
    
    /**
     * Returns the damage of this weapon
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
     * @return  how much damage should be dealt
     */
    public int attack() {
        // no durability! fist attack
        if (durability == 0) {
            return 1;
        }
        
        durability--;
        if (Math.random() > 0.7) {
            System.err.println("INFO: Critical hit!"); // TODO
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