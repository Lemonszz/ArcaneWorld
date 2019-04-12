package party.lemons.arcaneworld.item;


import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ArcaneWorldTab;
import party.lemons.arcaneworld.item.impl.*;
import party.lemons.arcaneworld.util.Pair;
import party.lemons.lemonlib.item.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 30/08/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class ArcaneWorldItems
{
	public static final Item ARCANE_HOE = Items.AIR;
	public static final Item FANG_WAND = Items.AIR;
	public static final Item SAPPHIRE = Items.AIR;
	public static final Item AMETHYST = Items.AIR;
	public static final Item GLOWING_CHORUS = Items.AIR;
	public static final Item BIOME_CRYSTAL = Items.AIR;
	public static final Item RITUAL_SCROLL = Items.AIR;
    public static final Item RECALLER = Items.AIR;
    public static final Item RECALL_EYE = Items.AIR;
    public static final Item MOLTEN_CORE = Items.AIR;
    public static final Item MOLTEN_PICKAXE = Items.AIR;
    public static final Item MOLTEN_SHOVEL = Items.AIR;
    public static final Item MOLTEN_AXE = Items.AIR;
    public static final Item POTION_ORB = Items.AIR;
    public static final Item GROWTH_POWDER = Items.AIR;

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		ItemRegistry.setup(ArcaneWorld.MODID, event.getRegistry(), ArcaneWorldTab.INSTANCE);

		ItemRegistry.registerItem(new ItemArcaneHoe(), "arcane_hoe");
		ItemRegistry.registerItem(new ItemFangWand(), "fang_wand");
		ItemRegistry.registerItem(new ItemModel(), "sapphire", "gemSapphire");
		ItemRegistry.registerItem(new ItemModel(), "amethyst", "gemAmethyst");
		ItemRegistry.registerItem(new ItemGlowingChorusFruit(), "glowing_chorus");
		ItemRegistry.registerItem(new ItemRecaller(), "recaller");
		ItemRegistry.registerItem(new ItemBiomeCrystal(5), "biome_crystal");
		ItemRegistry.registerItem(new ItemRitualScroll(), "ritual_scroll");
		ItemRegistry.registerItem(new ItemModel(), "molten_core");
		ItemRegistry.registerItem(new ItemMoltenPickaxe(Item.ToolMaterial.IRON), "molten_pickaxe");
		ItemRegistry.registerItem(new ItemMoltenShovel(Item.ToolMaterial.IRON), "molten_shovel");
		ItemRegistry.registerItem(new ItemMoltenAxe(Item.ToolMaterial.IRON), "molten_axe");
		ItemRegistry.registerItem(new ItemEtherealSword(Item.ToolMaterial.DIAMOND), "ethereal_sword");
		ItemRegistry.registerItem(new ItemPotionOrb(), "potion_orb");
		ItemRegistry.registerItem(new ItemGrowthPowder(), "growth_powder");
		ItemRegistry.registerItem( new ItemInternal(), "recall_eye");

	}
}