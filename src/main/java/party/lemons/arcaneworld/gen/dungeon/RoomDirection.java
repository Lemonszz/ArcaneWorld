package party.lemons.arcaneworld.gen.dungeon;

import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import java.util.HashMap;

/**
 * Created by Sam on 20/09/2018.
 */
public class RoomDirection
{
    private final HashMap<Direction, Boolean> directions;

    public RoomDirection()
    {
        this(false, false, false, false);
    }

    public RoomDirection(boolean up, boolean down, boolean left, boolean right)
    {
        directions = new HashMap<>();

        directions.put(Direction.NORTH, up);
        directions.put(Direction.SOUTH, down);
        directions.put(Direction.EAST, left);
        directions.put(Direction.WEST, right);
    }

    public RoomDirection withDirection(Direction direction, boolean open)
    {
        directions.put(direction, open);

        return this;
    }

    public boolean isOpen(Direction direction)
    {
        return directions.get(direction);
    }

    public boolean isSealed()
    {
        return this.equals(new RoomDirection());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof RoomDirection))
            return super.equals(obj);

        RoomDirection other = (RoomDirection) obj;

        for(Direction direction : Direction.values())
        {
            if(other.isOpen(direction) != isOpen(direction))
                return false;
        }

        return true;
    }

    public String getDirectory()
    {
        return getShape().getDirectory();
    }

    public RoomShape getShape()
    {
        int openCount = getOpenCount();

        if(openCount == 0)
            return RoomShape.SEALED;
        else if(openCount == 3)
            return RoomShape.T;
        else if(openCount == 4)
            return RoomShape.OPEN;
        else if(openCount == 1)
            return RoomShape.CAP;
        else if(openCount == 2)
        {
            if((isOpen(Direction.SOUTH) && isOpen(Direction.NORTH)) || (isOpen(Direction.EAST) && isOpen(Direction.WEST)))
                return RoomShape.CORRIDOR;
            else
                return RoomShape.CORNER;
        }

        return RoomShape.SEALED;
    }

    private int getOpenCount()
    {
        int count = 0;
        for(Direction direction : Direction.values())
        {
            if(isOpen(direction))
                count++;
        }
        return count;
    }

    public Mirror getMirror()
    {
        return Mirror.NONE;
    }

    public Rotation getRotation()
    {
        Rotation rotation = Rotation.NONE;
        RoomShape shape = getShape();

        switch (shape)
        {
            case CORNER:
                if(isOpen(Direction.NORTH) && isOpen(Direction.EAST))
                    rotation = Rotation.CLOCKWISE_90;
                else if(isOpen(Direction.SOUTH) && isOpen(Direction.EAST))
                    rotation = Rotation.CLOCKWISE_180;
                else if(isOpen(Direction.SOUTH) && isOpen(Direction.WEST))
                    rotation = Rotation.COUNTERCLOCKWISE_90;
                break;
            case T:
                if(isOpen(Direction.NORTH) && isOpen(Direction.SOUTH) && isOpen(Direction.EAST))
                    rotation = Rotation.CLOCKWISE_90;
                else if(isOpen(Direction.SOUTH) && isOpen(Direction.EAST) && isOpen(Direction.WEST))
                    rotation = Rotation.CLOCKWISE_180;
                else if(isOpen(Direction.NORTH) && isOpen(Direction.SOUTH) && isOpen(Direction.WEST))
                    rotation = Rotation.COUNTERCLOCKWISE_90;
                break;
            case CORRIDOR:
                if(isOpen(Direction.EAST))
                    rotation = Rotation.CLOCKWISE_90;
                break;
            case CAP:
                if(isOpen(Direction.NORTH))
                {
                    rotation = Rotation.NONE;
                }
                if(isOpen(Direction.EAST))
                {
                    rotation = Rotation.CLOCKWISE_90;
                }
                else if(isOpen(Direction.WEST))
                {
                    rotation = Rotation.COUNTERCLOCKWISE_90;
                }
                else if(isOpen(Direction.SOUTH))
                {
                    rotation = Rotation.CLOCKWISE_180;
                }
                break;
        }
        return rotation;
    }

    @Override
    public String toString()
    {
        String s = "";
        for(Direction direction : Direction.values())
        {
            s += direction.toString() + " " + isOpen(direction) + ", ";
        }

        return s;
    }

    enum RoomShape
    {
        SEALED("sealed"),
        OPEN("open"),
        CORNER("corner"),
        T("t"),
        CORRIDOR("corridor"),
        CAP("cap");

        private String directory;

        RoomShape(String directory)
        {
            this.directory = directory;
        }

        public String getDirectory()
        {
            return directory;
        }
    }
}