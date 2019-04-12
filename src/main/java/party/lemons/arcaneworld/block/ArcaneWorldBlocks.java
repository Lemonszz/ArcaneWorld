package party.lemons.arcaneworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import party.lemons.arcaneworld.block.tilentity.TileEntityLevitator;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.crafting.ArcaneWorldTab;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.util.Pair;
import party.lemons.lemonlib.block.BlockRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 30/08/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class ArcaneWorldBlocks
{

	public static final Block RITUAL_TABLE = Blocks.AIR;
	public static final Block ORE_SAPPHIRE =Blocks.AIR ;
	public static final Block ORE_AMETHYST =Blocks.AIR ;
	public static final Block ORE_AMETHYST_NETHER =Blocks.AIR ;
	public static final Block RETURN_PORTAL =Blocks.AIR ;
	public static final Block LEVITATOR =Blocks.AIR ;

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event)
	{
		BlockRegistry.setup(ArcaneWorld.MODID, event.getRegistry(), ArcaneWorldTab.INSTANCE);

		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockArcaneOre(3, 2, ()->ArcaneWorldItems.SAPPHIRE), 3F, 5F, 0F),"ore_sapphire", "oreSapphire");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockArcaneOre(2, 4, ()->ArcaneWorldItems.AMETHYST), 3F, 5F, 0), "ore_amethyst", "oreAmethyst");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockArcaneOre(2, 4, ()->ArcaneWorldItems.AMETHYST), 3F, 5F, 0), "ore_amethyst_nether", "oreAmethyst");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockModel(Material.IRON), 3F, 5F, 0), "block_sapphire", "blockSapphire");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockModel(Material.IRON), 3F, 5F, 0), "block_amethyst", "blockAmethyst");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockRitualTable(), 3F, 5F, 0), "ritual_table");
		BlockRegistry.registerBlock(BlockRegistry.setProperties(new BlockLevitator(), 3F, 5F, 0), "levitator");

		BlockRegistry.registerBlock(new BlockReturnPortal().setBlockUnbreakable(), "return_portal");

		GameRegistry.registerTileEntity(TileEntityRitualTable.class, new ResourceLocation(ArcaneWorld.MODID, "ritual_table"));
		GameRegistry.registerTileEntity(TileEntityLevitator.class, new ResourceLocation(ArcaneWorld.MODID, "levitator"));
	}
}