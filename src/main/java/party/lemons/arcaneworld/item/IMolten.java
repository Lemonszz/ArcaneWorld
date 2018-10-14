package party.lemons.arcaneworld.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Created by Sam on 15/10/2018.
 */
public interface IMolten
{
    default boolean canSmelt(ItemStack stack)
    {
        return !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty();
    }

    default ItemStack getSmeltResult(ItemStack stack)
    {
        return FurnaceRecipes.instance().getSmeltingResult(stack);
    }
}
