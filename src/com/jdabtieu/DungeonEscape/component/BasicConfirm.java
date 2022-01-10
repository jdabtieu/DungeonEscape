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
    public BasicConfirm(final String text) {
        super();
        final JLabel txt = new JLabel(text);
        final JButton btnYes = new JButton("Yes");
        final JButton btnNo = new JButton("No");
        
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 75, 300, 150);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(false);
        Main.getContentPane().add(this, Layer.POPUP, 0);
        
        // add confirmation text
        txt.setFont(Fonts.STD_PARA);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(0, 11, getWidth(), getHeight() - 75);
        txt.setForeground(Color.BLACK);
        add(txt);
        
        // add buttons
        btnYes.setFont(Fonts.STD_PARA);
        btnYes.setBounds(30, getHeight() - 34, 80, 24);
        add(btnYes);
        btnYes.addActionListener(e -> {
            synchronized(this) {
                selection = true;
                notify();
            }
        });
        
        btnNo.setFont(Fonts.STD_PARA);
        btnNo.setBounds(getWidth() - 110, getHeight() - 34, 80, 24);
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
     * @return  true if the user clicked yes, otherwise no
     */
    public boolean selection() {
        boolean movementPaused = Main.getPlayer().movementPaused();
        Main.getPlayer().pauseMovement();
        setVisible(true);
        synchronized(this) {
            try {
                wait(); // wait for a button press
            } catch (InterruptedException e) {
                selection = false;
            }
        }
        if (!movementPaused) Main.getPlayer().unpauseMovement();
        
        // remove this popup once player makes a selection
        Main.getContentPane().remove(this);
        Main.getContentPane().repaint();
        return selection;
    }
}
