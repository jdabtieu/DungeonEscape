import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.core.Player;
import com.jdabtieu.DungeonEscape.core.Weapon;
import com.jdabtieu.DungeonEscape.core.Window;
import javax.swing.SwingConstants;

public class Testing extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Testing frame = new Testing();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Testing() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, Window.WIDTH, Window.HEIGHT);
        getContentPane().setLayout(null);
        contentPane = new JPanel();
        contentPane.setBounds(Window.WIDTH / 2 - 90, Window.HEIGHT / 2 - 120, 180, 240);
        getContentPane().add(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GREEN);
        
        JLabel title = new JLabel("Choose a Weapon");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Sitka Text", Font.PLAIN, 14));
        title.setBounds(0, 0, 180, 30);
        contentPane.add(title);
        
        Player p = new Player();
        try {
            p.getWeapons().add(new Weapon("Wooden Axe", 3, 30, "wood_axe.png"));
        } catch (Exception e) {}
        try {
            p.getWeapons().add(new Weapon("Stone Axe", 10, 30, "wood_axe.png"));
        } catch (Exception e) {}
        
        ArrayList<Weapon> weapons = p.getWeapons();
        for (int i = 0; i < weapons.size(); i++) {
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBounds(20, 60 * i + 30, 140, 60);
            container.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            Weapon wp = weapons.get(i).clone();
            wp.setBounds(0, 10, 40, 40);
            JLabel lab = new JLabel("<html>" + wp.toString() + "</html>");
            lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lab.setBounds(40, 0, 200, 60);
            container.add(wp);
            container.add(lab);
            container.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    //p.setActiveWeapon(weapons.get(i));
                    container.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });
            contentPane.add(container);
        }
    }
}
