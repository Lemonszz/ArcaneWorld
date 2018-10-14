package party.lemons.arcaneworld.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;
import party.lemons.arcaneworld.crafting.ritual.Ritual;

/**
 * Created by Sam on 5/05/2018.
 */
public class MessageRitualClientActivate implements IMessage
{
	public MessageRitualClientActivate()
	{}

	public BlockPos pos;

	public MessageRitualClientActivate(BlockPos pos)
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

	public static class Handler implements IMessageHandler<MessageRitualClientActivate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageRitualClientActivate message, MessageContext ctx)
		{
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			BlockPos pos = message.pos;
			double dis = pos.getDistance((int)serverPlayer.posX, (int)serverPlayer.posY, (int)serverPlayer.posZ);
			WorldServer world = serverPlayer.getServerWorld();

			world.addScheduledTask(()->{

				if(!world.isBlockLoaded(pos) || !world.isBlockModifiable(serverPlayer, pos) || dis > 10)
					return;

				TileEntity te = world.getTileEntity(pos);
				if(te instanceof TileEntityRitualTable)
                {
                    ((TileEntityRitualTable) te).attemptActivateRitual(serverPlayer);
                }

			});

			return null;
		}
	}
}
