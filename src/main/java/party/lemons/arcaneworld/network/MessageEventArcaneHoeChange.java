package party.lemons.arcaneworld.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
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
public class MessageEventArcaneHoeChange implements IMessage
{
	public MessageEventArcaneHoeChange()
	{}

	public BlockPos pos;
	public int num;

	public MessageEventArcaneHoeChange(BlockPos pos, int num)
	{
		this.pos = pos;
		this.num = num;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		num = buf.readInt();

		pos = new BlockPos(x, y, z);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(num);
	}

	public static class Handler implements IMessageHandler<MessageEventArcaneHoeChange, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MessageEventArcaneHoeChange message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				BlockPos cPos = message.pos.up();
				Random r = Minecraft.getMinecraft().world.rand;
				for(int i = 0; i < 4; i++)
				{
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.WATER_SPLASH, cPos.getX() + r.nextFloat(), cPos.getY(), cPos.getZ() + r.nextFloat(), 0, 0, 0);

					IBlockState state = Minecraft.getMinecraft().world.getBlockState(message.pos);

					double dX = (r.nextDouble() / 4) * (r.nextBoolean() ? -1 : 1);
					double dY = r.nextDouble() / 5;
					double dZ = (r.nextDouble() / 4) * (r.nextBoolean() ? -1 : 1);
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.BLOCK_DUST, cPos.getX() + r.nextFloat(), cPos.getY(), cPos.getZ() + r.nextFloat(), dX, dY, dZ, Block.getStateId(state));
				}
				float pitch = 1.0F + ((float)message.num / 5F);
				Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player, message.pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, pitch);
			});

			return null;
		}
	}
}
