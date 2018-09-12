package party.lemons.arcaneworld.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import party.lemons.arcaneworld.block.ArcaneWorldBlocks;

import java.util.Random;

/**
 * Created by Sam on 12/09/2018.
 */
public class ArcaneWorldGen implements IWorldGenerator {


    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.getDimension())
        {
            case -1:
                    generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            case 1:
                    generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            default:
                generateNormal(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
        }
    }

    private void generateNormal(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

        WorldGenMinable sapphireGen = new WorldGenMinable(ArcaneWorldBlocks.ORE_SAPPHIRE.getDefaultState(), 6);
        int xp = chunkX * 16 + random.nextInt(16);
        int yp = random.nextInt(75);
        int zp = chunkZ * 16 + random.nextInt(16);
        BlockPos pos = new BlockPos(xp, yp, zp);
        Biome biome = world.getBiome(pos);
        int amt = 10;
        if(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN).contains(biome) || BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER).contains(biome))
        {
            amt = 25;
        }

        for (int i=0; i< amt; i++)
        {
            int x = chunkX * 16 + random.nextInt(16);
            int y = random.nextInt(75);
            int z = chunkZ * 16 + random.nextInt(16);
            sapphireGen.generate(world, random, new BlockPos(x, y, z));
        }
    }

    private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

         WorldGenMinable amethystGen = new WorldGenMinable(ArcaneWorldBlocks.ORE_AMETHYST.getDefaultState(), 6, b -> b.getBlock() == Blocks.END_STONE);

        for (int i=0; i< 10; i++)
        {
            int x = chunkX * 16 + random.nextInt(16);
            int y = random.nextInt(75);
            int z = chunkZ * 16 + random.nextInt(16);
            amethystGen.generate(world, random, new BlockPos(x, y, z));
        }
    }

    private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    }

}
