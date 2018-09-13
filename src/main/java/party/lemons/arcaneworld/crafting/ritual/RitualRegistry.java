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
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = new RegistryBuilder<Ritual>()
				.setType(Ritual.class)
				.setDefaultKey(new ResourceLocation(ArcaneWorld.MODID, "empty"))
				.setName(new ResourceLocation(ArcaneWorld.MODID, "rituals"))
				.create();
	}
}
