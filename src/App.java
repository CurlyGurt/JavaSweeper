//import Tile;
/* Functions Needed:
 * Grid Generator
 *      -Take in width and Length and bomb amount
 *      -Generate Tiles
 *      -Place bombs
 *      -if not bomb, check how many bombs surround
 *
 * Check if bomb
 *      -When tile is selected
 *      -check if bomb, if not, reveal number
 *      -if bomb, end game
 * 
 * Draw Game Table
 *      -start with printing
 */

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        Tile testTile = new Tile();
        //System.out.println("Tile1 xpos: " + tile1.xPos);
        System.out.println(testTile);
        testTile.setXPos(5);
        System.out.println(testTile);
        //generateGrid(3,3);
        Grid mainGrid = new Grid(3,3);
        System.out.println(mainGrid);
        System.out.println(mainGrid.tileBoard);
        System.out.println(mainGrid.tileBoard[2][2]);

    }

    //FUNCTIONS
    /*public Tile[][] generateGrid(int xSize, int ySize) 
    {
        Tile[][] tileBoard = new Tile[xSize][ySize];
        System.out.println(tileBoard[0][0]);
        return tileBoard;
    }*/
}