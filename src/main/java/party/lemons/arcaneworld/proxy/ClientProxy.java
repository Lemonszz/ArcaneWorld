package party.lemons.arcaneworld.proxy;

import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.block.tilentity.render.TESRRitualTable;
import party.lemons.arcaneworld.entity.EntityOvergrownSheep;
import party.lemons.arcaneworld.entity.EntityRift;
import party.lemons.arcaneworld.entity.model.RenderOvergrownSheep;
import party.lemons.arcaneworld.entity.model.RenderRift;
import party.lemons.arcaneworld.handler.ArcaneWorldSounds;
import party.lemons.arcaneworld.util.capabilities.IRitualCoordinate;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinate;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinateHandler;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinateStorage;

/**
 * Created by Sam on 30/08/2018.
 */
public class ClientProxy implements IProxy
{
    public static MusicTicker.MusicType DUNGEON_MUSIC_TYPE = MusicTicker.MusicType.NETHER;

    @Override
	public void registerSided()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRitualTable.class, new TESRRitualTable());
        RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, RenderRift::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityOvergrownSheep.class, RenderOvergrownSheep::new);
    }

    @Override
    public void registerSidedInit()
    {
        DUNGEON_MUSIC_TYPE = EnumHelperClient.addMusicType("DUNGEON", ArcaneWorldSounds.MUSIC_DUNGEON, 0, 1200);
    }

    @Override
    public void capabilityInit() {
        CapabilityManager.INSTANCE.register(IRitualCoordinate.class, new RitualCoordinateStorage(), RitualCoordinate.class);
    }
}
