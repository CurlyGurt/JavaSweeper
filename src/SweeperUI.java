import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SweeperUI {

    public static void ShowUI() {
        JFrame frame = new JFrame("JavaSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200,20));

        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(new Color(248,213,131));
        yellowLabel.setPreferredSize(new Dimension(200, 180));

        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

    }
}