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
       this.tileBoard[xSize][ySize] = null;
    }

    Grid(int row, int col)
    {
        this.xSize = col;
        this.ySize = row;
        this.tileBoard = new Tile[xSize][ySize];
        generateGrid(xSize, ySize);
    }

    void generateGrid(int xSize, int ySize)
    {
        for(int i = 0; i < xSize; i++)
        {
            //System.out.println("j is = " + j);
            for(int j = 0; j < ySize; j++)
            {
                tileBoard[i][j] = new Tile(i, j, 0, false, false);
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
                tileBoard[col][row].isBomb = true;
                System.out.println("Bomb placed at (" + col + ", " + row + ")!");
                bombAmt--;
            }
        }
    }

    void findBombsInProx()
    {
        int bombCount = 0;
        for(int i = 0; i < xSize; i++)
        {
            //System.out.println("i = " + i + "---------------------------");
            for(int j = 0; j < ySize; j++)
            {
                System.out.println("---------------------------------------------");
                System.out.println("i = " + i + "\tj = " + j);
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
                    System.out.println("tileBoard[i][j-1] = " + tileBoard[i][j-1]);
                    
                    //if(tileBoard[i])
                }
                System.out.println("tileBoard center = " + tileBoard[i][j]);

            }   
        }
    }

    public void revealBoard()
    {
        System.out.println("Revealing Entire Board!");
        System.out.println("-----------------------");
        for(int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
            {
                tileBoard[i][j].isRevealed = true;
            }
        }
    }

    public String toString()
    {
        String gridPrint = "";
        for(int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
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
