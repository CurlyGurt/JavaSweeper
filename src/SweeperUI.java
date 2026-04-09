import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;



public class SweeperUI extends Frame implements WindowListener, ActionListener {
    int WINDOWX = 1000;
    int WINDOWY = 1000;
    int buttonSize = 50;
    Border loweredBevel, raisedBevel, buttonBevel;
    int fontSize = buttonSize/2 + 5;
    JButton faceButton;
    JButton[][] buttonArr;
    Grid uiGrid;

    public void ShowUI(Grid mainGrid)
    {
        JFrame frame = new JFrame("JavaSweeper");
        JPanel headerPanel = new JPanel();
        
        buttonArr = new JButton[mainGrid.xSize][mainGrid.ySize];
        uiGrid = mainGrid;

        WINDOWX = 35+buttonSize*mainGrid.xSize;
        WINDOWY = 110+buttonSize*mainGrid.ySize;
        System.out.println("WindowX = " + WINDOWX);
        System.out.println("WindowY = " + WINDOWY);


        loweredBevel = BorderFactory.createLoweredBevelBorder();
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        //buttonBevel = BorderFactory.createBevelBorder();

        headerPanel.setBorder(loweredBevel);
        headerPanel.setBounds(5,5,WINDOWX-25, 50);

        faceButton = new JButton(":)");
        faceButton.setBounds((WINDOWX-35-20)/2, 10, 40,40);
        faceButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        faceButton.setBorder(raisedBevel);

        frame.add(headerPanel);
        frame.add(faceButton);

        for(int i = 0; i < mainGrid.xSize; i++)
        {
            for(int j = 0; j < mainGrid.ySize; j++)
            {
                buttonArr[i][j] = new JButton();
                buttonArr[i][j].setBounds(10+(buttonSize*i), 60+(buttonSize*j), buttonSize, buttonSize);
                //buttonArr[i][j].setMargin(new Insets(0, 0, 0, 0));
                buttonArr[i][j].setFont(new Font("Arial", Font.PLAIN, fontSize));
                buttonArr[i][j].setBorder(raisedBevel);
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
                buttonArr[i][j].setBorder(loweredBevel);
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
        buttonArr[xInd][yInd].setEnabled(false);
        if(uiGrid.tileBoard[yInd][xInd].nearbyBombs > 0) buttonArr[xInd][yInd].setText(Integer.toString(uiGrid.tileBoard[yInd][xInd].nearbyBombs));
        buttonArr[xInd][yInd].setBorder(loweredBevel);
        uiGrid.tileBoard[yInd][xInd].isRevealed = true;
        System.out.println("rAT (xInd,yInd) = " + "(" + xInd + "," + yInd + ")");
        //System.out.println("buttonArr[xInd].length = " + buttonArr[xInd].length + "buttonArr.length = " + buttonArr.length);

        //Check if bomb
        if(uiGrid.tileBoard[yInd][xInd].isBomb)
        {
            faceButton.setText(":(");
            revealAllTiles();
        }

        else if(uiGrid.tileBoard[yInd][xInd].nearbyBombs == 0)
        {
            //check up
            if(yInd > 0)
            {
                if(!uiGrid.tileBoard[yInd-1][xInd].isBomb && !uiGrid.tileBoard[yInd-1][xInd].isRevealed) revealAdjacentTiles(xInd, yInd-1);
            }

            //check down
            if(yInd < buttonArr[xInd].length-1)
            {
                if(!uiGrid.tileBoard[yInd+1][xInd].isBomb && !uiGrid.tileBoard[yInd+1][xInd].isRevealed) revealAdjacentTiles(xInd, yInd+1);
            }

            //check left
            if(xInd > 0)
            {
                if(!uiGrid.tileBoard[yInd][xInd-1].isBomb && !uiGrid.tileBoard[yInd][xInd-1].isRevealed) revealAdjacentTiles(xInd-1, yInd);
            }

            //check right
            if(xInd < buttonArr.length-1)
            {
                if(!uiGrid.tileBoard[yInd][xInd+1].isBomb && !uiGrid.tileBoard[yInd][xInd+1].isRevealed) revealAdjacentTiles(xInd+1, yInd);
            }
            
            //-------DIAGONALS-------

            //Check up-left
            if(yInd > 0 && xInd > 0)
            {
                if(!uiGrid.tileBoard[yInd-1][xInd-1].isBomb && !uiGrid.tileBoard[yInd-1][xInd-1].isRevealed) revealAdjacentTiles(xInd-1, yInd-1);
            }

            //check down-left
            if(yInd < buttonArr[xInd].length-1 && xInd > 0)
            {
                if(!uiGrid.tileBoard[yInd+1][xInd-1].isBomb && !uiGrid.tileBoard[yInd+1][xInd-1].isRevealed) revealAdjacentTiles(xInd-1, yInd+1);
            }

            //check up-right
            if(yInd > 0 && xInd < buttonArr.length-1)
            {
                if(!uiGrid.tileBoard[yInd-1][xInd+1].isBomb && !uiGrid.tileBoard[yInd-1][xInd+1].isRevealed) revealAdjacentTiles(xInd+1, yInd-1);
            }

            //check down-right
            if(yInd < buttonArr[xInd].length-1 && xInd < buttonArr.length-1)
            {
                if(!uiGrid.tileBoard[yInd+1][xInd+1].isBomb && !uiGrid.tileBoard[yInd+1][xInd+1].isRevealed) revealAdjacentTiles(xInd+1, yInd+1);
            }
        }
    }

    public void actionPerformed(ActionEvent e) 
    {
        //System.out.println(e.getActionCommand());
        //System.out.println(e.getSource() + "\n");
        //JButton tempButton = (JButton)e.getSource();

        for(int i = 0; i < buttonArr.length; i++)
        {
            for(int j = 0; j < buttonArr[i].length; j++)
            {
                if(buttonArr[i][j] == (JButton)e.getSource())
                {
                    System.out.println("button found at (" + i + "," + j + ")");
                    revealAdjacentTiles(i, j);
                    break;
                }
            }
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

/*//check up
        

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
        }*/