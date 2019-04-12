package party.lemons.arcaneworld.handler.ticker;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.network.MessageUpdateBiomeArea;
import party.lemons.lemonlib.ticker.ITicker;

/**
 * Created by Sam on 13/09/2018.
 */
public class TickerSetBiome implements ITicker
{
	private World world;
	private Biome biome;
	private BlockPos pos;
	private int radius, step, tick, dim;

	public TickerSetBiome(World world, Biome biome, BlockPos pos, int radius)
	{
		this.world = world;
		this.biome = biome;
		this.pos = pos;
		this.radius = radius;
		this.step = 0;
		this.dim = world.provider.getDimension();
		this.tick = 0;
	}

	@Override
	public void update(World world)
	{
		if(tick % 3 == 0) {

			AxisAlignedBB bb = new AxisAlignedBB(pos).grow(step, 0, step);
			for(int x = (int)bb.minX; x < bb.maxX; x ++)
			{
				for(int z = (int)bb.minZ; z < bb.maxZ; z++)
				{
					Chunk chunk = world.getChunk( new BlockPos(x, pos.getY(), z));
					chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)] = (byte)Biome.getIdForBiome(biome);
					chunk.setModified(true);
				}
			}
			ArcaneWorld.NETWORK.sendToAllTracking(new MessageUpdateBiomeArea(biome, bb), new NetworkRegistry.TargetPoint(getDimension(), pos.getX(), pos.getY(), pos.getZ(), 120));

			step++;
		}
		tick++;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("dim", getDimension());
		tags.setInteger("radius", radius);
		tags.setString("biome", biome.getRegistryName().toString());
		tags.setTag("pos", NBTUtil.createPosTag(pos));
		tags.setInteger("step", step);

		return tags;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		this.dim = tagCompound.getInteger("dim");
		this.radius = tagCompound.getInteger("radius");
		this.biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(tagCompound.getString("biome")));
		this.pos = NBTUtil.getPosFromTag(tagCompound.getCompoundTag("pos"));
		this.step = tagCompound.getInteger("step");
	}

	@Override
	public boolean isTaskFinished(World world)
	{
		return step == radius + 1;
	}

	@Override
	public int getDimension()
	{
		return dim;
	}

	@Override
	public boolean isUnique() {
		return false;
	}
}