package com.jdabtieu.DungeonEscape;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.component.StatusDisplay;
import com.jdabtieu.DungeonEscape.core.Drawable;
import com.jdabtieu.DungeonEscape.core.GameOver;
import com.jdabtieu.DungeonEscape.core.GameOverException;
import com.jdabtieu.DungeonEscape.core.MainMenu;
import com.jdabtieu.DungeonEscape.core.Player;
import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.stage.Stage;
import com.jdabtieu.DungeonEscape.stage.Stage1;

public class Main {
    public static Object mon = new Object();
    public static boolean gameOver;
    public static Thread mainThread;
    public static JFrame me = null;
    public static StatusDisplay sd = null;
    public static JPanel activeWindow = null;
    public static Player player;
    public static void main(String[] args) {
        mainThread = Thread.currentThread();
        drawSafe(() -> {
            me = new Window();
            me.setFocusable(true);
            me.setVisible(true);
        });
        while (true) {
            try {
                gameOver = false;
                drawSafe(() -> {
                    activeWindow = new MainMenu();
                    me.getContentPane().add(activeWindow, 1, 0);
                    player = new Player();
                    sd = new StatusDisplay();
                    me.getContentPane().add(sd, 2, 0);
                    player.x = 1200;
                    player.y = 500;
                });
                pause();
                drawSafe(() -> {
                    me.getContentPane().add(player, 2, 0);
                });
                swapWindow(Stage1.class, 1);
                sd.setVisible(true);
                player.setVisible(true);
                pause();
                System.out.println("Wowwww, you beat the game!"); // TODO stage 2
                me.getContentPane().removeAll();
                me.repaint();
            } catch (GameOverException e) {
                drawSafe(() -> {
                    me.getContentPane().removeAll();
                    me.repaint();
                });
            }
        }
    }
    
    public static void triggerGameOver() {
        player.setVisible(false);
        sd.setVisible(false);
        me.getContentPane().remove(player);
        me.getContentPane().remove(sd);
        if (activeWindow instanceof Stage) {
            ((Stage) activeWindow).finish();
        }
        swapWindow(GameOver.class, 100);
    }
    
    private static void swapWindow(Class<?> newWindow, int layer) {
        drawSafe(() -> {
           activeWindow.setVisible(false);
           me.getContentPane().remove(activeWindow);
           activeWindow = (JPanel) newWindow.getConstructors()[0].newInstance();
           me.getContentPane().add(activeWindow, layer, 0);
        });
    }
    
    private static void pause() {
        synchronized(mon) {
            try {
                mon.wait();
            } catch (InterruptedException e) {
                if (gameOver) {
                    throw new GameOverException();
                }
                e.printStackTrace();
            }
        }
    }
    
    public static void drawSafe(Drawable dr) {
        EventQueue.invokeLater(() -> {
            try {
                dr.draw();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}