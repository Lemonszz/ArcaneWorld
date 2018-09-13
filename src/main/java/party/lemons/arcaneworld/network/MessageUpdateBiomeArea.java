package party.lemons.arcaneworld.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.arcaneworld.handler.ArcaneWorldSounds;

import java.util.Random;

/**
 * Created by Sam on 5/05/2018.
 */
public class MessageUpdateBiomeArea implements IMessage
{
	public MessageUpdateBiomeArea()
	{}

	public AxisAlignedBB bb;
	private byte biome;

	public MessageUpdateBiomeArea(Biome biome, AxisAlignedBB bb)
	{
		this.bb = bb;
		this.biome = (byte) Biome.getIdForBiome(biome);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int mixX = buf.readInt();
		int maxX = buf.readInt();
		int mixY = buf.readInt();
		int maxY = buf.readInt();
		int mixZ = buf.readInt();
		int maxZ = buf.readInt();

		biome = buf.readByte();

		bb = new AxisAlignedBB(mixX, mixY, mixZ, maxX, maxY, maxZ);


	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt((int)bb.minX);
		buf.writeInt((int)bb.maxX);

		buf.writeInt((int)bb.minY);
		buf.writeInt((int)bb.maxY);

		buf.writeInt((int)bb.minZ);
		buf.writeInt((int)bb.maxZ);

		buf.writeByte(biome);
	}

	public static class Handler implements IMessageHandler<MessageUpdateBiomeArea, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MessageUpdateBiomeArea message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				AxisAlignedBB bb = message.bb;

				for(int x = (int)bb.minX; x < bb.maxX; x ++)
				{
					for(int z = (int)bb.minZ; z < bb.maxZ; z++)
					{
						Chunk chunk = Minecraft.getMinecraft().world.getChunk( new BlockPos(x, bb.minY, z));
						chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)] = message.biome;
						chunk.setModified(true);

						if(x == bb.minX || x == bb.maxX || z == bb.minZ || z == bb.maxZ)
							for(int i = 0; i < 1; i++) {
								Random random = new Random();
								Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.TOWN_AURA, x + random.nextFloat(), bb.minY + 1, z + random.nextFloat(), 0, 0, 0);
							}
					}
				}
				BlockPos center = new BlockPos(bb.getCenter());
				Minecraft.getMinecraft().world.playSound(center, ArcaneWorldSounds.GENERAL_BREAK_SQUISH, SoundCategory.BLOCKS, 1F, 1F, false);
				Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate((int)bb.minX, 0, (int)bb.minZ, (int)bb.maxX, Minecraft.getMinecraft().world.getHeight(), (int)bb.maxZ);
			});

			return null;
		}
	}
}
