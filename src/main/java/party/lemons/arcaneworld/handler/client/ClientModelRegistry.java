package party.lemons.arcaneworld.handler.client;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.item.IModel;
import party.lemons.arcaneworld.item.ItemBiomeCrystal;

/**
 * Created by Sam on 30/08/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID, value = Side.CLIENT)
public class ClientModelRegistry
{
	private static void registerSpecialModels()
	{
        ModelResourceLocation recaller_off = new ModelResourceLocation(ArcaneWorldItems.RECALLER.getRegistryName() + "_off", "inventory");
        ModelResourceLocation recaller_on = new ModelResourceLocation(ArcaneWorldItems.RECALLER.getRegistryName() + "_on", "inventory");
        ModelBakery.registerItemVariants(ArcaneWorldItems.RECALLER, recaller_off, recaller_on);
        ModelLoader.setCustomMeshDefinition(ArcaneWorldItems.RECALLER, s ->
        {
            if(s.hasTagCompound() && s.getTagCompound().hasKey("position"))
                return recaller_on;

            return recaller_off;
        });

    }

	@SubscribeEvent
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		ArcaneWorldItems.itemList.stream().filter(i -> i instanceof IModel).forEach(i -> registerModel((Item & IModel)i));
		ArcaneWorldItems.itemList.stream().filter(i -> i instanceof ItemBlock).forEach(i ->registerSimpleModel(i));

		registerSpecialModels();
	}

	@SubscribeEvent
	public static void onRegisterColor(ColorHandlerEvent.Item event)
	{
		event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
			if(ArcaneWorldItems.BIOME_CRYSTAL instanceof ItemBiomeCrystal)
			{
				if(((ItemBiomeCrystal) ArcaneWorldItems.BIOME_CRYSTAL).hasBiome(stack))
					return ((ItemBiomeCrystal) ArcaneWorldItems.BIOME_CRYSTAL).getBiome(stack).getGrassColorAtPos(DUMMY_POS);
			}

			return 0xFFFFFF;
		},ArcaneWorldItems.BIOME_CRYSTAL);
	}

	public static <ModelItem extends Item & IModel> void registerModel(ModelItem item)
	{
		if(item == Items.AIR || !item.hasModel())
			return;

		setModel(item, item.getModelLocation());
	}

	public static void registerSimpleModel(Item item)
	{
		if(item == Items.AIR)
			return;

		setModel(item, item.getRegistryName());
	}

	private static void setModel(Item item, ResourceLocation location)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(location, "inventory"));
	}

	private static final BlockPos DUMMY_POS = new BlockPos(0,0,0);
}