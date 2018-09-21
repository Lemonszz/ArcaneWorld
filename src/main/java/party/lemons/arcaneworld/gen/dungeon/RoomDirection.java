package party.lemons.arcaneworld.gen.dungeon;

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
        String str = "";
        if(isOpen(Direction.EAST))
            str += "east_";

        if(isOpen(Direction.NORTH))
            str += "north_";

        if(isOpen(Direction.WEST))
            str += "west_";

        if(isOpen(Direction.SOUTH))
            str += "south_";

        return str;
    }

    public void setFromDirectoryString(String directoryString)
    {
        String[] split = directoryString.split("_");
        for(String s : split)
        {
            switch(s)
            {
                case "east":
                    this.withDirection(Direction.EAST, true);
                    break;
                case "north":
                    this.withDirection(Direction.NORTH, true);
                    break;
                case "west":
                    this.withDirection(Direction.WEST, true);
                    break;
                case "south":
                    this.withDirection(Direction.SOUTH, true);
                    break;
            }
        }
    }
}