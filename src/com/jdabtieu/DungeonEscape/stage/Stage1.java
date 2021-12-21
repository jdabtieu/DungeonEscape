package com.jdabtieu.DungeonEscape.stage;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;

import com.jdabtieu.DungeonEscape.Main;
import com.jdabtieu.DungeonEscape.component.BasicPopup;
import com.jdabtieu.DungeonEscape.component.BossBanner;
import com.jdabtieu.DungeonEscape.component.Stage1_ComboLock;
import com.jdabtieu.DungeonEscape.core.Weapon;
import com.jdabtieu.DungeonEscape.map.Ground;
import com.jdabtieu.DungeonEscape.map.GroundWeapon;
import com.jdabtieu.DungeonEscape.map.HiddenSensor;
import com.jdabtieu.DungeonEscape.map.Sensor;
import com.jdabtieu.DungeonEscape.map.Text;
import com.jdabtieu.DungeonEscape.map.Wall;

public class Stage1 extends Stage {
    private boolean bossInit;
    private boolean comboLockEnabled;
    /**
     * Create the frame.
     * @throws IOException 
     */
    public Stage1() {
        super();
        bossInit = false;
        comboLockEnabled = false;
        fillStage("stage1");
        texts.add(new Text(">>> Where am I?", 880, 450));
        texts.add(new Text(">>> ...", 880, 470));
        texts.add(new Text(">>> Maybe I should open my eyes", 880, 490));
        texts.add(new Text(">>> This looks like my basement. But something's off.", 880, 510));
        texts.add(new Text(">>> Where am I?", 880, 530));
        texts.add(new Text("Tip: Sensors can trigger different actions", 1350, 550));
        texts.add(new Text("Tip: Watch out for spikes! They deal a ton of damage", 1220, 730));
        texts.add(new Text("Tip: Look around each room for room numbers and clue", 1040, 50));
        texts.add(new Text("1__34_", 870, 320));
        texts.add(new Text("_42__2", 710, 320));
        texts.add(new Text("Ooh, shiny! It's a weapon! Wonder what it is...", 1900, 96));
        texts.add(new Text("Tip: You can hold up to 3 weapons at a time. Any extras will be discarded.", 1900, 270));
        stage[3][126] = new Sensor(() -> initBoss());
        stage[4][126] = new Sensor(() -> initBoss());
        stage[5][126] = new Sensor(() -> initBoss());
        stage[29][74] = new Sensor(() -> changeTile(31, 74, Wall.class));
        stage[33][74] = new Sensor(() -> changeTile(31, 74, Ground.class));
        
        stage[3][14] = new HiddenSensor(() -> {
            if (comboLockEnabled) {
                comboLockEnabled = false;
                new Stage1_ComboLock();
            }
        });
        
        for (Point e : new Point[] {new Point(3, 12), new Point(4, 12), new Point(5, 12),
                                    new Point(5, 13), new Point(5, 14), new Point(5, 15),
                                    new Point(5, 16), new Point(4, 16), new Point(3, 16)}) {
            stage[e.x][e.y] = new HiddenSensor(() -> comboLockEnabled = true);
        }
        
        stage[3][73] = new HiddenSensor(() -> {
            changeTile(3, 73, Ground.class);
            new BasicPopup("You found a key!", Color.BLACK);
            redraw();
            Main.player.keys++;
        }, Color.YELLOW);
        
        stage[6][100] = new GroundWeapon(() -> {
            Main.player.addWeapon(new Weapon("Wooden Axe", 3, 30, "wood_axe.png"));
            changeTile(6, 100, Ground.class);
            redraw();
        });

        
        generateImg("stage1");
        finishConstructor();
    }
    
    private void initBoss() {
        if (bossInit || stage[6][100] instanceof GroundWeapon) return;
        bossInit = true;
        changeTile(3, 124, Wall.class);
        changeTile(4, 124, Wall.class);
        changeTile(5, 124, Wall.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.player.x = 2760;
        Main.player.y = 184;
        pauseMovement = true;
        redraw();
        BossBanner bb = new BossBanner();
        add(bb);
        bb.animate();
        remove(bb);
    }
    
    public void correctCombo() {
        changeTile(2, 14, Ground.class);
        changeTile(3, 14, Ground.class);
        redraw();
    }
}
