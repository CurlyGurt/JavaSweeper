public class Tile 
{
    int xPos;
    int yPos;
    int nearbyBombs;
    boolean isBomb;
    boolean isRevealed;

    Tile() //Default Constructor
    {
        this.xPos = 0;
        this.yPos = 0;
        this.nearbyBombs = 0;
        this.isBomb = false;        
        this.isRevealed = false;
    }

    Tile(int xPos, int yPos, int nearbyBombs, boolean isBomb, boolean isRevealed) //constructor
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isBomb = isBomb;        
        this.isRevealed = isRevealed;
    }

    public String toString() 
    {
        return  "xPos = " + (this.xPos+1) + '\n' +
                "yPos = " + (this.yPos+1) + '\n' +
                "nearbyBombs = " + this.nearbyBombs + '\n' +
                "isBomb = " + this.isBomb + '\n' +
                "isRevealed = " + this.isRevealed + '\n';
    }

    //getters
    public Object getXPos()
    {
        return xPos;
    }

    public Object getYPos()
    {
        return yPos;
    }

    public Object getBomb()
    {
        return isBomb;
    }

    public Object getRevealed()
    {
        return isRevealed;
    }

    //setters
    public void setXPos(int xPos)
    {
        this.xPos = xPos;
    }

    public void setYPos(int yPos)
    {
        this.yPos = yPos;
    }

    public void setBomb(boolean isBomb)
    {
        this.isBomb = isBomb;
    }

    public void setRevealed(boolean isRevealed)
    {
        this.isRevealed = isRevealed;
    }
}