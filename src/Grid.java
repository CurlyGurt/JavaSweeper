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

    Grid(int xSize, int ySize)
    {
        this.xSize = xSize;
        this.ySize = ySize;
        this.tileBoard = new Tile[xSize][ySize];
        generateGrid(xSize, ySize);
    }

    void generateGrid(int xSize, int ySize)
    {
        for(int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
            {
                tileBoard[i][j] = new Tile(i, j, 0, false, false);
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
            System.out.println("Rolling for bomb placement at (" + col + ", " + row + ")!");
            if(rand.nextInt(100) == 50) 
            {
                tileBoard[col][row].isBomb = true;
                System.out.println("Bomb placed at (" + col + ", " + row + ")!");
                bombAmt--;
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
