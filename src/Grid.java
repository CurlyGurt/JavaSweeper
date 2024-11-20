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
                System.out.println("Bomb placed at (" + col + ", " + row + ")!");
                bombAmt--;
            }
        }
    }

    void findBombsInProx()
    {
        int bombCount = 0;
        for(int i = 0; i < ySize; i++)
        {
            //System.out.println("i = " + i + "---------------------------");
            for(int j = 0; j < xSize; j++)
            {
                System.out.println("---------------------------------------------");
                System.out.println("i = " + i + "\tj = " + j + "\t(x,y) = (" + (j+1) + "," + (i+1) + ")\n");
                                     //(-1,-1) (0,-1) (1,-1)
                //check top left          ?      ?      ?
                //check top middle     (-1,0)  (0,0)  (1,0)          
                //check top right         ?      8      ?
                //check left           (-1,1)  (0,1)  (1,1)
                //check right             ?      ?      ?
                //check bottom left
                //check bottom
                //check bottom right

                if(j > 0)  //if not the left-most column
                {
                    System.out.println("tileBoard[i][j-1] (left): \n" + tileBoard[i][j-1]);                    
                }
                if(j < xSize-1) //if not bordering the right side
                {
                    System.out.println("tileBoard[i][j+1] (right): \n" + tileBoard[i][j+1]);
                }
                if(i > 0)  //if not the top row
                {
                    System.out.println("tileBoard[i-1][j] (top): \n" + tileBoard[i-1][j]);                    
                }
                if(i < ySize-1) //if not bordering the bottom
                {
                    System.out.println("tileBoard[i+1][j] (bottom): \n" + tileBoard[i+1][j]);
                }
                if(j > 0 && i > 0)  //if not the left-most column
                {
                    System.out.println("tileBoard[i-1][j-1] (top-left): \n" + tileBoard[i-1][j-1]);                    
                }
                if(j < xSize-1 && i < ySize-1) //if not bordering the right side or bottom
                {
                    System.out.println("tileBoard[i+1][j+1] (bottom-right): \n" + tileBoard[i+1][j+1]);
                }
                if(j > 0 && i < ySize-1)  //if not the left-most column and not the bottom row
                {
                    System.out.println("tileBoard[i+1][j-1] (bottom-left): \n" + tileBoard[i+1][j-1]);                    
                }
                if(i > 0 && j < xSize-1)  //if not the right-most column and not the top row
                {
                    System.out.println("tileBoard[i-1][j+1] (top-right): \n" + tileBoard[i-1][j+1]);                    
                }
                System.out.println("tileBoard[i][j] (center): \n" + tileBoard[i][j]);

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
                tileBoard[i][j].isRevealed = true;
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
                
                if(tileBoard[i][j].isRevealed == false) gridPrint += "?";
                else{
                        if(tileBoard[i][j].isBomb == true && tileBoard[i][j].isRevealed == true) gridPrint += "X";
                        else gridPrint += tileBoard[i][j].nearbyBombs;
                    }
                
            }
            gridPrint += '\n';
        }
        return gridPrint;
    }
}
