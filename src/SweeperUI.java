import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;



public class SweeperUI extends Frame implements WindowListener, ActionListener {
    static int WINDOWX = 1000;
    static int WINDOWY = 1000;
    JButton faceButton;
    JButton[][] buttonArr;
    Grid uiGrid;

    public void ShowUI(Grid mainGrid)
    {
        JFrame frame = new JFrame("JavaSweeper");
        JPanel headerPanel = new JPanel();
        buttonArr = new JButton[mainGrid.xSize][mainGrid.ySize];
        uiGrid = mainGrid;
        Border loweredBevel, raisedBevel;
        int buttonSize = 50;


        loweredBevel = BorderFactory.createLoweredBevelBorder();
        raisedBevel = BorderFactory.createRaisedBevelBorder();

        headerPanel.setBorder(loweredBevel);
        headerPanel.setBounds(5,5,WINDOWX-30, 50);

        faceButton = new JButton(":)");
        faceButton.setBounds((WINDOWX-35-20)/2, 10, 40,40);
        faceButton.setFont(new Font("Arial", Font.PLAIN, 25));
        faceButton.setBorder(raisedBevel);

        frame.add(headerPanel);
        frame.add(faceButton);

        /*for(int i = 0; i < mainGrid.xSize; i++)
        {
            for(int j = 0; j < mainGrid.ySize; j++)
            {
                JButton forTile = new JButton(Integer.toString(mainGrid.tileBoard[j][i].nearbyBombs));
                forTile.setBounds(10+(buttonSize*i), 60+(buttonSize*j), buttonSize, buttonSize);
                //forTile.setActionCommand(forTile.toString());
                forTile.addActionListener(this);
                frame.add(forTile);
            }
        }*/

        for(int i = 0; i < mainGrid.xSize; i++)
        {
            for(int j = 0; j < mainGrid.ySize; j++)
            {
                buttonArr[i][j] = new JButton();
                buttonArr[i][j].setBounds(10+(buttonSize*i), 60+(buttonSize*j), buttonSize, buttonSize);
                buttonArr[i][j].setFont(new Font("Arial", Font.PLAIN, 25));
                //forTile.setActionCommand(forTile.toString());
                buttonArr[i][j].addActionListener(this);
                frame.add(buttonArr[i][j]);
            }
        }

        frame.setSize(WINDOWX,WINDOWY);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void revealAllTiles()
    {
        for(int i = 0; i < buttonArr.length; i++)
        {
            for(int j = 0; j < buttonArr[i].length; j++)
            {
                buttonArr[i][j].setEnabled(false);
                if(uiGrid.tileBoard[j][i].isBomb) buttonArr[i][j].setText("*");
                else
                {
                    if(uiGrid.tileBoard[j][i].nearbyBombs > 0) buttonArr[i][j].setText(Integer.toString(uiGrid.tileBoard[j][i].nearbyBombs));
                }
            }
        }
    }

    public void revealAdjacentTiles(int xInd, int yInd) 
    {
        //check up
        

        //check down

        //check left
        if(xInd > 0)
        {
            if(!uiGrid.tileBoard[yInd][xInd-1].isBomb && !uiGrid.tileBoard[yInd][xInd-1].isRevealed)
            {
                buttonArr[xInd-1][yInd].setEnabled(false);
                uiGrid.tileBoard[yInd][xInd-1].isRevealed = true;
                if(uiGrid.tileBoard[yInd][xInd-1].nearbyBombs > 0) buttonArr[xInd-1][yInd].setText(Integer.toString(uiGrid.tileBoard[yInd][xInd-1].nearbyBombs));
                if(uiGrid.tileBoard[yInd][xInd].nearbyBombs == 0) revealAdjacentTiles(xInd-1, yInd);
            }
        }

        //check right
        if(xInd < buttonArr.length)
        {
            if(!uiGrid.tileBoard[yInd][xInd+1].isBomb && !uiGrid.tileBoard[yInd][xInd+1].isRevealed)
            {
                buttonArr[xInd+1][yInd].setEnabled(false);
                uiGrid.tileBoard[yInd][xInd+1].isRevealed = true;
                if(uiGrid.tileBoard[yInd][xInd+1].nearbyBombs > 0) buttonArr[xInd+1][yInd].setText(Integer.toString(uiGrid.tileBoard[yInd][xInd+1].nearbyBombs));
                if(uiGrid.tileBoard[yInd][xInd].nearbyBombs == 0) revealAdjacentTiles(xInd+1, yInd);
            }
        }
    }

    public void actionPerformed(ActionEvent e) 
    {
        //System.out.println(e.getActionCommand());
        //System.out.println(e.getSource() + "\n");
        JButton tempButton = (JButton)e.getSource();

        for(int i = 0; i < buttonArr.length; i++)
        {
            for(int j = 0; j < buttonArr[i].length; j++)
            {
                if(buttonArr[i][j] == (JButton)e.getSource())
                {
                    uiGrid.tileBoard[j][i].isRevealed = true;

                    if(uiGrid.tileBoard[j][i].isBomb) 
                    {
                        tempButton.setText("*");
                        faceButton.setText(":(");
                        revealAllTiles();
                    }
                    else 
                    {
                        if(uiGrid.tileBoard[j][i].nearbyBombs > 0) tempButton.setText(Integer.toString(uiGrid.tileBoard[j][i].nearbyBombs));
                        else revealAdjacentTiles(i, j);
                    }
                    break;
                }
            }
        }
        tempButton.setEnabled(false);
    }

    public void windowClosing(WindowEvent e) 
    {
        dispose();
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}