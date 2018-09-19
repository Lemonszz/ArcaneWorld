package party.lemons.arcaneworld.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.config.ConfigEntitySpawn;

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

        ConfigEntitySpawn illusionerSpawn = ArcaneWorldConfig.ENTITIES.ILLUSIONER_SPAWN;
        ConfigEntitySpawn vindicatorSpawn = ArcaneWorldConfig.ENTITIES.VINDICATOR_SPAWN;
        ConfigEntitySpawn evokerSpawn = ArcaneWorldConfig.ENTITIES.EVOKER_SPAWN;

        if(illusionerSpawn.enabled)
            EntityRegistry.addSpawn(EntityIllusionIllager.class, illusionerSpawn.rarity, illusionerSpawn.min, illusionerSpawn.max, EnumCreatureType.MONSTER, biomes);

        if(vindicatorSpawn.enabled)
            EntityRegistry.addSpawn(EntityVindicator.class, vindicatorSpawn.rarity, vindicatorSpawn.min, vindicatorSpawn.max, EnumCreatureType.MONSTER, biomes);

        if(evokerSpawn.enabled)
            EntityRegistry.addSpawn(EntityEvoker.class, evokerSpawn.rarity, evokerSpawn.min, evokerSpawn.max, EnumCreatureType.MONSTER, biomes);
    }
}
