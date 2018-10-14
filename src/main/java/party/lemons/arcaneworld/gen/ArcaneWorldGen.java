package party.lemons.arcaneworld.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.ArcaneWorldBlocks;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Sam on 12/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class ArcaneWorldGen
{
    public static final Biome ARCANE_VOID = Biomes.VOID;

    @SubscribeEvent
    public static void onRegisterBiome(RegistryEvent.Register<Biome> event)
    {
        event.getRegistry().register(new BiomeArcaneVoid(new Biome.BiomeProperties("arcane_void").setRainDisabled().setWaterColor(0x38393a)).setRegistryName(ArcaneWorld.MODID, "arcane_void"));

        BiomeDictionary.addTypes(ARCANE_VOID, BiomeDictionary.Type.VOID);
    }


    private static final WorldGenerator AMETHYST_GENERATOR = getOreGenerator(
            ArcaneWorldBlocks.ORE_AMETHYST::getDefaultState, b -> b.getBlock() == Blocks.END_STONE,ArcaneWorldConfig.ORES.AMETHYST_GENERATION.vein_size, ArcaneWorldConfig.ORES.AMETHYST_GENERATION.vein_count, ArcaneWorldConfig.ORES.AMETHYST_GENERATION.min_height, ArcaneWorldConfig.ORES.AMETHYST_GENERATION.max_height, 1
    );

    private static final WorldGenerator AMETHYST_GENERATOR_NETHER = getOreGenerator(
            ArcaneWorldBlocks.ORE_AMETHYST_NETHER::getDefaultState, b -> b.getBlock() == Blocks.NETHERRACK,ArcaneWorldConfig.ORES.AMETHYST_GENERATION_NETHER.vein_size, ArcaneWorldConfig.ORES.AMETHYST_GENERATION_NETHER.vein_count, ArcaneWorldConfig.ORES.AMETHYST_GENERATION_NETHER.min_height, ArcaneWorldConfig.ORES.AMETHYST_GENERATION_NETHER.max_height, -1
    );

    private static final WorldGenerator RIFT_GENERATE = getRiftGenerator();


    @SubscribeEvent
    public static void onPopulateChunk(PopulateChunkEvent.Pre event)
    {
        World world = event.getWorld();
        Random rand = event.getRand();
        BlockPos pos = new ChunkPos(event.getChunkX(), event.getChunkZ()).getBlock(8, 0, 8);
        Biome biome = world.getBiome(pos);

        //Create sapphire generator here since it needs to check the biome.
        //TODO: look into moving this elsewhere so doesn't need to be created more than once
        getOreGenerator(ArcaneWorldBlocks.ORE_SAPPHIRE::getDefaultState, b -> b.getBlock() == Blocks.STONE, ArcaneWorldConfig.ORES.SAPPHIRE_GENERATION.vein_size, isWetBiome(biome) ? ArcaneWorldConfig.ORES.SAPPHIRE_GENERATION.vein_count * 3 : ArcaneWorldConfig.ORES.SAPPHIRE_GENERATION.vein_count, ArcaneWorldConfig.ORES.SAPPHIRE_GENERATION.min_height, ArcaneWorldConfig.ORES.SAPPHIRE_GENERATION.max_height, 0).generate(world, rand, pos);

        AMETHYST_GENERATOR.generate(world, rand, pos);
        AMETHYST_GENERATOR_NETHER.generate(world, rand, pos);

        //Generate rifts
        getRiftGenerator().generate(world, rand, pos);
    }

    private static WorldGenerator getOreGenerator(Supplier<IBlockState> state, Predicate<IBlockState> predicate, int size, int count, int minHeight, int maxHeight, int... dims)
    {
        return new FeatureDimension(new FeatureRange(new FeatureVein(b -> state.get(), size, predicate), count, minHeight, maxHeight), dims);
    }

    private static WorldGenerator getRiftGenerator()
    {
        return new FeatureChance(new FeatureRift(), ArcaneWorldConfig.RIFTS.SPAWN_CHANCE);
    }

    private static boolean isWetBiome(Biome biome)
    {
       return BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN).contains(biome) ||
               BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER).contains(biome) ||
               BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP).contains(biome);
    }
}
