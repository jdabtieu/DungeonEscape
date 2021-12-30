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
        super();
        final JLabel txt;
        final JButton btn;
        
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 75, 300, 150);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(false);
        Main.getContentPane().add(this, Layer.POPUP, 0);
        
        txt = new JLabel(text);
        txt.setFont(Fonts.STD_PARA);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(10, 11, getWidth() - 20, getHeight() - 75);
        txt.setForeground(Color.BLACK);
        add(txt);
        
        btn = new JButton("OK");
        btn.setFont(Fonts.STD_PARA);
        btn.setBounds(getWidth() / 2 - 40, getHeight() - 34, 80, 24);
        add(btn);
        btn.addActionListener(e -> {
            synchronized(this) {
                notify();
            }
        });
    }
    
    public void selection() {
        setVisible(true);
        try {
            synchronized(this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Main.getContentPane().remove(this);
        Main.getContentPane().repaint();
    }
}
