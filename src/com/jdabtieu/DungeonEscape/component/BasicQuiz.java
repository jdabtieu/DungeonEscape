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
public class BasicQuiz extends JPanel {
    private Object mon;
    private int ans;
    private int selection;
    
    public BasicQuiz(String text, int ans, String... answers) { 
        mon = new Object();
        setBounds(Window.WIDTH / 2 - 100, Window.HEIGHT / 2 - 150, 200, 300);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(false);
        Main.getContentPane().add(this, Layer.POPUP, 0);
        
        this.ans = ans;
        
        JLabel txt = new JLabel("<html>" + text + "</html");
        txt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txt.setBounds(5, 5, getWidth() - 10, 50);
        txt.setForeground(Color.BLACK);
        add(txt);
        
        for (int i = 0; i < answers.length; i++) {
            final int f = i;
            JLabel lab = new JLabel("<html>" + answers[i] + "</html>");
            lab.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLUE), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lab.setBounds(30, 50 * i + 80, 140, 44);
            lab.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    synchronized(mon) {
                        selection = f;
                        mon.notify();
                    }
                }
            });
            add(lab);
        }
    }
    
    public boolean selection() {
        setVisible(true);
        try {
            synchronized(mon) {
                mon.wait();
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
