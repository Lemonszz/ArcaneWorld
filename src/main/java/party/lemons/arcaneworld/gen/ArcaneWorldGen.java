package party.lemons.arcaneworld.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.ArcaneWorldBlocks;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Sam on 12/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public class ArcaneWorldGen
{
    private static final WorldGenerator AMETHYST_GENERATOR = getOreGenerator(
            ArcaneWorldBlocks.ORE_AMETHYST::getDefaultState, b -> b.getBlock() == Blocks.END_STONE,8, 15, 0, 80
    );

    @SubscribeEvent
    public static void onDecorateBiome(DecorateBiomeEvent.Pre event) {
        World world = event.getWorld();
        Random rand = event.getRand();
        BlockPos pos = event.getChunkPos().getBlock(8, 0, 8);
        Biome biome = world.getBiome(pos);

        //Create sapphire generator here since it needs to check the biome.
        //TODO: look into moving this elsewhere so doens't need to be created more than once
        getOreGenerator(ArcaneWorldBlocks.ORE_SAPPHIRE::getDefaultState, b -> b.getBlock() == Blocks.STONE, 15, isWetBiome(biome) ? 25 : 5, 0, 80).generate(world, rand, pos);
        AMETHYST_GENERATOR.generate(world, rand, pos);
    }

    private static WorldGenerator getOreGenerator(Supplier<IBlockState> state, Predicate<IBlockState> predicate, int size, int count, int minHeight, int maxHeight)
    {
        return new FeatureRange(new FeatureVein(b -> state.get(), size, predicate), count, minHeight, maxHeight);
    }

    private static boolean isWetBiome(Biome biome)
    {
       return BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN).contains(biome) ||
               BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER).contains(biome) ||
               BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP).contains(biome);
    }
}
