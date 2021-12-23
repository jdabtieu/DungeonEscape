package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * BasicConfirm is used to display a simple text-based confirmation popup in the popup layer.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class BasicConfirm extends JPanel {
    /**
     * Stores the selection of this dialog, when the user selects an option.
     */
    private boolean selection;
    
    /**
     * Creates the popup
     * @param text  text to be displayed
     * @param color color of the text
     */
    public BasicConfirm(String text) {        
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 75, 300, 150);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(false);
        Main.getContentPane().add(this, 5, 0);
        
        JLabel txt = new JLabel(text);
        txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(0, 11, 300, 75);
        txt.setForeground(Color.BLACK);
        add(txt);
        
        JButton btnYes = new JButton("Yes");
        btnYes.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnYes.setBounds(30, 116, 80, 24);
        add(btnYes);
        btnYes.addActionListener(e -> {
            synchronized(this) {
                selection = true;
                notify();
            }
        });
        
        JButton btnNo = new JButton("No");
        btnNo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNo.setBounds(getWidth() - 110, 116, 80, 24);
        add(btnNo);
        btnNo.addActionListener(e -> {
            synchronized(this) {
                selection = false;
                notify();
            }
        });
    }
    
    /**
     * Wait for the user to make a selection, and return it.
     * @return  whether the user clicked Yes
     */
    public boolean selection() {
        Main.getPlayer().pauseMovement();
        setVisible(true);
        try {
            synchronized(this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            selection = false;
        }
        
        Main.getPlayer().unpauseMovement();
        Main.getContentPane().remove(this);
        Main.getContentPane().repaint();
        return selection;
    }
}
