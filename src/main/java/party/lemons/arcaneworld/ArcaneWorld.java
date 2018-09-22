package party.lemons.arcaneworld;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import party.lemons.arcaneworld.entity.ArcaneWorldEntities;
import party.lemons.arcaneworld.gen.dungeon.dimension.DungeonDimension;
import party.lemons.arcaneworld.handler.ArcaneWorldGuiHandler;
import party.lemons.arcaneworld.handler.OreDictHandler;
import party.lemons.arcaneworld.network.NetworkInit;
import party.lemons.arcaneworld.proxy.IProxy;

/**
 * Created by Sam on 9/09/2018.
 */
@Mod(modid= ArcaneWorld.MODID, name = ArcaneWorld.NAME, version = ArcaneWorld.VERSION)
public class ArcaneWorld
{
	public static final String MODID = "arcaneworld";
	public static final String NAME = "Arcane World";
	public static final String VERSION = "0.0.4";

	@Mod.Instance(MODID)
	public static ArcaneWorld INSTANCE;

	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@SidedProxy(clientSide = "party.lemons.arcaneworld.proxy.ClientProxy", serverSide = "party.lemons.arcaneworld.proxy.ServerProxy")
	public static IProxy proxy;


	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		NetworkInit.init();
		ArcaneWorldEntities.init();
        DungeonDimension.init();
		proxy.registerSided();
	}

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		OreDictHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new ArcaneWorldGuiHandler());
	}
}
