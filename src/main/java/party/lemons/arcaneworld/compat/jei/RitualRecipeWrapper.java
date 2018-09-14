package party.lemons.arcaneworld.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import party.lemons.arcaneworld.crafting.ritual.impl.Ritual;
import party.lemons.arcaneworld.crafting.ritual.impl.RitualCreateItem;
import sun.net.www.MimeEntry;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        String str = I18n.format(ritual.getTranslationKey());
        int length = Minecraft.getMinecraft().fontRenderer.getStringWidth(str);
        int height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        ArrayList<String> list = new ArrayList<>();
        if(mouseX >= 2 && mouseX <= 2 + length)
        {
            if(mouseY >= -2 && mouseY <= -2 + height)
                list.add(TextFormatting.DARK_GRAY + ritual.getRegistryName().toString());
        }

        return list;
    }


    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        String translate = I18n.format(ritual.getTranslationKey());
        Minecraft.getMinecraft().fontRenderer.drawString(translate, 2, -2, 0x333333);
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
