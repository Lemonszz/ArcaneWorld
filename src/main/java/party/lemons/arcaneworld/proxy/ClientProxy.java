package party.lemons.arcaneworld.proxy;

import net.minecraft.client.audio.MusicTicker;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.block.tilentity.render.TESRRitualTable;
import party.lemons.arcaneworld.entity.EntityRecallOrb;
import party.lemons.arcaneworld.entity.model.RenderRecallOrb;
import party.lemons.arcaneworld.handler.ArcaneWorldSounds;

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
        RenderingRegistry.registerEntityRenderingHandler(EntityRecallOrb.class, RenderRecallOrb::new);
    }

    @Override
    public void registerSidedInit()
    {
        DUNGEON_MUSIC_TYPE = EnumHelperClient.addMusicType("DUNGEON", ArcaneWorldSounds.MUSIC_DUNGEON, 0, 1200);
    }
}
