package party.lemons.arcaneworld.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sam on 12/09/2018.
 */
public class ArcaneWorldEntities
{
    public static void init()
    {
        List<Biome> biomesList =  ForgeRegistries.BIOMES.getValuesCollection().stream().filter(b -> !BiomeDictionary.getTypes(b).contains(BiomeDictionary.Type.NETHER) && !BiomeDictionary.getTypes(b).contains(BiomeDictionary.Type.END)).collect(Collectors.toList());
        Biome[] biomes = biomesList.toArray(new Biome[biomesList.size()]);
        System.out.println(biomes.length + " " + ForgeRegistries.BIOMES.getValuesCollection().size());

        EntityRegistry.addSpawn(EntityIllusionIllager.class, 2, 1, 1, EnumCreatureType.MONSTER, biomes);
        EntityRegistry.addSpawn(EntityVindicator.class, 2, 1, 1, EnumCreatureType.MONSTER, biomes);
        EntityRegistry.addSpawn(EntityEvoker.class, 2, 1, 1, EnumCreatureType.MONSTER, biomes);
    }
}
