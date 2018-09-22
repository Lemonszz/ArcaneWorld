package party.lemons.arcaneworld.gen.dungeon.generation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import party.lemons.arcaneworld.handler.ticker.ITicker;

/**
 * Created by Sam on 6/05/2018.
 */
public class TickerDungeon implements ITicker
{
	private World world;
	private int dimension;
	private DungeonGenerator generator;
	private int x, y, age;
	private boolean stopped = false;

	public TickerDungeon(World world)
	{
		this.world = world;
		this.dimension = world.provider.getDimension();
	}

	public TickerDungeon(World world, DungeonGenerator generator)
	{
		this(world);

		this.dimension = world.provider.getDimension();
		this.generator = generator;
		this.age = 0;
	}

	@Override
	public void update(World world)
	{
		if(world.provider.getDimension() != dimension || stopped)
			return;

		age++;

		if(age % 3 == 0)
        {
            boolean generated = false;
            while(!generated)
            {
                generated = generator.generateRoom(x, y);
                x++;
                if (x >= generator.getWidth())
                {
                    x = 0;
                    y++;

                    if (y >= generator.getHeight())
                    {
                        generated = true;
                        stopped = true;
                    }
                }
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		return new NBTTagCompound();
	}

	@Override
	public void readFromNBT(NBTTagCompound tags)
	{
		dimension = tags.getInteger("dim");
		world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimension);
		this.stopped = true;
	}

	@Override
	public boolean isTaskFinished(World world)
	{
		return stopped;
	}

	@Override
	public int getDimension()
	{
		return world.provider.getDimension();
	}

	@Override
	public boolean isUnique() {
		return false;
	}
}
