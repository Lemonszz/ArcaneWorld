package party.lemons.arcaneworld.gen.dungeon.dimension;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

/**
 * Created by Sam on 22/09/2018.
 */
public class DungeonDimensionProvider extends WorldProvider
{
    @Override
    public DimensionType getDimensionType()
    {
        return DungeonDimension.TYPE;
    }

    @Override
    public String getSaveFolder()
    {
        return "dungeons";
    }

    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorEmpty(world);
    }

    public boolean isSurfaceWorld()
    {
        return false;
    }
    public boolean canRespawnHere()
    {
        return false;
    }

}
