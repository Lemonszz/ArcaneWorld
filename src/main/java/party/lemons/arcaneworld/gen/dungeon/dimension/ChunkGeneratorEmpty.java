package party.lemons.arcaneworld.gen.dungeon.dimension;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sam on 22/09/2018.
 */
public class ChunkGeneratorEmpty implements IChunkGenerator
{
    private World world;

    public ChunkGeneratorEmpty(World world)
    {
        this.world = world;
    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        return new Chunk(world, x, z);
    }

    @Override
    public void populate(int x, int z)
    {

    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        return false;
    }
}
