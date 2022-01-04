package com.jdabtieu.DungeonEscape.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.core.Music;
import com.jdabtieu.DungeonEscape.core.Window;
/**
 * VolumeSlider controls the volume of the music.
 * 
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class VolumeSlider extends JSlider {
    /**
     * Creates the slider
     */
    public VolumeSlider() {
        super(0, 100, 81);
        setOpaque(false);
        setBounds(Window.WIDTH - 140, 10, 120, 40);
        addChangeListener(e -> Music.changeVolume(getValue()));
        addVolumeIcon();
    }
    
    /**
     * Adds a volume icon next to the slider
     */
    private void addVolumeIcon() {
        final JLabel img = new JLabel();
        final BufferedImage i = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = (Graphics2D) i.getGraphics();
        g.setColor(new Color(40, 40, 40));
        g.fillRect(10, 10, 10, 10);
        g.fillPolygon(new int[] {20, 20, 30, 30}, new int[] {10, 20, 30, 0}, 4);
        img.setIcon(new ImageIcon(i));
        img.setBounds(Window.WIDTH - 175, 13, 30, 30);
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(getValue() == 0) {
                    setValue(80);
                } else {
                    setValue(0);
                }
            }
        });
        Main.getContentPane().add(img, Layer.POPUP, 0);
    }
}