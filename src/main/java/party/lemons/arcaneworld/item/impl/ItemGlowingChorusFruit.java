package party.lemons.arcaneworld.item.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import party.lemons.arcaneworld.item.IModel;

/**
 * Created by Sam on 13/09/2018.
 */
public class ItemGlowingChorusFruit extends ItemFood implements IModel
{
    public ItemGlowingChorusFruit()
    {
        super(2, 0.3F, false);
        this.setAlwaysEdible();
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
       if(!worldIn.isRemote && !player.isRiding() && !player.isBeingRidden())
       {
           player.changeDimension(1);
       }

        super.onFoodEaten(stack, worldIn, player);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }


    @Override
    public ResourceLocation getModelLocation() {
        return getRegistryName();
    }
}
