package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * BasicPopup is used to display a simple text-based popup in the popup layer.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class BasicPopup extends JPanel {
    /**
     * Creates the popup
     * @param text  text to be displayed
     * @param color color of the text
     */
    public BasicPopup(String text, Color color) {
        boolean movementPaused = Main.getPlayer().movementPaused();
        Main.getPlayer().pauseMovement();
        
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 75, 300, 150);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        Main.getContentPane().add(this, Layer.POPUP, 0);
        
        JButton btnClose = new JButton("X");
        btnClose.setBackground(Color.LIGHT_GRAY);
        btnClose.setBorder(null);
        btnClose.setFocusPainted(false);
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnClose.setBounds(getWidth() - 40, 10, 28, 23);
        add(btnClose);
        
        JLabel txt = new JLabel(text);
        txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(0, 10, getWidth(), getHeight() - 10);
        txt.setForeground(color);
        add(txt);
        
        btnClose.addActionListener(e -> {
            setVisible(false);
            Main.getContentPane().remove(this);
            if (!movementPaused) {
                Main.getPlayer().unpauseMovement();
            }
        });
    }
}
