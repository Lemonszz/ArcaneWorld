package party.lemons.arcaneworld.crafting.ritual;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ritual.impl.*;
import party.lemons.arcaneworld.item.ArcaneWorldItems;

/**
 * Created by Sam on 10/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class RitualRegistry
{
	public static IForgeRegistry<Ritual> REGISTRY;

	public static final Ritual EMPTY = null;

	@SubscribeEvent
	public static void onRegisterRitual(RegistryEvent.Register<Ritual> event)
	{
		createRitual(event.getRegistry(), new Ritual().setEmpty(), "empty");
		createRitual(event.getRegistry(), new RitualTime(6000, of(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0)), of(new ItemStack(Items.GLOWSTONE_DUST)), of(new ItemStack(Items.GOLD_NUGGET)), of(new ItemStack(Items.GOLD_NUGGET))), "time_skip");
		createRitual(event.getRegistry(), new RitualTime(-6000, of(new ItemStack(Items.QUARTZ)), of(new ItemStack(Items.REDSTONE)), of(new ItemStack(Items.GOLD_NUGGET)), of(new ItemStack(Items.GOLD_NUGGET))), "time_rewind");
		createRitual(event.getRegistry(), new RitualWeather(RitualWeather.WeatherType.CLEAR, of(new ItemStack(Blocks.SAND)), of(new ItemStack(Items.BLAZE_POWDER))), "clear_skies");
		createRitual(event.getRegistry(), new RitualWeather(RitualWeather.WeatherType.RAIN, of(new ItemStack(Items.PRISMARINE_CRYSTALS)), of(new ItemStack(Items.PRISMARINE_SHARD))), "raining");
		createRitual(event.getRegistry(), new RitualWeather(RitualWeather.WeatherType.THUNDER, of(new ItemStack(Items.PRISMARINE_CRYSTALS)), of(new ItemStack(Items.PRISMARINE_SHARD)), of(new ItemStack(Items.GUNPOWDER))), "thundering");

		createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.ARCANE_HOE), of(new ItemStack(Items.GOLDEN_HOE)), of("plankWood"), of(new ItemStack(Items.NETHER_WART)), of(new ItemStack(Items.RABBIT_FOOT)), of(new ItemStack(Items.DYE, 1, 4))), "create_arcane_hoe");
		createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.FANG_WAND), of(new ItemStack(Items.STICK)), of("gemDiamond"), of(new ItemStack(Items.GOLD_INGOT)), of(new ItemStack(Items.GLOWSTONE_DUST)), of(new ItemStack(Items.ENDER_PEARL))), "create_evoking_wand");
		createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.GLOWING_CHORUS), of(new ItemStack(Items.CHORUS_FRUIT)), of(new ItemStack(Blocks.OBSIDIAN)), of(new ItemStack(Items.GLOWSTONE_DUST)), of(new ItemStack(Items.ENDER_EYE)), of(new ItemStack(Items.DRAGON_BREATH))), "create_glowing_chorus");

		createSummonRitual(event.getRegistry(), EntityZombie.class, new ItemStack(Items.ROTTEN_FLESH), true, "summon_zombie");
		createSummonRitual(event.getRegistry(), EntitySkeleton.class, new ItemStack(Items.BOW), true, "summon_skeleton");
		createSummonRitual(event.getRegistry(), EntitySpider.class, new ItemStack(Items.STRING), true, "summon_spider");
		createSummonRitual(event.getRegistry(), EntityCaveSpider.class, new ItemStack(Items.SPIDER_EYE), true, "summon_cave_spider");
		createSummonRitual(event.getRegistry(), EntityEnderman.class, new ItemStack(Items.ENDER_PEARL), true, "summon_enderpearl");
		createSummonRitual(event.getRegistry(), EntityPigZombie.class, new ItemStack(Items.GOLD_INGOT), true, "summon_zombie_pigman");
		createSummonRitual(event.getRegistry(), EntityBlaze.class, new ItemStack(Items.BLAZE_ROD), true, "summon_blaze");
		createSummonRitual(event.getRegistry(), EntityCreeper.class, new ItemStack(Items.GUNPOWDER), true, "summon_creeper");
		createSummonRitual(event.getRegistry(), EntityElderGuardian.class, new ItemStack(Blocks.SPONGE), true, "summon_elder_guardian");
		createSummonRitual(event.getRegistry(), EntityEndermite.class, new ItemStack(Items.ENDER_EYE), true, "summon_endermite");
		createSummonRitual(event.getRegistry(), EntityGhast.class, new ItemStack(Items.GHAST_TEAR), true, "summon_ghast");
		createSummonRitual(event.getRegistry(), EntityGuardian.class, new ItemStack(Items.PRISMARINE_CRYSTALS), true, "summon_guardian");
		createSummonRitual(event.getRegistry(), EntityMagmaCube.class, new ItemStack(Items.MAGMA_CREAM), true, "summon_magma_cube");
		createSummonRitual(event.getRegistry(), EntityShulker.class, new ItemStack(Blocks.PURPLE_SHULKER_BOX), true, "summon_shulker");
		createSummonRitual(event.getRegistry(), EntitySilverfish.class, new ItemStack(Blocks.STONE), true, "summon_silverfish");
		createSummonRitual(event.getRegistry(), EntitySlime.class, new ItemStack(Items.SLIME_BALL), true, "summon_slimeball");
		createSummonRitual(event.getRegistry(), EntityWitch.class, new ItemStack(Items.REDSTONE), true, "summon_witch");
		createSummonRitual(event.getRegistry(), EntityVindicator.class, new ItemStack(Items.IRON_AXE), true, "summon_vindicator");
		createSummonRitual(event.getRegistry(), EntityEvoker.class, new ItemStack(Items.TOTEM_OF_UNDYING), true, "summon_evoker");
		createSummonRitual(event.getRegistry(), EntityPolarBear.class, new ItemStack(Blocks.SNOW), true, "summon_polarbear");
		createSummonRitual(event.getRegistry(), EntityIllusionIllager.class, new ItemStack(Items.CAULDRON), true, "summon_illusioner");

		createSummonRitual(event.getRegistry(), EntityBat.class, new ItemStack(Items.SUGAR), false, "summon_bat");
		createSummonRitual(event.getRegistry(), EntityChicken.class, new ItemStack(Items.EGG), false, "summon_chicken");
		createSummonRitual(event.getRegistry(), EntityCow.class, new ItemStack(Items.BEEF), false, "summon_cow");
		createSummonRitual(event.getRegistry(), EntityDonkey.class, new ItemStack(Items.GOLDEN_CARROT), false, "summon_donkey");
		createSummonRitual(event.getRegistry(), EntityHorse.class, new ItemStack(Items.APPLE), false, "summon_horse");
		createSummonRitual(event.getRegistry(), EntityMooshroom.class, new ItemStack(Blocks.RED_MUSHROOM_BLOCK), false, "summon_mooshroom");
		createSummonRitual(event.getRegistry(), EntityOcelot.class, new ItemStack(Items.FISH), false, "summon_ocelot");
		createSummonRitual(event.getRegistry(), EntityParrot.class, new ItemStack(Items.FEATHER), false, "summon_parrot");
		createSummonRitual(event.getRegistry(), EntityPig.class, new ItemStack(Items.PORKCHOP), false, "summon_pig");
		createSummonRitual(event.getRegistry(), EntityRabbit.class, new ItemStack(Items.RABBIT_FOOT), false, "summon_rabbit");
		createSummonRitual(event.getRegistry(), EntitySheep.class, new ItemStack(Items.MUTTON), false, "summon_sheep");
		createSummonRitual(event.getRegistry(), EntitySquid.class, new ItemStack(Items.DYE, 1, 0), false, "summon_squid");
		createSummonRitual(event.getRegistry(), EntityVillager.class, new ItemStack(Items.EMERALD), false, "summon_villager");
		createSummonRitual(event.getRegistry(), EntityLlama.class, new ItemStack(Blocks.CARPET), false, "summon_llama");
		createSummonRitual(event.getRegistry(), EntityWolf.class, new ItemStack(Items.COOKED_BEEF), false, "summon_wolf");
	}

	public static Ritual createRitual(IForgeRegistry<Ritual> registry, Ritual ritual, String name)
	{
		registry.register(ritual.setRegistryName(ArcaneWorld.MODID, name));

		return ritual;
	}

	public static Ritual createSummonRitual(IForgeRegistry<Ritual> registry, Class<? extends EntityLiving> entity, ItemStack primary, boolean isHostile, String name)
	{
		ItemStack secondary = new ItemStack(isHostile ? ArcaneWorldItems.AMETHYST : ArcaneWorldItems.SAPPHIRE);
		ItemStack bones = new ItemStack(Items.BONE);

		return createRitual(registry, new RitualSummon(entity, of(primary), of(secondary), of(bones)), name);
	}

	public static Ingredient of(String oreDict)
	{
		return new OreIngredient(oreDict);
	}

	public static Ingredient of(Item item)
	{
		return of(new ItemStack(item));
	}

	public static Ingredient of(Block block)
	{
		return of(new ItemStack(block));
	}

	public static Ingredient of(ItemStack stack)
	{
		return Ingredient.fromStacks(stack);
	}

	@SubscribeEvent
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = new RegistryBuilder<Ritual>()
				.setType(Ritual.class)
				.setDefaultKey(new ResourceLocation(ArcaneWorld.MODID, "empty"))
				.setName(new ResourceLocation(ArcaneWorld.MODID, "rituals"))
				.create();
	}
}
