package party.lemons.arcaneworld.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.config.ConfigEntitySpawn;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sam on 12/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public class ArcaneWorldEntities
{
    private static int ID = 0;

    @SubscribeEvent
    public static void onRegisterEntity(RegistryEvent.Register<EntityEntry> event)
    {
        event.getRegistry().register(EntityEntryBuilder.create().name("Rift").entity(EntityRift.class).id(new ResourceLocation(ArcaneWorld.MODID, "rift"), ID++).tracker(64, 3, false).build());
        event.getRegistry().register(EntityEntryBuilder.create().name("Overgrown Sheep").entity(EntityOvergrownSheep.class).id(new ResourceLocation(ArcaneWorld.MODID, "overgrown_sheep"), ID++).tracker(80, 3, true).build());
    }

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
