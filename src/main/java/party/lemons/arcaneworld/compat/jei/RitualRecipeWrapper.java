package party.lemons.arcaneworld.compat.jei;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import party.lemons.arcaneworld.crafting.ritual.impl.Ritual;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualRecipeWrapper implements IRecipeWrapper
{
    private Ritual ritual;
    private List<List<ItemStack>> ingreds;

    public RitualRecipeWrapper(Ritual ritual)
    {
        this.ritual = ritual;

        ArrayList<ItemStack> stacks = new ArrayList<>();
        ingreds = new ArrayList<>();
        for(int i = 0; i < ritual.getRequiredItems().size(); i++)
        {
            stacks.add(ritual.getRequiredItems().get(i));
        }

        ingreds.add(stacks);
    }

    @Override
    public void getIngredients(IIngredients iIngredients)
    {
        iIngredients.setInputLists(ItemStack.class,ingreds);
    }
}
