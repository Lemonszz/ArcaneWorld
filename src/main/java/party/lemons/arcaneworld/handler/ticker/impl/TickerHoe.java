package party.lemons.arcaneworld.handler.ticker.impl;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
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
	private EntityPlayer player;
	private boolean stopped = false;

	public TickerHoe(World world)
	{
		this.world = world;
		this.age = 0;
		this.dimension = world.provider.getDimension();
	}

	public TickerHoe(World world, EntityPlayer player, int size, EnumFacing direction, BlockPos origin)
	{
		this(world);

		this.size = size;
		this.direction = direction;
		this.origin = origin;
		this.current = 0;
		this.dimension = world.provider.getDimension();
		this.player = player;
	}

	@Override
	public void update(World world)
	{
	    //If this is being updated with the wrong world... don't
		if(world.provider.getDimension() != dimension)
			return;

		BlockPos currentPos = getCurrentPos();
		IBlockState state = world.getBlockState(currentPos);

		if(age % 3 == 0)    //Tick every 3 ticks
		{
			boolean set = false; //If set == true, the block at currentPos was changed

            /*
                If the block above the current pos is a growable (seeds, grass, sapling) or the block is air
                the block at current pos can be replaced
             */
			if(world.getBlockState(currentPos.up()).getBlock() instanceof IGrowable || world.isAirBlock(currentPos.up()))
			{
			    if(isHoeable(state))    //If the state as position can be turned into farmland
                {
                    //Set the block to farmland
                    world.setBlockState(currentPos, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7));

                    /*
                        Handle seed planting.
                        Seeds can only be planted if the ticker knows about the player & the block above the current pos is air
                     */
                    if(player != null && world.isAirBlock(currentPos.up()))
                    {
                        ItemStack offhand = player.getHeldItemOffhand();    //players offhand stack
                        if(!offhand.isEmpty())
                        {
                            if(offhand.getItem() instanceof IPlantable) //If the held stack is a IPlantable, we can plant it
                            {
                                IBlockState plantState  = ((IPlantable)offhand.getItem()).getPlant(world, currentPos.up()); //The state to plant
                                if(state.getBlock().canPlaceBlockAt(world, currentPos.up()))    //If the state can be placed, we can place the state
                                {
                                    //place seed
                                    offhand.shrink(1);
                                    world.setBlockState(currentPos.up(), plantState);
                                    state.getBlock().onBlockPlacedBy(world, currentPos.up(), plantState, player, offhand);
                                }
                            }
                        }
                    }

                    //Let clients know about the event
                    ArcaneWorld.NETWORK.sendToAllTracking(new MessageEventArcaneHoeChange(currentPos, current),
                            new NetworkRegistry.TargetPoint(dimension, currentPos.getX(), currentPos.getY(), currentPos.getZ(), 64));

                    //If we get to this point, it's guarenteed that the farmland was set
                    set = true;
                }
			}
			current++;

			if(!set)    //If we didn't set the block, stop
				stopped = true;
		}
		age++;
	}

    private boolean isHoeable(IBlockState state)
    {
        for(int i = 0; i < hoeables.length; i++)
        {
            if (hoeables[i] == state.getBlock())
                return true;
        }

        return false;
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
