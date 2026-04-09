import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
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
        //Window Setup
        JFrame frame = new JFrame("JavaSweeper");
        JPanel headerPanel = new JPanel();
        WINDOWX = 35+buttonSize*mainGrid.xSize; //Sets window resolution according to button size
        WINDOWY = 110+buttonSize*mainGrid.ySize;
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        
        buttonArr = new JButton[mainGrid.xSize][mainGrid.ySize]; //creates a 2D button array that matches the Grid layout generated in App
        uiGrid = mainGrid; //sets this class' grid to the generated Grid so that other functions can access

        //Header Panel setup (contains face button, timer, bomb/flag counter)
        headerPanel.setBorder(loweredBevel);
        headerPanel.setBounds(5,5,WINDOWX-25, 50);

        //Sets up facebutton into the middle of the header panel
        faceButton = new JButton(":)");
        faceButton.setBounds((WINDOWX-35-20)/2, 10, 40,40);
        faceButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        faceButton.setBorder(raisedBevel);

        //Creates the array of buttons according the size of the grid supplied
        for(int i = 0; i < mainGrid.xSize; i++)
        {
            for(int j = 0; j < mainGrid.ySize; j++)
            {
                buttonArr[i][j] = new JButton();
                buttonArr[i][j].setBounds(10+(buttonSize*i), 60+(buttonSize*j), buttonSize, buttonSize); //set size and placement of buttons according to buttonSize
                buttonArr[i][j].setFont(new Font("Arial", Font.PLAIN, fontSize)); 
                buttonArr[i][j].setBorder(raisedBevel);
                buttonArr[i][j].addActionListener(this);
                frame.add(buttonArr[i][j]);
            }
        }

        frame.add(headerPanel);
        frame.add(faceButton);
        frame.setSize(WINDOWX,WINDOWY);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    //Goes through entire 2D array of Buttons and reveals all of them (due to a failure state)
    public void revealAllTiles()
    {
        for(int i = 0; i < buttonArr.length; i++)
        {
            for(int j = 0; j < buttonArr[i].length; j++)
            {
                buttonArr[i][j].setEnabled(false);
                buttonArr[i][j].setBorder(loweredBevel);
                if(uiGrid.tileBoard[j][i].isBomb) buttonArr[i][j].setText("*");
                else if(uiGrid.tileBoard[j][i].nearbyBombs > 0) buttonArr[i][j].setText(Integer.toString(uiGrid.tileBoard[j][i].nearbyBombs));
            }
        }
    }

    //Reveals the tile that was selected, reveals additional tiles if the current tile has no nearby bombs
    public void revealAdjacentTiles(int xInd, int yInd) 
    {
        buttonArr[xInd][yInd].setEnabled(false); //Grays out selected tile
        buttonArr[xInd][yInd].setBorder(loweredBevel);
        uiGrid.tileBoard[yInd][xInd].isRevealed = true;
        if(uiGrid.tileBoard[yInd][xInd].nearbyBombs > 0) buttonArr[xInd][yInd].setText(Integer.toString(uiGrid.tileBoard[yInd][xInd].nearbyBombs)); //if tile has nearby bombs then display it's number, if not keep button blank
        
        //Check if bomb
        if(uiGrid.tileBoard[yInd][xInd].isBomb)
        {
            faceButton.setText(":(");
            revealAllTiles();
        }
        
        //if tile is blank and not a bomb, we reveal all tiles around it that aren't bombs. This occurs recursively, revealing large areas of empty tiles.
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
            
            // -------DIAGONALS-------

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

    //When a button is clicked, Goes through entire array of buttons to find it's (x,y) coordinates. This makes it possible to grab a Tiles data from uiGrid
    public void actionPerformed(ActionEvent e) 
    {
        for(int i = 0; i < buttonArr.length; i++)
        {
            for(int j = 0; j < buttonArr[i].length; j++)
            {
                if(buttonArr[i][j] == (JButton)e.getSource())
                {
                    //System.out.println("button found at (" + i + "," + j + ")"); //shows (x,y) coords of pressed button, used for testing purposes.
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