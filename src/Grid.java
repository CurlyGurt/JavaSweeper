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
}
