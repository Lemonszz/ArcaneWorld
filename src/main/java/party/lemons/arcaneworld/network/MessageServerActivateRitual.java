package party.lemons.arcaneworld.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;

import java.util.UUID;

/**
 * Created by Sam on 5/05/2018.
 */
public class MessageServerActivateRitual implements IMessage
{
	public MessageServerActivateRitual()
	{}

	public ResourceLocation loc;
	public BlockPos pos;
	public int activator;
	public ItemStack[] items = new ItemStack[5];

	public MessageServerActivateRitual(ResourceLocation location, BlockPos pos, EntityPlayer activator, ItemStack[] items)
	{
		this.loc = location;
		this.pos = pos;
		this.activator = activator.getEntityId();
		this.items = items;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		loc = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
		pos = new BlockPos(x, y, z);

		activator = buf.readInt();
		for(int i = 0; i < 5; i++)
			items[i] = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		ByteBufUtils.writeUTF8String(buf, loc.toString());
		buf.writeInt(activator);

		for(int i = 0; i < 5; i++)
			ByteBufUtils.writeItemStack(buf, items[i]);
	}

	public static class Handler implements IMessageHandler<MessageServerActivateRitual, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MessageServerActivateRitual message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				BlockPos cPos = message.pos;
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(cPos);

				if(te instanceof TileEntityRitualTable)
				{
					if(((TileEntityRitualTable) te).getState() != TileEntityRitualTable.RitualState.NONE)
						return;

					((TileEntityRitualTable) te).setState(TileEntityRitualTable.RitualState.START_UP);
					((TileEntityRitualTable) te).setRitual(RitualRegistry.REGISTRY.getValue(message.loc));
					((TileEntityRitualTable) te).setActivator((EntityPlayer) Minecraft.getMinecraft().world.getEntityByID(message.activator));
					((TileEntityRitualTable) te).setStacks(message.items);
				}

			});

			return null;
		}
	}
}
