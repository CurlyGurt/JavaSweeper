import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.border.Border;


public class SweeperUI extends Frame implements WindowListener, ActionListener {
    Border loweredBevel, raisedBevel, buttonBevel;
    int WINDOWX = 1000;
    int WINDOWY = 1000;
    int buttonSize = 50;
    int fontSize = buttonSize/2 + 5;
    int xSize = 9; //default to easy
    int ySize = 9;
    int bombs = 10;
    int initBombCount;
    int bombCounter;
    int bombsCorrectlyFlagged;
    JFrame frame;
    JFrame diffFrame;
    JButton faceButton;
    JButton[][] buttonArr;
    Grid uiGrid = new Grid(1,1);

    MouseAdapter flagger = new MouseAdapter() 
    {
        public void mouseClicked(MouseEvent me) 
        {
            //me.getSource();
            if(me.getButton() == MouseEvent.BUTTON1)
            {
                for(int i = 0; i < buttonArr.length; i++)
                {
                    for(int j = 0; j < buttonArr[i].length; j++)
                    {
                        if(buttonArr[i][j] == (JButton)me.getSource())
                        {
                            //System.out.println("button found at (" + i + "," + j + ")"); //shows (x,y) coords of pressed button, used for testing purposes.
                            revealAdjacentTiles(i, j);
                            break;
                        }
                    }
                }
            }
            if(me.getButton() == MouseEvent.BUTTON3) 
            {
                for(int i = 0; i < buttonArr.length; i++)
                {
                    for(int j = 0; j < buttonArr[i].length; j++)
                    {
                        if(buttonArr[i][j] == (JButton)me.getSource())
                        {
                            //System.out.println("button found at (" + i + "," + j + ")"); //shows (x,y) coords of pressed button, used for testing purposes.
                            if(buttonArr[i][j].getText() == "F")
                            {
                                buttonArr[i][j].setText("");
                                bombCounter++;
                                if(uiGrid.tileBoard[j][i].isBomb) bombsCorrectlyFlagged--;
                                System.out.println("bombCounter = " + bombCounter + " bombsCorrectlyFlagged = " + bombsCorrectlyFlagged);
                            }
                            else if(!uiGrid.tileBoard[j][i].isRevealed && bombCounter >= 0) 
                            {
                                buttonArr[i][j].setText("F");
                                bombCounter--;
                                if(uiGrid.tileBoard[j][i].isBomb) bombsCorrectlyFlagged++;
                                if(initBombCount == bombsCorrectlyFlagged) revealAllTiles();
                                System.out.println("bombCounter = " + bombCounter + " bombsCorrectlyFlagged = " + bombsCorrectlyFlagged);
                            }
                            break;
                        }
                    }
                }
            }
        }
    };

    public void ShowUI()
    {
        //Window Setup
        frame = new JFrame("JavaSweeper");
        JPanel headerPanel = new JPanel();
        WINDOWX = 35+buttonSize*uiGrid.xSize; //Sets window resolution according to button size
        WINDOWY = 110+buttonSize*uiGrid.ySize;
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        raisedBevel = BorderFactory.createRaisedBevelBorder();

        buttonArr = new JButton[uiGrid.xSize][uiGrid.ySize]; //creates a 2D button array that matches the Grid layout generated in App
        //uiGrid = mainGrid; //sets this class' grid to the generated Grid so that other functions can access
        bombCounter = initBombCount = bombs;

        //Header Panel setup (contains face button, timer, bomb/flag counter)
        headerPanel.setBorder(loweredBevel);
        headerPanel.setBounds(5,5,WINDOWX-25, 50);

        //Sets up facebutton into the middle of the header panel
        faceButton = new JButton(":)");
        faceButton.setBounds((WINDOWX-35-20)/2, 10, 40,40);
        faceButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        faceButton.setBorder(raisedBevel);
        faceButton.setActionCommand("restart,0,0,0");
        faceButton.addActionListener(this);

        //Creates the array of buttons according the size of the grid supplied
        for(int i = 0; i < uiGrid.xSize; i++)
        {
            for(int j = 0; j < uiGrid.ySize; j++)
            {
                buttonArr[i][j] = new JButton();
                buttonArr[i][j].setBounds(10+(buttonSize*i), 60+(buttonSize*j), buttonSize, buttonSize); //set size and placement of buttons according to buttonSize
                buttonArr[i][j].setFont(new Font("Arial", Font.PLAIN, fontSize)); 
                buttonArr[i][j].setBorder(raisedBevel);
                buttonArr[i][j].addMouseListener(flagger);
                //buttonArr[i][j].addActionListener(this);
                frame.add(buttonArr[i][j]);
            }
        }

        frame.add(headerPanel);
        frame.add(faceButton);
        frame.addWindowListener(this);
        frame.setSize(WINDOWX,WINDOWY);
        frame.setLayout(null);
        frame.setVisible(true);
    }

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

    //Goes through entire 2D array of Buttons and reveals all of them (due to a failure state)
    public void revealAllTiles()
    {
        if(initBombCount == bombsCorrectlyFlagged)
        {
            System.out.println("You Won! Congratulations!");
            for(int i = 0; i < buttonArr.length; i++)
            {
                for(int j = 0; j < buttonArr[i].length; j++)
                {
                    buttonArr[i][j].setEnabled(false);
                    buttonArr[i][j].setBorder(loweredBevel);
                    uiGrid.tileBoard[j][i].setRevealed(true);
                    faceButton.setText(":D");
                    if(uiGrid.tileBoard[j][i].isBomb) buttonArr[i][j].setText("*");
                    else if(uiGrid.tileBoard[j][i].nearbyBombs > 0) buttonArr[i][j].setText(Integer.toString(uiGrid.tileBoard[j][i].nearbyBombs));
                }
            }
        }
        else
        {
            for(int i = 0; i < buttonArr.length; i++)
            {
                for(int j = 0; j < buttonArr[i].length; j++)
                {
                    buttonArr[i][j].setEnabled(false);
                    buttonArr[i][j].setBorder(loweredBevel);
                    uiGrid.tileBoard[j][i].setRevealed(true);
                    if(uiGrid.tileBoard[j][i].isBomb) buttonArr[i][j].setText("*");
                    else if(uiGrid.tileBoard[j][i].nearbyBombs > 0) buttonArr[i][j].setText(Integer.toString(uiGrid.tileBoard[j][i].nearbyBombs));
                }
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
            System.out.println("xSize = " + xSize + " ySize = " + ySize + " bombs = " + bombs);
            uiGrid.generateGrid(xSize, ySize);
            uiGrid.placeBombs(bombs);
            uiGrid.findBombsInProx();

            System.out.println("----------Generated Grid----------- \n" + uiGrid); //displays grid in terminal for testing purposes
            
            ShowUI();
            diffFrame.setVisible(false);
        }
        else if(action[0].equals("restart")) 
        {
            frame.dispose();
            buttonArr = null;
            bombsCorrectlyFlagged = 0;
            System.gc();
            diffFrame.setVisible(true);
        }
    }

    public void windowClosing(WindowEvent e) 
    {
        
        if(e.getWindow() == diffFrame)
        {
            dispose();
            System.exit(0);
        }
        else if(e.getWindow() == frame)
        {
            frame.dispose();
            buttonArr = null;
            bombsCorrectlyFlagged = 0;
            System.gc();
            diffFrame.setVisible(true);
        }
    }

    public void windowClosed(WindowEvent e) 
    {
        dispose();
        //System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    
}