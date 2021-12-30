package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Fonts;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * ComboLock is used to display a simple 6-digit combination lock for stage 1.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class ComboLock extends JPanel {
    /**
     * Whether the user's answer is correct
     */
    private boolean correct;

    /**
     * Create the panel
     * @param combo String representation of the correct combination
     */
    public ComboLock(final String combo) {
        super();
        final JTextField input;
        final JLabel title;
        final JButton btnClose;
        final JLabel lblWrong;
        
        correct = false;
        Main.getPlayer().pauseMovement();
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 100, 300, 200);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        
        // Force only numeric digits, with a length up to 6
        input = new JTextField();
        ((AbstractDocument) input.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offset, int len, String str, AttributeSet a) throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength()) + str;
                if (text.matches("^[0-9]{0,6}$")) {
                    super.replace(fb, offset, len, str, a);
                }
            }
            
            @SuppressWarnings("unused")
            public void insertString(FilterBypass fb, int offset, int len, String str, AttributeSet a) throws BadLocationException {
                replace(fb, offset, len, str, a);
            }
        });
        input.setFont(Fonts.MONO);
        input.setBounds(getWidth() / 2 - 50, 85, 100, 30);
        add(input);
        
        title = new JLabel("ComboLock 8100");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Fonts.TITLE);
        title.setBounds(0, 0, getWidth(), 40);
        add(title);
        
        btnClose = new JButton("X");
        btnClose.setBackground(Color.LIGHT_GRAY);
        btnClose.setBorder(null);
        btnClose.setFocusPainted(false);
        btnClose.setFont(Fonts.CLOSE_BTN);
        btnClose.setBounds(262, 7, 28, 23);
        add(btnClose);
        
        lblWrong = new JLabel("That's the wrong combination...");
        lblWrong.setForeground(Color.RED);
        lblWrong.setFont(Fonts.STD_PARA);
        lblWrong.setHorizontalAlignment(SwingConstants.CENTER);
        lblWrong.setBounds(0, 149, getWidth(), 14);
        lblWrong.setVisible(false);
        add(lblWrong);
        btnClose.addActionListener(e -> {
            setVisible(false);
            Main.getContentPane().remove(this);
            Main.getPlayer().unpauseMovement();
            synchronized(this) {
                correct = false;
                notify();
            }
        });

        input.addActionListener(e -> {
            if (!input.getText().equals(combo)) {
                lblWrong.setVisible(true);
            } else {
                setVisible(false);
                Main.getContentPane().remove(this);
                Main.getPlayer().unpauseMovement();
                synchronized(this) {
                    correct = true;
                    notify();
                }
            }
        });
        Main.getContentPane().add(this, Layer.POPUP, 0);
    }
    
    /**
     * Wait for the user to exit or enter the correct combination
     * @return  whether the user entered the correct combination
     */
    public boolean run() {
        synchronized(this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return correct;
    }
}
