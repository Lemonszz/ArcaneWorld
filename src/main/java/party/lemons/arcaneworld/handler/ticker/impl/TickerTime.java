package party.lemons.arcaneworld.handler.ticker.impl;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.world.World;
import party.lemons.arcaneworld.handler.ticker.ITicker;

/**
 * Created by Sam on 10/09/2018.
 */
public class TickerTime implements ITicker
{
	private final long TIME_STEP = 100;

	private long target;
	private int dimension;

	public TickerTime(World world){}
	public TickerTime(long target, World world)
	{
		this.target = target + world.getWorldTime();
		this.dimension = world.provider.getDimension();
	}

	@Override
	public void update(World world)
	{
		if(world.getWorldTime() > target)
		{
			world.setWorldTime(world.getWorldTime() - TIME_STEP);
		}
		else
		{
			world.setWorldTime(world.getWorldTime() + TIME_STEP);
			if(world.getWorldTime() > target)
				world.setWorldTime(target);
		}

		for(EntityPlayerMP playerMP : world.getMinecraftServer().getPlayerList().getPlayers())
		{
			playerMP.connection.sendPacket(new SPacketTimeUpdate(world.getTotalWorldTime(), world.getWorldTime(), world.getGameRules().getBoolean("doDaylightCycle")));
		}

	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setLong("target", target);
		tags.setInteger("dim", dimension);
		return tags;
	}

	@Override
	public void readFromNBT(NBTTagCompound tags)
	{
		target = tags.getLong("target");
		dimension = tags.getInteger("dim");
	}

	@Override
	public boolean isTaskFinished(World world)
	{
		return world.getWorldTime() == target;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public boolean isUnique() {
		return true;
	}
}