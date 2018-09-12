package party.lemons.arcaneworld.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import party.lemons.arcaneworld.crafting.ritual.impl.Ritual;
import party.lemons.arcaneworld.crafting.ritual.impl.RitualCreateItem;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualRecipeWrapper implements IRecipeWrapper
{
    private Ritual ritual;
    private List<List<ItemStack>> ingreds;

    public RitualRecipeWrapper(IJeiHelpers helpers, Ritual ritual)
    {
        this.ritual = ritual;

        ingreds = helpers.getStackHelper().expandRecipeItemStackInputs(ritual.getRequiredItems());
    }

    public Ritual getRitual()
    {
        return ritual;
    }

    @Override
    public void getIngredients(IIngredients iIngredients)
    {
        iIngredients.setInputLists(ItemStack.class,ingreds);

        if(ritual instanceof RitualCreateItem)
        {
            iIngredients.setOutput(ItemStack.class, ((RitualCreateItem) ritual).getItemstack());
        }
    }
}
