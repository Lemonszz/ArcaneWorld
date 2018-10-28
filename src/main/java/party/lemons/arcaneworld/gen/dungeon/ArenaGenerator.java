package party.lemons.arcaneworld.gen.dungeon;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.gen.dungeon.generation.DungeonRoomProcessor;

/**
 * Created by Sam on 25/10/2018.
 */
public class ArenaGenerator
{
    public static final ResourceLocation ARENA_POS = new ResourceLocation(ArcaneWorld.MODID, "arena");

    public static void generate(World world, BlockPos position)
    {
        PlacementSettings settings = new PlacementSettings();
        Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), ARENA_POS);
        template.addBlocksToWorld(world, position, new DungeonRoomProcessor(), settings, 2);
    }
}
