package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 10/09/2018.
 */
public abstract class Ritual extends IForgeRegistryEntry.Impl<Ritual>
{
	private final List<Ingredient> ingredients;
	private boolean empty = false;

	public Ritual(Ingredient... ingredients)
	{
		this.ingredients = new ArrayList<>();
		for(int i = 0; i < 5; i++)
			this.ingredients.add(Ingredient.EMPTY);

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

		List<Ingredient> ingreds = new ArrayList<>(this.ingredients);
		List<ItemStack> inputList = new ArrayList<>();
		for(int i = 0; i < inputs.size(); i++)
			inputList.add(inputs.get(i));

		inputList.removeIf(is -> is.isEmpty());
		ingreds.removeIf(is -> is == Ingredient.EMPTY);

		for(Ingredient ingredient : ingreds)
		{
			int removeIndex = -1;
			for(int i = 0; i < inputList.size(); i++)
			{
				if(ingredient.apply(inputList.get(i)))
				{
					removeIndex = i;
					break;
				}
			}

			if(removeIndex == -1)
				return false;
			else
				inputList.remove(removeIndex);
		}
		return true;
	}

	public abstract void onActivate(@Nonnull World world, @Nonnull BlockPos pos);

	public boolean isEmpty()
	{
		return empty;
	}

    public List<Ingredient>  getRequiredItems() {
		return ingredients;
    }
}
