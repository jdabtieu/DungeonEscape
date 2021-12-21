import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jdabtieu.DungeonEscape.core.Window;

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
        
        JLabel title = new JLabel();
        title.setBounds(0, 0, 40, 40);
        
        BufferedImage titleText;
        try {
            titleText = ImageIO.read(new File("assets/weapon/wood_xe.png"));
            title.setIcon(new ImageIcon(titleText));
        } catch (IOException e) {
            e.printStackTrace();
            BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setStroke(new BasicStroke(4));
            g.setColor(Color.RED);
            g.drawLine(0, 0, 40, 40);
            g.drawLine(0, 40, 40, 0);
            title.setIcon(new ImageIcon(img));
            
        }
        contentPane.add(title);
    }
}
