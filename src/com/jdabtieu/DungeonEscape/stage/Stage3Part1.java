package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.HiddenSensor;
import com.jdabtieu.DungeonEscape.tile.Text;

public class Stage3Part1 extends Stage {
    private boolean ambushInit;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage3Part1() {
        super();
        Main.getPlayer().setLocation(360, 1750);
        ambushInit = false;
        fillStage("stage3_1");
        texts.add(new Text(">>> I can see clearly now the light is here", 40, 1500));
        texts.add(new Text(">>> I can see all obstacles in the way", 40, 1520));
        texts.add(new Text(">>> And I can see that this stage is the last one", 40, 1540));
        texts.add(new Text("Tip: Weapons can't be switched out mid-fight, so make sure your weapon has enough durability.", 30, 1760));
        stage[69][16] = new HiddenSensor(() -> initAmbush());
        stage[69][17] = new HiddenSensor(() -> initAmbush());
        stage[69][18] = new HiddenSensor(() -> initAmbush());
        stage[30][16] = new HiddenSensor(() -> initDrop());
        stage[30][17] = new HiddenSensor(() -> initDrop());
        stage[30][18] = new HiddenSensor(() -> initDrop());
        
        finishConstructor();
    }
    
    private void initAmbush() {
        if (ambushInit) return;
        ambushInit = true;
        Main.getPlayer().setLocation(340, 1340);
        Main.getPlayer().pauseMovement();
        changeTile(69, 16, Ground.class);
        changeTile(69, 17, Ground.class);
        changeTile(69, 18, Ground.class);
        redraw();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Banner bb = new Banner("AMBUSH!");
        Main.getContentPane().add(bb, Layer.ENEMY, 0);
        bb.animate();
        Main.getContentPane().remove(bb);
        JLabel enemy;
        try {
            enemy = new JLabel(new ImageIcon(ImageIO.read(new File("assets/ambush.png"))));
        } catch (IOException e) {
            enemy = new JLabel();
        }
        enemy.setBounds(156, 90, 740, 320);
        Main.getContentPane().add(enemy, Layer.ENEMY, 0);
        Point[] offsets = {new Point(115, 4), new Point(38, 60), new Point(40, 190),
                           new Point(172, 161), new Point(260, 63), new Point(304, 184),
                           new Point(375, 16), new Point(520, 72), new Point(483, 177)};
        HealthBar[] healthBars = new HealthBar[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            healthBars[i] = new HealthBar(11);
            healthBars[i].setBounds(enemy.getX() + offsets[i].x, enemy.getY() + offsets[i].y, 80, 20);
            Main.getContentPane().add(healthBars[i], Layer.ENEMY, 0);
        }
        Main.getPlayer().weaponSelect(mon);
        pause();
        for (int i = 0; i < offsets.length; i++) {
            fight(11, healthBars[i], () -> (int) (Math.random() + 0.5) * (int) (Math.random() * 5 + 1));
        }
        Main.getPlayer().unpauseMovement();
        new BasicPopup("You defeated the enemies!", Color.BLACK);
        
        for (int i = 0; i < offsets.length; i++) {
            healthBars[i].setVisible(false);
            Main.getContentPane().remove(healthBars[i]);
        }
        enemy.setVisible(false);
        Main.getContentPane().remove(enemy);
        redraw();
    }
    
    private void initDrop() {
        finish();
        synchronized(Main.mon) {
            Main.mon.notify();
        }
    }
}
