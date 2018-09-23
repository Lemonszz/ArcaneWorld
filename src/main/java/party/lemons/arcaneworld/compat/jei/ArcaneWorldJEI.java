package party.lemons.arcaneworld.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import party.lemons.arcaneworld.block.ArcaneWorldBlocks;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;
import party.lemons.arcaneworld.crafting.ritual.container.ContainerRitual;
import party.lemons.arcaneworld.crafting.ritual.container.GuiRitual;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.item.ArcaneWorldItems;

import java.util.stream.Collectors;

/**
 * Created by Sam on 12/09/2018.
 */
@JEIPlugin
public class ArcaneWorldJEI implements IModPlugin {

    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new RitualRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    public void register(IModRegistry registry) {
        registry.handleRecipes(Ritual.class, r -> new RitualRecipeWrapper(registry.getJeiHelpers(), r), RitualRecipeCategory.ID);
        registry.addRecipes(RitualRegistry.REGISTRY.getValuesCollection().stream().filter(r -> !r.isEmpty()).collect(Collectors.toList()), RitualRecipeCategory.ID);
        registry.addRecipeClickArea(GuiRitual.class, 0, 0, 20, 20, RitualRecipeCategory.ID);
        registry.addRecipeCatalyst(new ItemStack(ArcaneWorldBlocks.RITUAL_TABLE), RitualRecipeCategory.ID);

        registry.getRecipeTransferRegistry().addRecipeTransferHandler(
                ContainerRitual.class,
                RitualRecipeCategory.ID,
                0, 5,
                5, 36
        );

        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ArcaneWorldItems.RECALL_EYE));
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ArcaneWorldBlocks.RETURN_PORTAL));
    }
}