package party.lemons.arcaneworld.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.crafting.ritual.container.ContainerRitual;
import party.lemons.arcaneworld.crafting.ritual.container.GuiRitual;

import javax.annotation.Nullable;

/**
 * Created by Sam on 10/09/2018.
 */
public class ArcaneWorldGuiHandler implements IGuiHandler
{
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityRitualTable)
			return new ContainerRitual(player.inventory, ((TileEntityRitualTable)te).getInventory(), player);

		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityRitualTable)
			return new GuiRitual(player.inventory, ((TileEntityRitualTable)te).getInventory(), new BlockPos(x, y, z));

		return null;
	}
}
