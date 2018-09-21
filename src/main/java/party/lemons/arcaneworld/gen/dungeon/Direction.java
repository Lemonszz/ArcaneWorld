package party.lemons.arcaneworld.gen.dungeon;

import java.util.Random;

/**
 * Created by Sam on 20/09/2018.
 */
public enum Direction
{
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);

    private final int x, y;

    Direction(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean isHorizontal()
    {
        return x != 0;
    }

    public boolean isVertical()
    {
        return y != 0;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public static Direction random()
    {
        return Direction.values()[RANDOM.nextInt(Direction.values().length)];
    }

    public Direction opposite()
    {
        switch(this)
        {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        //What
        return null;
    }

    private static Random RANDOM = new Random();
}