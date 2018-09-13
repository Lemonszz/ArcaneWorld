package party.lemons.arcaneworld.handler.ticker.impl;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.handler.ticker.ITicker;
import party.lemons.arcaneworld.network.MessageEventArcaneHoeChange;

/**
 * Created by Sam on 6/05/2018.
 */
public class TickerHoe implements ITicker
{
	private World world;
	private int dimension, size, current, age;
	private EnumFacing direction;
	private BlockPos origin;
	private boolean stopped = false;

	public TickerHoe(World world)
	{
		this.world = world;
		this.age = 0;
		this.dimension = world.provider.getDimension();
	}

	public TickerHoe(World world, int size, EnumFacing direction, BlockPos origin)
	{
		this(world);

		this.size = size;
		this.direction = direction;
		this.origin = origin;
		this.current = 0;
		this.dimension = world.provider.getDimension();
	}

	@Override
	public void update(World world)
	{
		if(world.provider.getDimension() != dimension)
			return;

		BlockPos currentPos = getCurrentPos();
		IBlockState state = world.getBlockState(currentPos);

		if(age % 3 == 0)
		{
			boolean set = false;
			if(world.getBlockState(currentPos.up()).getBlock() instanceof IGrowable || world.isAirBlock(currentPos.up()))
			{
				for(int i = 0; i < hoeables.length; i++)
				{
					if(state.getBlock() == hoeables[i])
					{
						world.setBlockState(currentPos, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7));
						ArcaneWorld.NETWORK.sendToAllTracking(new MessageEventArcaneHoeChange(currentPos, current),
								new NetworkRegistry.TargetPoint(dimension, currentPos.getX(), currentPos.getY(), currentPos.getZ(), 64));
						set = true;
					}
				}
			}
			current++;

			if(!set)
				stopped = true;
		}
		age++;
	}

	private static Block[] hoeables = {Blocks.DIRT, Blocks.GRASS, Blocks.FARMLAND };

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();

		tags.setInteger("size", size);
		tags.setInteger("dim", dimension);
		tags.setInteger("direction", direction.ordinal());
		tags.setInteger("current", current);
		tags.setTag("origin", NBTUtil.createPosTag(origin));

		return tags;
	}

	@Override
	public void readFromNBT(NBTTagCompound tags)
	{
		size = tags.getInteger("size");
		direction = EnumFacing.values()[tags.getInteger("direction")];
		current = tags.getInteger("current");
		origin = NBTUtil.getPosFromTag(tags.getCompoundTag("origin"));
		dimension = tags.getInteger("dim");

		world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimension);
	}

	@Override
	public boolean isTaskFinished(World world)
	{
		return current > size || stopped;
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

	private BlockPos getCurrentPos()
	{
		return origin.offset(direction, current);
	}
}
