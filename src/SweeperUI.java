import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;



public class SweeperUI {
    static int WINDOWX = 1000;
    static int WINDOWY = 1000;

    /*public static void ShowUI() {
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

    }*/

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        JPanel timerPanel = new JPanel();
        Border loweredBevel, raisedBevel;
        //Border paneEdge = BorderFactory.createEmptyBorder(0,10,10,10);
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        raisedBevel = BorderFactory.createRaisedBevelBorder();

        timerPanel.setBorder(loweredBevel);
        //addCompForBorder(loweredbevel, "lowered bevel border", timerPanel);
        timerPanel.setBounds(5,5,WINDOWX-30, 50);

        JButton faceButton = new JButton(":)");
        faceButton.setBounds((WINDOWX-35-20)/2, 10, 40,40);
        faceButton.setBorder(raisedBevel);
        //timerPanel.add(faceButton);

        frame.add(timerPanel);
        frame.add(faceButton);

        /*JButton button = new JButton("Start the game already!!!");
        button.setBounds(50,50,100,100);
        frame.add(button);*/

        frame.setSize(WINDOWX,WINDOWY);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}