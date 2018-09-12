package party.lemons.arcaneworld.handler;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.item.IModel;

/**
 * Created by Sam on 30/08/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID, value = Side.CLIENT)
public class ClientModelRegistry
{
	private static void registerSpecialModels()
	{
	}

	@SubscribeEvent
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		ArcaneWorldItems.itemList.stream().filter(i -> i instanceof IModel).forEach(i -> registerModel((Item & IModel)i));
		ArcaneWorldItems.itemList.stream().filter(i -> i instanceof ItemBlock).forEach(i ->registerSimpleModel(i));

		registerSpecialModels();
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
}