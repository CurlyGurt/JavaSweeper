import java.util.Random;

public class Grid 
{
    int xSize;
    int ySize;
    Tile[][] tileBoard;
    //need an array of an array of objects
    Grid() 
    {
       this.xSize = 0;
       this.ySize = 0;
       this.tileBoard[ySize][xSize] = null;
    }

    Grid(int col, int row)
    {
        this.xSize = col;
        this.ySize = row;
        this.tileBoard = new Tile[ySize][xSize];
        generateGrid(xSize, ySize);
    }

    void generateGrid(int xSize, int ySize)
    {
        for(int i = 0; i < ySize; i++)
        {
            //System.out.println("j is = " + j);
            for(int j = 0; j < xSize; j++)
            {
                tileBoard[i][j] = new Tile(j, i, 0, false, false);
                //System.out.println("j is = " + j);
            }
        }
    }

    void placeBombs(int bombAmt)
    {
        int col = 0;
        int row = 0;
        Random rand = new Random();
        while(bombAmt >= 0)
        {
            col = rand.nextInt(xSize);
            row = rand.nextInt(ySize);
            //System.out.println("Rolling for bomb placement at (" + col + ", " + row + ")!");
            if(rand.nextInt(100) == 50) 
            {
                tileBoard[row][col].isBomb = true;
                //System.out.println("Bomb placed at (" + col + ", " + row + ")!");
                bombAmt--;
            }
        }
    }

    void findBombsInProx()
    {
        //int bombCount = 0;
        for(int i = 0; i < ySize; i++)
        {
            //System.out.println("i = " + i + "---------------------------");
            for(int j = 0; j < xSize; j++)
            {
                //System.out.println("---------------------------------------------");
                //System.out.println("i = " + i + "\tj = " + j + "\t(x,y) = (" + (j+1) + "," + (i+1) + ")\n");
                                     //(-1,-1) (0,-1) (1,-1)
                //check top left          ?      ?      ?
                //check top middle     (-1,0)  (0,0)  (1,0)          
                //check top right         ?      8      ?
                //check left           (-1,1)  (0,1)  (1,1)
                //check right             ?      ?      ?
                //check bottom left
                //check bottom
                //check bottom right
                //if(tileBoard[i][j].isBomb) break;

                if(j > 0)  //if not the left-most column
                {
                    //System.out.println("tileBoard[i][j-1] (left): \n" + tileBoard[i][j-1]);
                    if(tileBoard[i][j-1].isBomb) tileBoard[i][j].nearbyBombs++;
                }
                if(j < xSize-1) //if not bordering the right side
                {
                    //System.out.println("tileBoard[i][j+1] (right): \n" + tileBoard[i][j+1]);
                    if(tileBoard[i][j+1].isBomb) tileBoard[i][j].nearbyBombs++;
                }
                if(i > 0)  //if not the top row
                {
                    //System.out.println("tileBoard[i-1][j] (top): \n" + tileBoard[i-1][j]);        
                    if(tileBoard[i-1][j].isBomb) tileBoard[i][j].nearbyBombs++;
            
                }
                if(i < ySize-1) //if not bordering the bottom
                {
                    //System.out.println("tileBoard[i+1][j] (bottom): \n" + tileBoard[i+1][j]);
                    if(tileBoard[i+1][j].isBomb) tileBoard[i][j].nearbyBombs++;

                }
                if(j > 0 && i > 0)  //if not the left-most column
                {
                    //System.out.println("tileBoard[i-1][j-1] (top-left): \n" + tileBoard[i-1][j-1]);
                    if(tileBoard[i-1][j-1].isBomb) tileBoard[i][j].nearbyBombs++;
                   
                }
                if(j < xSize-1 && i < ySize-1) //if not bordering the right side or bottom
                {
                    //System.out.println("tileBoard[i+1][j+1] (bottom-right): \n" + tileBoard[i+1][j+1]);
                    if(tileBoard[i+1][j+1].isBomb) tileBoard[i][j].nearbyBombs++;

                }
                if(j > 0 && i < ySize-1)  //if not the left-most column and not the bottom row
                {
                    //System.out.println("tileBoard[i+1][j-1] (bottom-left): \n" + tileBoard[i+1][j-1]);
                    if(tileBoard[i+1][j-1].isBomb) tileBoard[i][j].nearbyBombs++;

                }
                if(i > 0 && j < xSize-1)  //if not the right-most column and not the top row
                {
                    //System.out.println("tileBoard[i-1][j+1] (top-right): \n" + tileBoard[i-1][j+1]);  
                    if(tileBoard[i-1][j+1].isBomb) tileBoard[i][j].nearbyBombs++;
                  
                }
                //System.out.println("tileBoard[i][j] (center): \n" + tileBoard[i][j]);

            }   
        }
    }

    public void revealBoard()
    {
        System.out.println("Revealing Entire Board!");
        System.out.println("-----------------------");
        for(int i = 0; i < ySize; i++)
        {
            for(int j = 0; j < xSize; j++)
            {
                tileBoard[i][j].setRevealed(true);
            }
        }
    }

    public void revealTile(int xCord, int yCord)
    {
        if(!tileBoard[yCord][xCord].isBomb) 
        {
            tileBoard[yCord][xCord].isRevealed = true;
        }
        else
        {
            revealBoard(); //!!! Must change this to failure state
        }
        if(tileBoard[yCord][xCord].nearbyBombs == 0) 
        {
            if(xCord > 0)  //if not the left-most column
            {
                if(tileBoard[yCord][xCord-1].nearbyBombs == 0 && !tileBoard[yCord][xCord-1].isRevealed && !tileBoard[yCord][xCord-1].isBomb) 
                {
                    System.out.println("Revealing (" + xCord + "," + (yCord) + ")...\txCord>0");
                    revealTile(xCord-1, yCord);
                }
            }
            if(xCord < xSize-1) //if not bordering the right side
            {
                if(tileBoard[yCord][xCord+1].nearbyBombs == 0 && !tileBoard[yCord][xCord+1].isRevealed && !tileBoard[yCord][xCord+1].isBomb) 
                {
                    System.out.println("Revealing (" + xCord + "," + (yCord) + ")...\txCord < xSize-1");
                    revealTile(xCord+1, yCord);
                } 
            }
            if(yCord > 0)  //if not the top row
            {
                if(tileBoard[yCord-1][xCord].nearbyBombs == 0 && !tileBoard[yCord-1][xCord].isRevealed && !tileBoard[yCord-1][xCord].isBomb)
                {
                    System.out.println("Revealing (" + xCord + "," + (yCord) + ")...\tyCord>0");
                    revealTile(xCord, yCord-1);
                } 
            }
            if(yCord < ySize-1) //if not bordering the bottom
            {
                if(tileBoard[yCord+1][xCord].nearbyBombs == 0 && !tileBoard[yCord+1][xCord].isRevealed && !tileBoard[yCord+1][xCord].isBomb)
                {
                    System.out.println("Revealing (" + xCord + "," + (yCord) + ")...\tyCord < ySize-1");
                    revealTile(xCord, yCord+1);
                } 
            }
        }
    }

    public String toString()
    {
        String gridPrint = "";
        for(int i = 0; i < ySize; i++)
        {
            for(int j = 0; j < xSize; j++)
            {
                if(tileBoard[i][j].isRevealed == false) gridPrint += "? ";
                else{
                        if(tileBoard[i][j].isBomb == true && tileBoard[i][j].isRevealed == true) gridPrint += "X ";
                        else gridPrint += tileBoard[i][j].nearbyBombs+" ";
                    }
            }
            gridPrint += '\n';
        }
        return gridPrint;
    }
}
