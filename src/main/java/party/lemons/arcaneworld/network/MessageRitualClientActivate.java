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
					//TODO: move this somewhere (tile entity?)
                    //TODO: Also it really needs a cleanup
					for(Ritual ritual : RitualRegistry.REGISTRY.getValuesCollection())
					{
						if(ritual.isEmpty())
							continue;

						NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
						for(int i = 0; i < stacks.size(); i++)
							stacks.set(i, ((TileEntityRitualTable) te).getInventory().getStackInSlot(i));

						if(ritual.matches(stacks))
						{
							((TileEntityRitualTable)te).setRitual(ritual);
							((TileEntityRitualTable)te).setActivator(serverPlayer);
							((TileEntityRitualTable)te).setState(TileEntityRitualTable.RitualState.START_UP);

							ItemStack[] usedStacks = new ItemStack[5];
							for(int i = 0; i < 5; i++)
							{
								usedStacks[i] = ((TileEntityRitualTable) te).getInventory().getStackInSlot(i).copy();
							}
							((TileEntityRitualTable) te).setStacks(usedStacks);

							//Take from tile entity inventory
							for(int i = 0; i < ((TileEntityRitualTable) te).getInventory().getSlots(); i++)
							{
							    Ingredient ingredient = ritual.getRequiredItems().get(i);
                                if(ingredient != Ingredient.EMPTY)
                                {
                                    int size = ingredient.getMatchingStacks()[0].getCount();
                                    ((TileEntityRitualTable) te).getInventory().getStackInSlot(i).shrink(size);
                                }
							}

							ArcaneWorld.NETWORK.sendToAllTracking(new MessageServerActivateRitual(ritual.getRegistryName(), te.getPos(), serverPlayer, usedStacks), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
							break;
						}
					}
				}

			});

			return null;
		}
	}
}
