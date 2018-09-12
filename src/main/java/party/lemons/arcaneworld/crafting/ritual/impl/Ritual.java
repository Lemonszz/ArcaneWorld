package party.lemons.arcaneworld.crafting.ritual.impl;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sam on 10/09/2018.
 */
public class Ritual extends IForgeRegistryEntry.Impl<Ritual>
{
	private final NonNullList<ItemStack> ingredients;
	private boolean empty = false;

	public Ritual(ItemStack... ingredients)
	{
		this.ingredients = NonNullList.withSize(5, ItemStack.EMPTY);

		for(int i = 0; i < ingredients.length; i++)
		{
			this.ingredients.set(i, ingredients[i]);
		}
	}

	public Ritual setEmpty()
	{
		this.empty = true;
		return this;
	}

	public boolean matches(NonNullList<ItemStack> inputs)
	{
		if(this.isEmpty())
			return false;

		List<ItemStack> inputList = new ArrayList<>();
		for(int i = 0; i < inputs.size(); i++)
			inputList.add(inputs.get(i));
		inputList.sort(Comparator.comparing(s -> s.getItem().getRegistryName()));

		List<ItemStack> ingredList = new ArrayList<>();
		for(int i = 0; i < ingredients.size(); i++)
			ingredList.add(ingredients.get(i));
		ingredList.sort(Comparator.comparing(s -> s.getItem().getRegistryName()));

		inputList.removeIf(is -> is.isEmpty());
		ingredList.removeIf(is -> is.isEmpty());

		if(inputList.size() == ingredList.size())
		{
			for(int i = 0; i < ingredList.size(); i++)
			{
				ItemStack s1 = inputList.get(i);
				ItemStack s2 = ingredList.get(i);

				if(!s1.isItemEqual(s2))
					return false;
			}
			return true;
		}
		return false;
	}

	public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NonNullList<ItemStack> ingredients, @Nullable EntityPlayer activator)
	{

	}

	public boolean isEmpty()
	{
		return empty;
	}

    public NonNullList<ItemStack>  getRequiredItems() {
		return ingredients;
    }
}
