import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.BorderFactory;

public class difficultyUI extends Frame implements WindowListener, ActionListener {
    int xSize = 9; //default to easy
    int ySize = 9;
    int bombs = 10;
    JFrame diffFrame;

    public void showDifficultyWindow()
    {
        diffFrame = new JFrame("JavaSweeper: Select Your Difficulty");
        diffFrame.addWindowListener(this);
        GridLayout layout = new GridLayout(0, 1);
        JButton startButton = new JButton("Start");
        JRadioButton easyDiff = new JRadioButton("Easy (9x9, 10 Mines)");
        JRadioButton medDiff = new JRadioButton("Medium (16x16, 40 Mines)");
        JRadioButton hardDiff = new JRadioButton("Hard (30x16, 99 Mines)");
        //JRadioButton custDiff = new JRadioButton("Custom ");

        //easy selected by default
        easyDiff.setSelected(true);

        startButton.setActionCommand("start,0,0,0");
        easyDiff.setActionCommand("difficulty,9,9,10");
        medDiff.setActionCommand("difficulty,16,16,40");
        hardDiff.setActionCommand("difficulty,30,16,99");
        //custDiff.setActionCommand("9, 9, 10");

        //Groups the radio buttons together so that only one can be selected at a time.
        ButtonGroup buttGroup = new ButtonGroup();
        buttGroup.add(easyDiff);
        buttGroup.add(medDiff);
        buttGroup.add(hardDiff);

        startButton.addActionListener(this);
        easyDiff.addActionListener(this);
        medDiff.addActionListener(this);
        hardDiff.addActionListener(this);

        JPanel buttPanel = new JPanel(layout);
        buttPanel.add(easyDiff);
        buttPanel.add(medDiff);
        buttPanel.add(hardDiff);
        buttPanel.add(startButton);
        buttPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        //JPanel startPanel = new JPanel(layout);
        //startPanel.add(startButton);
        //startPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        diffFrame.add(buttPanel, BorderLayout.LINE_START);
        //diffFrame.add(startPanel, BorderLayout.CENTER);
        diffFrame.pack();
        diffFrame.setVisible(true);
    }

     public void actionPerformed(ActionEvent e) 
    {
        //splits string from a buttons ActionCommand into an array of strings
        String[] action = e.getActionCommand().split(",");
        
        if(action[0].equals("difficulty"))
        {
            xSize = Integer.parseInt(action[1]);
            ySize = Integer.parseInt(action[2]);
            bombs = Integer.parseInt(action[3]);
        }
        else if(action[0].equals("start")) 
        {
            Grid mainGrid = new Grid(xSize,ySize);
            mainGrid.placeBombs(bombs);
            mainGrid.findBombsInProx();

            System.out.println("----------Generated Grid----------- \n" + mainGrid); //displays grid in terminal for testing purposes
            SweeperUI ui = new SweeperUI();
            ui.ShowUI(mainGrid);
            diffFrame.setVisible(false);
        }
    }

    public void windowClosing(WindowEvent e) 
    {
        dispose();
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) 
    {
        dispose();
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
