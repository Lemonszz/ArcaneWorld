package party.lemons.arcaneworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.crafting.ArcaneWorldTab;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.item.IModel;
import party.lemons.arcaneworld.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 30/08/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class ArcaneWorldBlocks
{
    public static List<Block> blockList = new ArrayList<>();
	private static List<Pair<Block, String[]>> oreDict = new ArrayList<>();

	public static final Block RITUAL_TABLE = Blocks.AIR;
	public static final Block ORE_SAPPHIRE =Blocks.AIR ;
	public static final Block ORE_AMETHYST =Blocks.AIR ;
	public static final Block RETURN_PORTAL =Blocks.AIR ;

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> r = event.getRegistry();

		setProperties(registerBlock(r, new BlockArcaneOre(3, 2, ()->ArcaneWorldItems.SAPPHIRE), "ore_sapphire", "oreSapphire"), 3F, 5F, 0F);
		setProperties(registerBlock(r, new BlockArcaneOre(2, 4, ()->ArcaneWorldItems.AMETHYST), "ore_amethyst", "oreAmethyst"), 3F, 5F, 0F);
		setProperties(registerBlock(r, new BlockModel(Material.IRON), "block_sapphire", "blockSapphire"), 3F, 5F, 0F);
		setProperties(registerBlock(r, new BlockModel(Material.IRON), "block_amethyst", "blockAmethyst"), 3F, 5F, 0F);
		setProperties(registerBlock(r, new BlockRitualTable(), "ritual_table"), 3F, 5F, 0F);

        Block portal = new BlockReturnPortal().setBlockUnbreakable().setRegistryName(ArcaneWorld.MODID, "return_portal");
        blockList.add(portal);
        r.register(portal);

		GameRegistry.registerTileEntity(TileEntityRitualTable.class, new ResourceLocation(ArcaneWorld.MODID, "ritual_table"));
	}

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		blockList.stream().filter(b-> (b instanceof IModel) && ((IModel) b).hasModel()).forEach(b -> registerItemBlock(event.getRegistry(), b));
	}

	public static void registerItemBlock(IForgeRegistry<Item> registry, Block block)
	{
		ItemBlock ib = new ItemBlock(block);
		ib.setRegistryName(block.getRegistryName());

		ArcaneWorldItems.itemList.add(ib);
		registry.register(ib);
	}

	public static Block setProperties(Block block, float hardness, float resistence, float light)
	{
		return block.setHardness(hardness).setResistance(resistence).setLightLevel(light);
	}

	public static Block registerBlock(IForgeRegistry<Block> registry, Block block, String name, String... oreDict)
	{
		return registerBlock(registry, block, name, ArcaneWorld.MODID, true, oreDict);
	}

	public static Block registerBlock(IForgeRegistry<Block> registry, Block block, String name, String domain, boolean addDomainToUnloc, String... ores)
	{
		String unloc = addDomainToUnloc ? (domain + ".") : "";

		block.setTranslationKey(unloc + name);
		block.setRegistryName(domain, name);
		block.setCreativeTab(ArcaneWorldTab.INSTANCE);

		if(ores.length > 0)
			oreDict.add(Pair.of(block, ores));

		blockList.add(block);
		registry.register(block);

		return block;
	}

	public static List<Pair<Block, String[]>> getOreDictEntries()
	{
		return oreDict;
	}
}