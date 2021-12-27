import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
        contentPane.setBounds(0, 0, Window.WIDTH / 2, 200);
        getContentPane().add(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GREEN);
        
        String title = "Guess the Phrase (80s Song Lyrics)";
        String ans = "vector";
        
        Text txt = new Text(title, 0, 10);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setBounds(0, 10, contentPane.getWidth(), 40);
        txt.setFont(new Font("Sitka Text", Font.BOLD, 16));
        contentPane.add(txt);
        
        ArrayList<JTextField> letters = new ArrayList<>();
        for (int i = 0; i < ans.length(); i++) {
            if (ans.charAt(i) == ' ') continue;
            JTextField f = new JTextField();
            f.setBounds((10 + 40 * i) % contentPane.getWidth(), 60 * ((10 + 40 * i) / contentPane.getWidth() + 1), 30, 30);
            letters.add(f);
            contentPane.add(f);
        }
        
        ans = ans.replace(" ", "");
        
        JButton submit = new JButton("Submit");
        submit.setBounds(contentPane.getWidth() / 2 - 50, 160, 100, 30);
        contentPane.add(submit);
        submit.addActionListener(e -> {
            synchronized(this) {
                notify();
            }
        });
        
    }
}
