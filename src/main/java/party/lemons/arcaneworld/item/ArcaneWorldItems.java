package party.lemons.arcaneworld.item;


import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ArcaneWorldTab;
import party.lemons.arcaneworld.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 30/08/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class ArcaneWorldItems
{
	public static List<Item> itemList = new ArrayList<>();
	private static List<Pair<Item, String[]>> oreDict = new ArrayList<>();

	public static final Item ARCANE_HOE = Items.AIR;
	public static final Item FANG_WAND = Items.AIR;
	public static final Item SAPPHIRE = Items.AIR;
	public static final Item AMETHYST = Items.AIR;
	public static final Item GLOWING_CHORUS = Items.AIR;

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> r = event.getRegistry();

		registerItem(r, new ItemArcaneHoe(), "arcane_hoe");
		registerItem(r, new ItemFangWand(), "fang_wand");
		registerItem(r, new ItemModel(), "sapphire", "gemSapphire");
		registerItem(r, new ItemModel(), "amethyst", "gemAmethyst");
		registerItem(r, new ItemGlowingChorusFruit(), "glowing_chorus");
	}

	public static Item registerItem(IForgeRegistry<Item> registry, Item item, String name, String... oredict)
	{
		item.setTranslationKey(ArcaneWorld.MODID + "." + name);
		item.setRegistryName(ArcaneWorld.MODID, name);
		item.setCreativeTab(ArcaneWorldTab.INSTANCE);

		if(oredict.length > 0)
			oreDict.add(Pair.of(item, oredict));

		itemList.add(item);
		registry.register(item);

		return item;
	}

	public static List<Pair<Item, String[]>> getOreDictEntries()
	{
		return oreDict;
	}
}