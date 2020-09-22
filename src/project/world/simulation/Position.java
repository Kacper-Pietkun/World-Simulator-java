package project.world.simulation;

/**
 * @author Kacper Pietkun
 */
public class Position
{
    private int x;
    private int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Position()
    {
        x = 0;
        y = 0;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public boolean equals(Object object)
    {
        if(object instanceof Position)
        {
            Position position = (Position) object;
            return (x == position.x && y == position.y);
        }
        return false;
    }
}
