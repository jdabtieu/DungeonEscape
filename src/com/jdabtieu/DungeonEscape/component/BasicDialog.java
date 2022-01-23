package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;

/**
 * BasicDialog is used to display a simple text-based dialog in the popup layer.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class BasicDialog extends JPanel {
    /**
     * Creates the dialog
     * @param text  text to be displayed
     */
    public BasicDialog(final String text) {
        this(text, Color.BLACK);
    }
    
    /**
     * Creates the dialog
     * @param text  text to be displayed
     * @param clr   color of the text
     */
    public BasicDialog(final String text, final Color clr) {
        super();
        final JLabel txt = new JLabel(text);
        final JButton btn = new JButton("OK");
        
        // create and add popup
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 75, 300, 150);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(false);
        Main.getContentPane().add(this, Layer.POPUP, 0);
        
        // add text and ok button
        txt.setFont(Fonts.STD_PARA);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(10, 11, getWidth() - 20, getHeight() - 60);
        txt.setForeground(clr);
        add(txt);
        
        btn.setFont(Fonts.STD_PARA);
        btn.setBounds(getWidth() / 2 - 40, getHeight() - 34, 80, 24);
        add(btn);
        btn.addActionListener(e -> {
            synchronized(this) {
                notify(); // unpause when button pressed
            }
        });
    }
    
    public void selection() {
        setVisible(true);
        try {
            synchronized(this) {
                wait(); // pause and wait for ok to be pressed
            }
        } catch (InterruptedException e) {
            // rethrow interrupt
            Thread.currentThread().interrupt();
        }
        
        Main.getContentPane().remove(this);
        Main.getContentPane().repaint();
    }
}
