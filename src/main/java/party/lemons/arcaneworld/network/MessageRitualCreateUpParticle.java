package party.lemons.arcaneworld.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Sam on 5/05/2018.
 */
public class MessageRitualCreateUpParticle implements IMessage
{
	public MessageRitualCreateUpParticle()
	{}

	public BlockPos pos;

	public MessageRitualCreateUpParticle(BlockPos pos)
	{
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();

		pos = new BlockPos(x, y, z);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler implements IMessageHandler<MessageRitualCreateUpParticle, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MessageRitualCreateUpParticle message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				BlockPos cPos = message.pos.up();
				Random r = Minecraft.getMinecraft().world.rand;
				float offsetX = 0.5F + ((r.nextFloat() / 4F) * (r.nextBoolean() ? 0F : 1F));
				float offsetZ = 0.5F + ((r.nextFloat() / 4F) * (r.nextBoolean() ? 0F : 1F));
				float offsetY = 0.7F;

				float vY = r.nextFloat() / (1  + r.nextInt(4));
				float vX = (r.nextFloat() / 10F)  * (r.nextBoolean() ? 0F : 1F);
				float vZ = (r.nextFloat() / 10F)  * (r.nextBoolean() ? 0F : 1F);

				Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.CRIT, cPos.getX() + offsetX, cPos.getY() + offsetY, cPos.getZ() + offsetZ, vX, vY, vZ);
			});

			return null;
		}
	}
}
