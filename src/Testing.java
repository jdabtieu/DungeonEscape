import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jdabtieu.DungeonEscape.core.Window;
import com.jdabtieu.DungeonEscape.tile.Text;

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
        contentPane.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        getContentPane().add(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GREEN);
        
        Text txt = new Text("Interview Room", 100, 100);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txt.setBounds(100, 100, 148, 40);
        txt.setFont(new Font("Sitka Text", Font.BOLD, 16));
        contentPane.add(txt);
    }
}
