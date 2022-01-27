package com.jdabtieu.DungeonEscape.core;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.Inventory;
import com.jdabtieu.DungeonEscape.component.StatusDisplay;
import com.jdabtieu.DungeonEscape.component.Weapon;
import com.jdabtieu.DungeonEscape.tile.Tile;
/**
 * This class stores information about the player, including the status display and inventory
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Player extends Tile {
    /**
     * The amount of coins the player has
     */
    private int coins;

    /**
     * The amount of health the player has
     */
    private int health;
    
    /**
     * The x-position of the player, relative to the map
     */
    private int x;
    
    /**
     * The y-position of the player, relative to the map
     */
    private int y;
    
    /**
     * The number of keys the player has
     */
    private int keys;
    
    /**
     * A reference to the player's inventory
     */
    private final Inventory inv;
    
    /**
     * A reference to the player's status display
     */
    private final StatusDisplay sd;
    
    /**
     * A list of weapons the player is holding
     */
    private final ArrayList<Weapon> weapons;
    
    /**
     * A reference to the active weapon
     */
    private Weapon activeWeapon;
    
    /**
     * Whether the player is allowed to move
     */
    private boolean pauseMovement;
    
    /**
     * Whether the player has completed the interview. This triggers an alternate ending
     */
    private boolean interviewComplete;
    
    /**
     * The high score for this game
     */
    private static int highScore = 0;
    
    /**
     * Creates a new player, status display, and inventory
     */
    public Player() {
        super();
        setBackground(Color.green);
        x = Window.WIDTH / 2 - 10;
        y = Window.HEIGHT / 2 - 10;
        setBounds(x, y, 20, 20);
        setVisible(false);
        
        coins = 0;
        health = 100;
        keys = 0;
        inv = new Inventory();
        sd = new StatusDisplay();
        weapons = new ArrayList<>();
        pauseMovement = false;
        interviewComplete = false;
        
        Main.getContentPane().add(inv, Layer.PLAYER_INFO, 0);
        Main.getContentPane().add(sd, Layer.PLAYER_INFO, 0);
    }
    
    /**
     * Adds the specified amount of coins to the player
     * @param coins the number of coins to add
     * @throws IllegalArgumentException if coins is negative
     */
    public void addCoins(final int coins) {
        if (coins < 0) throw new IllegalArgumentException("Cannot add negative coins");
        this.coins += coins;
        sd.repaint();
    }

    /**
     * Use the specified amount of coins
     * @param coins the number of coins to use
     * @throws IllegalArgumentException if the player doesn't have enough coins
     */
    public void useCoins(final int coins) {
        if (coins > this.coins) throw new IllegalArgumentException("Not enough coins");
        this.coins -= coins;
        sd.repaint();
    }
    
    /**
     * Get the number of coins the player has
     * @return  the number of coins the player has
     */
    public int getCoins() {
        return coins;
    }
    
    /**
     * Change the player's health by the specified amount. If the player runs out of health
     * as a result of this call, a loss will be triggered
     * @param dh    amount to change the health by
     */
    public void changeHealth(final int dh) {
        if (health + dh > 0) {
            health += dh;
            sd.repaint();
            return;
        }
        // player is out of health, game over
        health = 0;
        Main.triggerLoss();
    }
    
    /**
     * Get the amount of health the player has
     * @return  the amount of health the player has
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Sets the player's healtth to the specified value
     * @param health    the new health of the player
     */
    public void setHealth(final int health) {
        this.health = health;
        sd.repaint();
    }
    
    /**
     * Gets the player's x-position relative to the map
     * @return  the player's x-position
     */
    public int xPos() {
        return x;
    }
    
    /**
     * Gets the player's y-position relative to the map
     * @return  the player's y-position
     */
    public int yPos() {
        return y;
    }
    
    /**
     * Move the player by the specified amount in the x- and y-directions.
     * This method should only be called by the Stage class, since this
     * method does not update the map position.
     * @param dx    the amount to move the player in the x-direction
     * @param dy    the amount to move the player in the y-direction
     */
    public void movePlayer(final int dx, final int dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * Sets the player's position to (x, y), relative to the map.
     * This method should only be called by the Stage class or in
     * the constructor of a Stage subclass, since this
     * method does not update the map position. Instead, use
     * Stage.setPlayerPosition(x, y)
     * 
     * @param x the player's new x-position
     * @param y the player's new y-position
     */
    public void setPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Add a key to the player
     */
    public void addKey() {
        keys++;
        sd.repaint();
    }
    
    /**
     * Use a key, if the player has one
     * @return  whether the player had a key to use
     */
    public boolean useKey() {
        if (keys < 0) return false;
        keys--;
        sd.repaint();
        return true;
    }

    /**
     * Get the number of keys the player has
     * @return  the number of keys the player has
     */
    public int getKeys() {
        return keys;
    }
    
    /**
     * Sets the inventory's visibility
     * @param aFlag true to make the inventory visible; false to make it invisible
     * @see Inventory#setVisible(boolean)
     */
    public void setInventoryVisible(final boolean aFlag) {
        inv.setVisible(aFlag);
    }
    
    /**
     * Repaints the inventory
     * @see Inventory#repaint()
     */
    public void repaintInventory() {
        SwingUtilities.invokeLater(() -> inv.repaint());
    }
    
    /**
     * Sets the status display's visibility
     * @param aFlag true to make the status display visible; false to make it invisible
     * @see StatusDisplay#setVisible(boolean)
     */
    public void setSDVisible(final boolean aFlag) {
        sd.setVisible(aFlag);
    }
    
    /**
     * Returns a reference to the status display
     * @return  a reference to the status display
     */
    public StatusDisplay getSD() {
        return sd;
    }
    
    /**
     * Prompts the player to select a weapon. The selected weapon will be set as activeWeapon
     * @see #weaponSelect(String)
     */
    public void weaponSelect() {
        weaponSelect("Choose a Weapon");
    }

    /**
     * Prompts the player to select a weapon using the specified prompt.
     * The selected weapon will be set as activeWeapon
     * @param pmt   the prompt text
     */
    public void weaponSelect(final String pmt) {
        // get a reference to this object to use for wait/notify
        final Object mon = this;
        final boolean movementPaused = pauseMovement;
        final JPanel contentPane = new JPanel();
        final JLabel title = new JLabel(pmt);
        
        // create the prompt
        pauseMovement = true;
        contentPane.setBounds(Window.WIDTH / 2 - 90, Window.HEIGHT / 2 - 150, 180, 300);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GREEN);
        
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Fonts.WEAPON_SELECT);
        title.setBounds(0, 0, 180, 60);
        contentPane.add(title);
        
        // add each weapon to the prompt
        for (int i = 0; i < weapons.size(); i++) {
            final int f = i; // variables in anonymous classes must be final or effectively final
            final JPanel container = new JPanel();
            final Weapon wp = weapons.get(i).clone();
            final JLabel lab = new JLabel("<html>" + wp + "</html>");
            
            // position the weapon container, add the weapon image, and text to it
            container.setLayout(null);
            container.setBounds(20, 60 * i + 60, 140, 60);
            container.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            wp.setBounds(0, 10, 40, 40);
            lab.setFont(Fonts.STD_PARA);
            lab.setBounds(40, 0, 200, 60);
            container.add(wp);
            container.add(lab);
            
            // select the weapon when the user clicks on it
            container.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    activeWeapon = weapons.get(f);
                    pauseMovement = movementPaused;
                    contentPane.setVisible(false);
                    Main.getContentPane().remove(contentPane);
                    
                    // unpause
                    synchronized(mon) {
                        mon.notify();
                    }
                }
            });
            contentPane.add(container);
        }
        Main.getContentPane().add(contentPane, Layer.POPUP, 0);
        synchronized(this) {
            try { // pause
                wait();
            } catch (InterruptedException e) {
                // rethrow interrupt
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Adds the specified weapon to the player's inventory. This does not set it as the active weapon.
     * The player can hold up to 3 weapons at once, and extras are discarded.
     * @param wp    the weapon to add to the player's inventory
     */
    public void addWeapon(final Weapon wp) {
        weapons.add(wp);
        if (weapons.size() > 3) { // discard if more than 3 weapons
            weaponSelect("<html>You found a new weapon,<br>but you can only hold 3.<br>Choose one to discard.</html>");
            weapons.remove(activeWeapon);
            activeWeapon = null;
        } else {
            new BasicDialog("<html>You found a new weapon!<br>" + wp + "</html>", Color.BLACK).selection();
        }
        inv.repaint();
    }
    
    /**
     * Returns the player's list of weapons
     * @return  the list of weapons
     */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Returns the player's current active weapon
     * @return  the active weapon
     */
    public Weapon getActiveWeapon() {
        return activeWeapon;
    }
    
    /**
     * Pauses the player's movement
     */
    public void pauseMovement() {
        pauseMovement = true;
    }
    
    /**
     * Unpauses the player's movement
     */
    public void unpauseMovement() {
        pauseMovement = false;
    }
    
    /**
     * Returns whether the player's movement is paused
     * @return  whether the player's movement is paused
     */
    public boolean movementPaused() {
        return pauseMovement;
    }
    
    /**
     * Returns whether the player completed the interview
     * @return  whether the player completed the interview
     */
    public boolean interviewComplete() {
        return interviewComplete;
    }

    /**
     * Marks the inventory as complete
     */
    public void setInterviewComplete() {
        interviewComplete = true;
    }

    /**
     * Returns the high score over all games played in the current session
     * @return  the session high score
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     * Updates the high score of the session. This sets the high score
     * equal to the current score if the current score is higher than the
     * previously recorded high score.
     */
    public void updateHighScore() {
        highScore = Math.max(score(), highScore);
    }

    /**
     * Returns the player's score for this run. This is mathematically equal to
     * the number of coins, plus 100 times the number of keys, plus the sum of
     * scores for each of the player's weapons
     * @return  the player's score
     * @see Weapon#score()
     */
    public int score() {
        return coins + 100 * keys + getWeapons().stream()
                                           .mapToInt(e -> e.score()).sum();
    }
    
    /**
     * Sets the player, inventory, and status display's visibility according to
     * the flag
     * @param aFlag true to make them visible; false to make them invisible
     */
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (inv != null) inv.setVisible(aFlag);
        if (sd != null) sd.setVisible(aFlag);
    }
}
