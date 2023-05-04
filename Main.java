import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("EDD2 - Lab 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("No sirvio FlatLaf");
        }
        Color borderColor = Color.gray;
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor));


        ImageIcon icon = new ImageIcon("big-data.png");
        Image image = icon.getImage();
        frame.setIconImage(image);
        GraphPanel graphPanel = new GraphPanel();
        frame.add(graphPanel);
        frame.setVisible(true);

        
    }
}