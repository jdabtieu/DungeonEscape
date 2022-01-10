package com.jdabtieu.DungeonEscape.vfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * This class creates a screen flickering effect, as if the room was poorly lit and lights
 * keep going out randomly.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class ScreenFlicker extends JPanel {
    /**
     * The intensity of the effect.
     */
    private static int alpha = 0;
    
    /**
     * A reference to the animation thread
     */
    private static Thread animThread;
    
    /**
     * Creates the flickering effect, but does not animate it.
     */
    public ScreenFlicker() {
        super();
        setOpaque(false);
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        Main.getContentPane().add(this, Layer.VFX_0, 0);
    }
    
    /**
     * Sets the intensity to the specified value
     * @param a the intensity, where 0 = none and 255 = full black
     */
    public static void setAlpha(int a) {
        alpha = a;
    }
    
    /**
     * Decreases the alpha value by 1, or about 0.4%
     */
    public static void decreaseAlpha() {
        alpha = Math.max(alpha - 1, 0);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // Change transparency of this component
        // https://stackoverflow.com/questions/4982960/java-swing-translucent-components
        g.setColor(new Color(0, 0, 0, alpha));
        Insets insets = getInsets();
        g.fillRect(insets.left, insets.top, 
                getWidth() - insets.left - insets.right, 
                getHeight() - insets.top - insets.bottom);
        super.paintComponent(g);
    }
    
    /**
     * Starts the animation.
     */
    public void startAnimation() {
        // create animation thread
        animThread = new Thread(() -> {
            Main.safeSleep(1000);
            while (true) {
                // change transparency (scale from 0-255), then repaints the component
                setAlpha((int) (Math.random() * 170) + 56);
                repaint();
                // stop if this thread gets interrupted
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "Animation-ScreenFlash");
        animThread.start();
    }
    
    /**
     * Stops the animation
     */
    public static void stopAnimation() {
        animThread.interrupt();
    }
}
