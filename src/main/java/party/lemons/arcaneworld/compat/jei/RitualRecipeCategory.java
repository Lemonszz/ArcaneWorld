package party.lemons.arcaneworld.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.ArcaneWorldBlocks;
import party.lemons.arcaneworld.crafting.ritual.impl.RitualCreateItem;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualRecipeCategory implements IRecipeCategory<RitualRecipeWrapper>
{
    public static final ResourceLocation RITUAL_BG = new ResourceLocation(ArcaneWorld.MODID, "textures/gui/ritual_jei.png");
    public static final String ID = "arcaneworld.ritual";
    private String locName;
    private IDrawable background;
    private IDrawable icon;

    public RitualRecipeCategory(IGuiHelper guiHelper)
    {
        //TODO: translate this properly
        locName = "Rituals";
        background = guiHelper.createDrawable(RITUAL_BG, 0, 0, 176, 38);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ArcaneWorldBlocks.RITUAL_TABLE));
    }

    @Override
    public String getUid() {
        return ID;
    }

    @Override
    public String getTitle() {
        return locName;
    }

    @Override
    public String getModName() {
        return ArcaneWorld.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, RitualRecipeWrapper wrapper, IIngredients ingredients) {
        if(!(wrapper instanceof RitualRecipeWrapper))
            return;

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> output = ingredients.getOutputs(ItemStack.class);

        int in = 0;
        for(int i = 0; i < inputs.get(0).size(); i++)
        {
            layout.getItemStacks().init(i, true, 43 + (18 * i), 9);
            layout.getItemStacks().set(in++, inputs.get(0).get(i));
        }

        if(output.size() > 0)
        for(int i = 0; i < output.get(0).size(); i++) {
            layout.getItemStacks().init(in, true, 43 + (20 * in), 9);
            layout.getItemStacks().set(in++, output.get(0).get(i));
        }
    }
}
