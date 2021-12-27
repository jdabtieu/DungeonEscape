package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * BasicQuiz is used to display a multiple-choice quiz question, for the stage 2 interview
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class BasicQuiz extends JPanel {
    /**
     * Stores the index of the correct answer
     */
    private int ans;
    
    /**
     * Stores the index of the user-selected answer
     */
    private int selection;
    
    /**
     * Creates a quiz question
     * @param text      the question
     * @param ans       the index of the correct answer
     * @param answers   a list of answer choices
     */
    public BasicQuiz(String text, int ans, String... answers) {
        setBounds(Window.WIDTH / 2 - 100, Window.HEIGHT / 2 - 150, 200, 300);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(false);
        Main.getContentPane().add(this, Layer.POPUP, 0);
        
        this.ans = ans;
        
        JLabel txt = new JLabel("<html>" + text + "</html>");
        txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txt.setBounds(5, 5, getWidth() - 10, 50);
        txt.setForeground(Color.BLACK);
        add(txt);
        
        for (int i = 0; i < answers.length; i++) {
            final int f = i;
            final BasicQuiz c = this;
            JLabel lab = new JLabel("<html>" + answers[i] + "</html>");
            lab.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLUE), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lab.setBounds(30, 50 * i + 80, 140, 44);
            lab.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    synchronized(c) {
                        selection = f;
                        c.notify();
                    }
                }
            });
            add(lab);
        }
    }
    
    /**
     * Displays the question and waits for the user to select an answer
     * @return  whether the player's answer is correct
     */
    public boolean selection() {
        setVisible(true);
        try {
            synchronized(this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        
        Main.getContentPane().remove(this);
        Main.getContentPane().repaint();
        return selection == ans;
    }
}
