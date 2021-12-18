package com.jdabtieu.DungeonEscape;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Component.StatusDisplay;
import com.jdabtieu.DungeonEscape.Stage.*;

public class Main {
    public static Object mon = new Object();
    public static boolean gameOver;
    public static Thread mainThread;
    public static JFrame me = null;
    public static StatusDisplay sd = null;
    private static JPanel activeWindow = null;
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
                swapWindow(Stage1.class, true, 1);
                sd.setVisible(true);
                pause();
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
        swapWindow(GameOver.class, false, 3);
    }
    
    private static void swapWindow(Class<?> newWindow, boolean redrawPlayer, int layer) {
        drawSafe(() -> {
           activeWindow.setVisible(false);
           me.getContentPane().remove(activeWindow);
           activeWindow = (JPanel) newWindow.getConstructors()[0].newInstance();
           me.getContentPane().add(activeWindow, layer, 0);
           if (redrawPlayer) {
               player.setVisible(true);
               player.repaint();
           }
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