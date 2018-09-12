package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualCreateItem extends Ritual
{
    private final ItemStack result;
    public RitualCreateItem(ItemStack result, ItemStack... ing)
    {
        super(ing);

        this.result = result;
    }

    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NonNullList<ItemStack> ingredients, @Nullable EntityPlayer activator)
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
