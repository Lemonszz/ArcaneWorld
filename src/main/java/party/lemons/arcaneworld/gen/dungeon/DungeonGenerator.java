package party.lemons.arcaneworld.gen.dungeon;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.handler.ticker.TickerHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by Sam on 20/09/2018.
 */
public class DungeonGenerator
{
    private final int MIN_WIDTH = 5;
    private final int MIN_HEIGHT = 5;
    private final int MAX_WIDTH = 10;
    private final int MAX_HEIGHT = 10;
    private final int ROOM_WIDTH = 13;

    private BlockPos origin;
    private World world;
    private Random random;
    private int width, height;
    private RoomDirection[][] directions;

    public DungeonGenerator(World world, BlockPos origin)
    {
        this.world = world;
        this.origin = origin;
        this.random = world.rand;
        this.width =  MIN_WIDTH + random.nextInt(MAX_WIDTH - MIN_WIDTH + 1);
        this.height =  MIN_HEIGHT + random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);

        directions = new RoomDirection[width][height];
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                directions[i][j] = new RoomDirection();
            }
        }
    }

    public void generate()
    {
        createLayout();
        TickerHandler.addTicker(new TickerDungeon(world, this), world.provider.getDimension());
    }

    public boolean generateRoom(int x, int y)
    {
        RoomDirection direction = directions[x][y];
        if(!direction.isSealed())
        {
            BlockPos generatePos = origin.add(x * ROOM_WIDTH, 0, y * ROOM_WIDTH);
            Rotation rotation = direction.getRotation();

            int offsetX = 0;
            int offsetZ = 0;
            switch (rotation)
            {
                case CLOCKWISE_90:
                    offsetX = 12;
                    break;
                case CLOCKWISE_180:
                    offsetX = 12;
                    offsetZ = 12;
                    break;
                case COUNTERCLOCKWISE_90:
                    offsetZ = 12;
                    break;
            }

            PlacementSettings settings = new PlacementSettings().setRotation(direction.getRotation()).setMirror(direction.getMirror());
            ResourceLocation layout = getRoomLayout(direction);
            Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), layout);
            template.addBlocksToWorld(world, generatePos.add(offsetX, 0, offsetZ), settings);

            return true;
        }

        return false;
    }

    private void createLayout()
    {
        int x = 0;
        int z = 0;
        boolean finished = false;
        while(!finished)
        {
            RoomDirection currentRoom = directions[x][z];
            if(z != height - 1)
            {
                Direction moveDirection = random.nextBoolean() ? Direction.EAST : Direction.WEST;
                if(!(x + moveDirection.getX() > 0 && x + moveDirection.getX() < width))
                {
                   moveDirection = Direction.SOUTH;
                }
                currentRoom.withDirection(moveDirection, true);
                directions[x + moveDirection.getX()][ z + moveDirection.getY()].withDirection(moveDirection.opposite(), true);
                x += moveDirection.getX();
                z += moveDirection.getY();
            }
            else
            {
                if(x != width - 1)
                {
                    currentRoom.withDirection(Direction.EAST, true);
                    directions[x +1][z].withDirection(Direction.WEST, true);

                    x += 1;
                }
                else
                {
                    finished = true;
                }
            }
        }

        for(int i = 0; i < 40; i++)
        {
            int mutateX =  random.nextInt(width);
            int mutateZ =  random.nextInt(height);

            RoomDirection direction = directions[mutateX][mutateZ];
            Direction mutateDir = Direction.random();

            if(direction.isSealed())
                continue;
            if(mutateX == 0 && mutateDir.getX() < 0)
                continue;
            if(mutateX == width - 1 && mutateDir.getX() > 0)
                continue;
            if(mutateZ == 0 && mutateDir.getY() < 0)
                continue;
            if(mutateZ == height - 1 && mutateDir.getY() > 0)
                continue;

            Direction moveDirection = mutateDir;
            if(mutateDir.isVertical())  //TODO: Why the fuck do i need to do this
            {
              //  moveDirection = mutateDir.opposite();
            }

            System.out.println("muate");
           direction.withDirection(moveDirection, true);
           directions[mutateX + mutateDir.getX()][mutateZ + mutateDir.getY()].withDirection(moveDirection.opposite(), true);
        }
    }

    public ResourceLocation getRoomLayout(RoomDirection direction)  //TODO: Cache this?
    {
        try
        {
            File file = new File(this.getClass().getClassLoader().getResource("assets/" + ArcaneWorld.MODID +  "/structures/dungeon/" + direction.getDirectory()).toURI());
            File[] templates = file.listFiles();
            String selected = templates[random.nextInt(templates.length)].getName();
            selected = selected.substring(0, selected.lastIndexOf("."));

            return new ResourceLocation(ArcaneWorld.MODID, "dungeon/"+ direction.getDirectory() +"/" + selected);
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
