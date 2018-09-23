package party.lemons.arcaneworld.crafting.ritual;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ritual.impl.*;
import party.lemons.arcaneworld.item.ArcaneWorldItems;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 13/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public class Rituals {

    @SubscribeEvent
    public static void onRegisterRitual(RegistryEvent.Register<Ritual> event)
    {
        createRitual(event.getRegistry(), new RitualScroll(of(ArcaneWorldItems.RITUAL_SCROLL)), "ritual_scroll");
        createRitual(event.getRegistry(), new Ritual() {public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items) { }}.setEmpty(), "empty");
        createRitual(event.getRegistry(), new RitualTime(6000, of(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0)), of("dustGlowstone"), of("nuggetGold"), of("nuggetGold")), "time_skip");
        createRitual(event.getRegistry(), new RitualTime(-6000, of("gemQuartz"), of("dustRedstone"), of("nuggetGold"), of("nuggetGold")), "time_rewind");
        createRitual(event.getRegistry(), new RitualWeather(RitualWeather.WeatherType.CLEAR, of("sand"), of(Items.BLAZE_POWDER)), "clear_skies");
        createRitual(event.getRegistry(), new RitualWeather(RitualWeather.WeatherType.RAIN, of("dustPrismarine"), of("gemPrismarine")), "raining");
        createRitual(event.getRegistry(), new RitualWeather(RitualWeather.WeatherType.THUNDER, of("dustPrismarine"), of("gemPrismarine"), of("gunpowder")), "thundering");
        createRitual(event.getRegistry(), new RitualDragonBreath(of(Items.DRAGON_BREATH), of(Items.ENDER_EYE), of("blockPrismarine"), of("netherrack"), of("ingotGold")), "dragon_breath");
        createRitual(event.getRegistry(), new RitualDungeon(of("blockSapphire"), of("ingotGold")),"dungeon");

        createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.ARCANE_HOE), of(Items.GOLDEN_HOE), of("gemDiamond"), of("cropNetherWart"), of(Items.RABBIT_FOOT), of("dyeBlack")), "create_arcane_hoe");
        createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.FANG_WAND), of("stickWood"), of("gemDiamond"), of("ingotGold"), of("dustGlowstone"), of("enderpearl")), "create_evoking_wand");
        createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.GLOWING_CHORUS), of(Items.CHORUS_FRUIT), of("obsidian"), of("dustGlowstone"), of(Items.ENDER_EYE), of(Items.DRAGON_BREATH)), "create_glowing_chorus");
        createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.BIOME_CRYSTAL), of("gemDiamond"), of("dustRedstone"), of("grass"), of(Items.ENDER_EYE), of(Items.DRAGON_BREATH)), "create_biome_crystal");
        createRitual(event.getRegistry(), new RitualCreateItem(new ItemStack(ArcaneWorldItems.RECALLER), of("obsidian"), of(Items.ENDER_EYE), of("obsidian"), of("gemAmethyst")), "create_recaller");

        createSummonRitual(event.getRegistry(), EntityZombie.class, of(Items.ROTTEN_FLESH), true, "summon_zombie");
        createSummonRitual(event.getRegistry(), EntitySkeleton.class, of(Items.BOW), true, "summon_skeleton");
        createSummonRitual(event.getRegistry(), EntitySpider.class, of("string"), true, "summon_spider");
        createSummonRitual(event.getRegistry(), EntityCaveSpider.class, of(Items.SPIDER_EYE), true, "summon_cave_spider");
        createSummonRitual(event.getRegistry(), EntityEnderman.class, of("enderpearl"), true, "summon_enderpearl");
        createSummonRitual(event.getRegistry(), EntityPigZombie.class, of("ingotGold"), true, "summon_zombie_pigman");
        createSummonRitual(event.getRegistry(), EntityBlaze.class, of(Items.BLAZE_ROD), true, "summon_blaze");
        createSummonRitual(event.getRegistry(), EntityCreeper.class, of("gunpowder"), true, "summon_creeper");
        createSummonRitual(event.getRegistry(), EntityElderGuardian.class, of(Blocks.SPONGE), true, "summon_elder_guardian");
        createSummonRitual(event.getRegistry(), EntityEndermite.class, of(Items.ENDER_EYE), true, "summon_endermite");
        createSummonRitual(event.getRegistry(), EntityGhast.class, of(Items.GHAST_TEAR), true, "summon_ghast");
        createSummonRitual(event.getRegistry(), EntityGuardian.class, of("dustPrismarine"), true, "summon_guardian");
        createSummonRitual(event.getRegistry(), EntityMagmaCube.class, of(Items.MAGMA_CREAM), true, "summon_magma_cube");
        createSummonRitual(event.getRegistry(), EntityShulker.class, of(Blocks.PURPLE_SHULKER_BOX), true, "summon_shulker");
        createSummonRitual(event.getRegistry(), EntitySilverfish.class, of("stone"), true, "summon_silverfish");
        createSummonRitual(event.getRegistry(), EntitySlime.class, of("slimeball"), true, "summon_slimeball");
        createSummonRitual(event.getRegistry(), EntityWitch.class, of("dustRedstone"), true, "summon_witch");
        createSummonRitual(event.getRegistry(), EntityVindicator.class,of(Items.IRON_AXE), true, "summon_vindicator");
        createSummonRitual(event.getRegistry(), EntityEvoker.class, of(Items.TOTEM_OF_UNDYING), true, "summon_evoker");
        createSummonRitual(event.getRegistry(), EntityPolarBear.class, of(Blocks.SNOW), true, "summon_polarbear");
        createSummonRitual(event.getRegistry(), EntityIllusionIllager.class, of(Items.CAULDRON), true, "summon_illusioner");

        createSummonRitual(event.getRegistry(), EntityBat.class, of(Items.SUGAR), false, "summon_bat");
        createSummonRitual(event.getRegistry(), EntityChicken.class, of("egg"), false, "summon_chicken");
        createSummonRitual(event.getRegistry(), EntityCow.class, of(Items.BEEF), false, "summon_cow");
        createSummonRitual(event.getRegistry(), EntityDonkey.class, of(Items.GOLDEN_CARROT), false, "summon_donkey");
        createSummonRitual(event.getRegistry(), EntityHorse.class, of(Items.APPLE), false, "summon_horse");
        createSummonRitual(event.getRegistry(), EntityMooshroom.class, of(Blocks.RED_MUSHROOM_BLOCK), false, "summon_mooshroom");
        createSummonRitual(event.getRegistry(), EntityOcelot.class,of(Items.FISH), false, "summon_ocelot");
        createSummonRitual(event.getRegistry(), EntityParrot.class, of("feather"), false, "summon_parrot");
        createSummonRitual(event.getRegistry(), EntityPig.class, of(Items.PORKCHOP), false, "summon_pig");
        createSummonRitual(event.getRegistry(), EntityRabbit.class, of(Items.RABBIT_FOOT), false, "summon_rabbit");
        createSummonRitual(event.getRegistry(), EntitySheep.class, of(Items.MUTTON), false, "summon_sheep");
        createSummonRitual(event.getRegistry(), EntitySquid.class, of(new ItemStack(Items.DYE, 1, 0)), false, "summon_squid");
        createSummonRitual(event.getRegistry(), EntityVillager.class, of("gemEmerald"), false, "summon_villager");
        createSummonRitual(event.getRegistry(), EntityLlama.class, of(Blocks.CARPET), false, "summon_llama");
        createSummonRitual(event.getRegistry(), EntityWolf.class, of(Items.COOKED_BEEF), false, "summon_wolf");
    }

    public static Ritual createRitual(IForgeRegistry<Ritual> registry, Ritual ritual, String name)
    {
        registry.register(ritual.setRegistryName(ArcaneWorld.MODID, name).setTranslationKey(ArcaneWorld.MODID + "." + name));

        return ritual;
    }

    public static Ritual createSummonRitual(IForgeRegistry<Ritual> registry, Class<? extends EntityLiving> entity, Ingredient primary, boolean isHostile, String name)
    {
        Ingredient secondary = of(isHostile ? "gemAmethyst" : "gemSapphire");
        Ingredient bones = of("bone");

        return createRitual(registry, new RitualSummon(entity, primary, secondary, bones), name);
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

}
