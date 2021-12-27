package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Font;

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
import com.jdabtieu.DungeonEscape.core.Window;

public class ComboLock extends JPanel {
    private boolean correct;

    /**
     * Create the panel.
     */
    public ComboLock(String combo) {
        correct = false;
        Main.getPlayer().pauseMovement();
        setBounds(Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 100, 300, 200);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        Main.getContentPane().add(this, 5, 0);
        
        JTextField input = new JTextField();
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
        input.setFont(new Font("Consolas", Font.PLAIN, 14));
        input.setBounds(100, 85, 100, 30);
        add(input);
        
        JLabel title = new JLabel("ComboLock 8100");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Sitka Text", Font.BOLD, 12));
        title.setBounds(0, 0, 300, 40);
        add(title);
        
        JButton btnClose = new JButton("X");
        btnClose.setBackground(Color.LIGHT_GRAY);
        btnClose.setBorder(null);
        btnClose.setFocusPainted(false);
        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnClose.setBounds(262, 7, 28, 23);
        add(btnClose);
        
        JLabel lblWrong = new JLabel("That's the wrong combination...");
        lblWrong.setForeground(Color.RED);
        lblWrong.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblWrong.setHorizontalAlignment(SwingConstants.CENTER);
        lblWrong.setBounds(0, 149, 300, 14);
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
    }
    
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
