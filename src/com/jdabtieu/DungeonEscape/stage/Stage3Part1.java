package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.Banner;
import com.jdabtieu.DungeonEscape.component.BasicConfirm;
import com.jdabtieu.DungeonEscape.component.BasicDialog;
import com.jdabtieu.DungeonEscape.component.HealthBar;
import com.jdabtieu.DungeonEscape.core.Layer;
import com.jdabtieu.DungeonEscape.tile.Ground;
import com.jdabtieu.DungeonEscape.tile.HiddenSensor;
import com.jdabtieu.DungeonEscape.tile.Sensor;
import com.jdabtieu.DungeonEscape.tile.Text;
/**
 * Code for stage 3 part 1
 *
 * @author Jonathan Wu (jonathan.wu3@student.tdsb.on.ca)
 * @date 2022-01-01
 */
public class Stage3Part1 extends Stage {
    /**
     * Whether the ambush has been initiated
     */
    private boolean ambushInit;
    
    /**
     * Whether the vending machine is active
     */
    private boolean activeVending;
    
    /**
     * Create the stage 
     */
    public Stage3Part1() {
        super("stage3_1");
        Main.getPlayer().setPosition(360, 1750);
        ambushInit = false;
        activeVending = false;
        texts.add(new Text(">>> I can see clearly now the end is near", 40, 1500));
        texts.add(new Text(">>> I can see all obstacles in the way", 40, 1520));
        texts.add(new Text(">>> And I can see that this stage is the last one", 40, 1540));
        texts.add(new Text("Tip: You can't walk through spikes. And they really hurt.", 30, 1760));
        stage[69][16] = new HiddenSensor(() -> initAmbush());
        stage[69][17] = new HiddenSensor(() -> initAmbush());
        stage[69][18] = new HiddenSensor(() -> initAmbush());
        stage[30][16] = new HiddenSensor(() -> initDrop());
        stage[30][17] = new HiddenSensor(() -> initDrop());
        stage[30][18] = new HiddenSensor(() -> initDrop());
        stage[79][32] = new HiddenSensor(() -> activeVending = true);
        stage[80][31] = new HiddenSensor(() -> activeVending = true);
        stage[81][31] = new HiddenSensor(() -> activeVending = true);
        stage[82][31] = new HiddenSensor(() -> activeVending = true);
        stage[83][32] = new HiddenSensor(() -> activeVending = true);
        stage[81][33] = new Sensor(() -> vendingMachine());
        
        finishConstructor();
    }
    
    /**
     * Code for the vending machine
     */
    private void vendingMachine() {
        if (!activeVending) return;
        activeVending = false;
        Main.getPlayer().pauseMovement();
        if (new BasicConfirm("<html>For 700 coins, Vending Machine offers:<br>" + 
                             "5 HP health potion<br>" +
                             "Would you like to purchase it?</html>").selection()) {
            try {
                Main.getPlayer().useCoins(700);
                Main.getPlayer().changeHealth(5);
            } catch (IllegalArgumentException e) {
                new BasicDialog("You don't have enough coins!", Color.RED).selection();
            }
        }
        Main.getPlayer().unpauseMovement();
    }
    
    /**
     * Code for the ambush
     */
    private void initAmbush() {
        if (ambushInit) return;
        final JLabel enemy = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("assets/ambush.png")));
        final Point[] offsets = {new Point(115, 4), new Point(38, 60), new Point(40, 190),
                new Point(172, 161), new Point(260, 63), new Point(304, 184),
                new Point(375, 16), new Point(520, 72), new Point(483, 177)};
        final HealthBar[] healthBars = new HealthBar[offsets.length];

        ambushInit = true;
        changeTile(69, 16, Ground.class);
        changeTile(69, 17, Ground.class);
        changeTile(69, 18, Ground.class);
        Main.safeSleep(200);
        setPlayerPosition(340, 1340);
        Main.getPlayer().pauseMovement();
        new Banner("AMBUSH!").animate();
        
        enemy.setBounds(156, 90, 740, 320);
        Main.getContentPane().add(enemy, Layer.ENEMY, 0);
        
        for (int i = 0; i < offsets.length; i++) {
            healthBars[i] = new HealthBar(11);
            healthBars[i].setBounds(enemy.getX() + offsets[i].x, enemy.getY() + offsets[i].y, 80, 20);
            Main.getContentPane().add(healthBars[i], Layer.ENEMY, 0);
        }
        
        Main.getPlayer().weaponSelect();
        
        for (final HealthBar bar : healthBars) {
            fight(bar, () -> (int) (Math.random() + 0.5) * (int) (Math.random() * 5 + 1));
        }
        
        Main.getPlayer().unpauseMovement();
        new BasicDialog("You defeated the enemies!").selection();
        
        for (final HealthBar bar : healthBars) {
            bar.setVisible(false);
            Main.getContentPane().remove(bar);
        }
        enemy.setVisible(false);
        Main.getContentPane().remove(enemy);
    }
    
    /**
     * End of part 1: player drops into part 2
     */
    private void initDrop() {
        finish();
        synchronized(Main.mon) {
            Main.mon.notify();
        }
    }
}
