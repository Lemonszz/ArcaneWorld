package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.crafting.ritual.Ritual;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualCreateItem extends Ritual
{
    private final ItemStack result;
    public RitualCreateItem(ItemStack result, Ingredient... ing)
    {
        super(ing);

        this.result = result;
    }

    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos)
    {
        EntityItem item = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.6F, pos.getZ() + 0.5F, result);
        item.setDefaultPickupDelay();

        item.motionY = -0.25F;

        world.spawnEntity(item);
    }

    public ItemStack getItemstack()
    {
        return result;
    }
}
