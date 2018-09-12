package party.lemons.arcaneworld.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.block.tilentity.render.TESRRitualTable;

/**
 * Created by Sam on 30/08/2018.
 */
public class ClientProxy implements IProxy
{
	@Override
	public void registerSided()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRitualTable.class, new TESRRitualTable());
	}
}
