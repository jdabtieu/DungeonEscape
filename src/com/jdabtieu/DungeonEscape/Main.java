package com.jdabtieu.DungeonEscape;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jdabtieu.DungeonEscape.component.VolumeSlider;
import com.jdabtieu.DungeonEscape.core.Credits;
import com.jdabtieu.DungeonEscape.core.GameOver;
import com.jdabtieu.DungeonEscape.core.GameOverException;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.MainMenu;
import com.jdabtieu.DungeonEscape.core.Music;
import com.jdabtieu.DungeonEscape.core.Player;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.stage.Stage;
import com.jdabtieu.DungeonEscape.stage.Stage1;
import com.jdabtieu.DungeonEscape.stage.Stage2;
import com.jdabtieu.DungeonEscape.stage.Stage3Part1;
import com.jdabtieu.DungeonEscape.stage.Stage3Part2;
import com.jdabtieu.DungeonEscape.vfx.ScreenFlicker;
/**
 * DungeonEscape is an adventure game that follows the main character with amnesia as
 * they traverse a vast dungeon with three floors (stages). Solve puzzles, defeat enemies
 * and bosses, gather coins and custom weapons, and find the way out!
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Main {
    /**
     * Monitor object used to pause the main thread while the game is running.
     * This object should be public to allow easy access.
     */
    public static final Object mon = new Object();
    
    /**
     * Determines whether to end the game when a pause is interrupted. This is set by the
     * GameOver class, right before triggering an interrupt. If true, the game will reset.
     * If false, the game will continue running.
     */
    public static boolean gameOver;
    
    /**
     * Stores a reference to the game's main thread, for interrupting purposes in GameOver.
     * Use getMainThread() to access.
     */
    private static Thread mainThread;
    
    /**
     * Stores a reference to the game's window. This is used to add elements to the screen.
     * Use getContentPane() to access its content pane.
     */
    private static JFrame me = null;
    
    /**
     * Stores a reference to the currently displayed scene (i.e. a stage/main menu). This
     * should always be in the display layer 1, so that items can be layered on top.
     */
    private static JPanel currScene = null;
    
    /**
     * Stores a reference to the player. Use getPlayer() to access it.
     */
    private static Player player;
    
    /**
     * Stores a reference to the scren flickering effect
     */
    private static ScreenFlicker sf;
    
    /**
     * Main method
     * @param args  not used
     */
    public static void main(String[] args) {
        // Populate main thread and create window
        mainThread = Thread.currentThread();
        SwingUtilities.invokeLater(() -> {
            me = new Window();
            me.setFocusable(true);
            me.setVisible(true);
            sf = new ScreenFlicker();
        });
        
        // Main game loop
        while (true) {
            try {
                SwingUtilities.invokeLater(() -> {
                    // Initialize new game variables
                    gameOver = false;
                    currScene = new MainMenu();
                    getContentPane().add(currScene, Layer.MAP, 0);
                    player = new Player();
                    getContentPane().add(player, Layer.PLAYER, 0);
                });
                pause(); // unpaused by Start Game button in MainMenu
                
                // Stage 1, make player and status display visible
                swapWindow(Stage1.class, 1);
                player.setVisible(true);
                getContentPane().add(new VolumeSlider(), Layer.POPUP, 0);
                sf.startAnimation();
               
                pause(); // unpaused by beating Stage 1 boss
                
                // Stage 2
                swapWindow(Stage2.class, 1);
                
                pause(); // unpaused by beating Stage 2 boss

                // Stage 3
                swapWindow(Stage3Part1.class, 1);
                
                pause(); // unpaused by finishing the stage 3 maze

                swapWindow(Stage3Part2.class, 1);
                
                pause(); // unpaused by getting to the credits scene in stage 3
               
                // Player won! Credits!!
                currScene.setVisible(false);
                getContentPane().remove(currScene);
                currScene = new Credits();
                getContentPane().add(currScene, Layer.MAP, 0);
                ((Credits) currScene).animate();
                
                throw new GameOverException();
            } catch (GameOverException e) {
                // Clear the screen if the game is over
                SwingUtilities.invokeLater(() -> {
                    getContentPane().removeAll();
                    me.repaint();
                });
                Music.stopAudio();
                ScreenFlicker.stopAnimation();
            }
        }
    }
    
    /**
     * Gets a reference to the window's content pane
     * @return  the current window's content pane
     */
    public static Container getContentPane() {
        return me.getContentPane();
    }
    
    /**
     * Gets a reference to the main thread
     * @return  reference to the main thread
     */
    public static Thread getMainThread() {
        return mainThread;
    }
    
    /**
     * Gets a reference to the player
     * @return  the player
     */
    public static Player getPlayer() {
        return player;
    }
    
    /**
     * Triggers a loss. This displays the loss screen, and halts the current game instance.
     */
    public static void triggerLoss() {
        player.setVisible(false);
        getContentPane().remove(player);
        ScreenFlicker.stopAnimation();
        getContentPane().remove(sf);
        
        // Stop keyboard listener
        if (currScene instanceof Stage) {
            ((Stage) currScene).finish();
        }
        swapWindow(GameOver.class, Layer.MAP);
    }
    
    /**
     * Switches the active scene to a new scene as described by newWindow.
     * @param newWindow the class of the new scene that should be displayed
     * @param layer     the layer it should be displayed at
     */
    private static void swapWindow(Class<?> newWindow, int layer) {
        SwingUtilities.invokeLater(() -> {
            currScene.setVisible(false);
            getContentPane().remove(currScene);
            
            try {
                currScene = (JPanel) newWindow.getConstructors()[0].newInstance();
            } catch (Exception e) {
                // Catastrophic error if the window cannot be drawn
                e.printStackTrace();
                System.exit(-1);
            }
            getContentPane().add(currScene, layer, 0);
        });
    }
    
    /**
     * Pauses the main thread until the monitor object is notified.
     * While paused, the game runs stages in a second thread. When unpaused, the game
     * moves to the next scene/stage.
     */
    private static void pause() {
        synchronized(mon) {
            try {
                mon.wait();
            } catch (InterruptedException e) {
                if (gameOver) {
                    throw new GameOverException();
                } else {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Sleeps for at least mills milliseconds. If interrupted, this function will immediately
     * return instead of throwing an InterruptedException.
     * @param mills the length of time to sleep in milliseconds
     * @throws IllegalArgumentException if the value of mills is negative
     */
    public static void safeSleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {} // interruption not important
    }
}