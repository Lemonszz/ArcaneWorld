package party.lemons.arcaneworld.gen.dungeon.dimension;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;

/**
 * Created by Sam on 22/09/2018.
 */
public class DungeonDimension
{
    public static DimensionType TYPE = DimensionType.register("dungeon", "dungeon", ArcaneWorldConfig.ConfigDungeonDimension.DIM_ID, DungeonDimensionProvider.class, false);

    public static void init()
    {
        DimensionManager.registerDimension(ArcaneWorldConfig.ConfigDungeonDimension.DIM_ID, TYPE);
    }
}
