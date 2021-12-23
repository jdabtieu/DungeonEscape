package com.jdabtieu.DungeonEscape;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.component.StatusDisplay;
import com.jdabtieu.DungeonEscape.core.GameOver;
import com.jdabtieu.DungeonEscape.core.GameOverException;
import com.jdabtieu.DungeonEscape.core.MainMenu;
import com.jdabtieu.DungeonEscape.core.Player;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.stage.Stage;
import com.jdabtieu.DungeonEscape.stage.Stage1;
import com.jdabtieu.DungeonEscape.stage.Stage2;
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
    public static Object mon = new Object();
    
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
     * Stores a reference to the status display. Use getSD() to access it.
     */
    private static StatusDisplay sd = null;
    
    /**
     * Stores a reference to the currently displayed scene (i.e. a stage/main menu). This
     * should always be in the display layer 1, so that items can be layered on top.
     */
    private static JPanel currScene = null;
    
    /**
     * Stores a reference to the player. Use getPlayer() to access it.
     */
    private static Player player;
    
    public static void main(String[] args) {
        // Populate main thread and create window
        mainThread = Thread.currentThread();
        drawSafe(() -> {
            me = new Window();
            me.setFocusable(true);
            me.setVisible(true);
        });
        
        // Main game loop
        while (true) {
            try {
                drawSafe(() -> {
                    // Initialize new game variables
                    gameOver = false;
                    currScene = new MainMenu();
                    me.getContentPane().add(currScene, 1, 0);
                    player = new Player();
                    sd = new StatusDisplay();
                    getContentPane().add(sd, 3, 0);
                    getContentPane().add(player, 2, 0);
                    player.setLocation(1200, 500);
                });
                pause(); // unpaused by Start Game button in MainMenu
                
                // Stage 1, make player and status display visible
                swapWindow(Stage1.class, 1);
                sd.setVisible(true);
                player.setVisible(true);
                
                pause(); // unpaused by beating Stage 1 boss
                
                // Stage 2
                swapWindow(Stage2.class, 1);
                
                pause(); // unpaused by beating Stage 2 boss
                
                System.out.println("Wowwww, you beat the game!"); // TODO stage 3
                throw new GameOverException();
            } catch (GameOverException e) {
                // Clear the screen if the game is over
                drawSafe(() -> {
                    getContentPane().removeAll();
                    me.repaint();
                });
            }
        }
    }
    
    /**
     * Gets a reference to the window's content pane
     * @return  the content pane
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
     * Gets a reference to the status display
     * @return  the status display
     */
    public static StatusDisplay getSD() {
        return sd;
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
        sd.setVisible(false);
        me.getContentPane().remove(player);
        me.getContentPane().remove(sd);
        
        // Stop keyboard listener
        if (currScene instanceof Stage) {
            ((Stage) currScene).finish();
        }
        swapWindow(GameOver.class, 100);
    }
    
    /**
     * Switches the active scene to a new scene as described by newWindow.
     * @param newWindow the class of the new scene that should be displayed
     * @param layer     the layer it should be displayed at
     */
    private static void swapWindow(Class<?> newWindow, int layer) {
        drawSafe(() -> {
            currScene.setVisible(false);
            me.getContentPane().remove(currScene);
            
            try {
                currScene = (JPanel) newWindow.getConstructors()[0].newInstance();
            } catch (Exception e) {
                // Catastrophic error if the window cannot be drawn
                e.printStackTrace();
                System.exit(-1);
            }
            me.getContentPane().add(currScene, layer, 0);
        });
    }
    
    /**
     * Pauses the main thread until the monitor object is notified.
     * While paused, the game runs stages in a second thread. When unpaused. the game
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
     * Runs code as specified by dr in the AWT Event Thread
     * @param dr    the code to be run
     */
    public static void drawSafe(Runnable dr) {
        EventQueue.invokeLater(() -> {
            dr.run();
        });
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