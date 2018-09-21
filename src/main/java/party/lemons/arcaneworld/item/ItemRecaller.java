package party.lemons.arcaneworld.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 19/09/2018.
 */
public class ItemRecaller extends Item
{
    public ItemRecaller()
    {
        this.setMaxDamage(75);
        this.setMaxStackSize(1);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(getPosition(stack) != null)
        {
            if(getDimension(stack) != Integer.MAX_VALUE)
            {
                recall(player, world, stack);
                return EnumActionResult.SUCCESS;
            }
        }
        else
        {
            setPosition(stack, world, pos.offset(facing));
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        //TODO Duplicated code
        if(getPosition(stack) != null)
        {
            if (getDimension(stack) != Integer.MAX_VALUE)
            {
                recall(player, world, stack);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(getPosition(stack) != null && getDimension(stack) == worldIn.provider.getDimension())
        {
            BlockPos pos = getPosition(stack);
            tooltip.add(TextFormatting.GOLD + "X: " + pos.getX());
            tooltip.add(TextFormatting.GOLD + "Y: " + pos.getY());
            tooltip.add(TextFormatting.GOLD + "Z: " + pos.getZ());
        }
    }

    public void recall(EntityPlayer player, World world, ItemStack stack)
    {
        if(world.isRemote)
            return;

        BlockPos pos = getPosition(stack);
        ((WorldServer)world).spawnParticle(EnumParticleTypes.PORTAL, player.posX , player.posY + (player.height / 2), player.posZ,40, 0, 0, 0, 1D);
        player.setPositionAndUpdate(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        ((WorldServer)world).spawnParticle(EnumParticleTypes.PORTAL, player.posX , player.posY + (player.height / 2), player.posZ ,40, 0, 0, 0, 5D);

        stack.damageItem(1, player);
        setPosition(stack, world, null);
        player.getCooldownTracker().setCooldown(this, 25);

    }


    public static BlockPos getPosition(ItemStack stack)
    {
        if (!stack.hasTagCompound())
            return null;

        NBTTagCompound tags = stack.getTagCompound();
        if (tags.hasKey("position"))
            return NBTUtil.getPosFromTag(tags.getCompoundTag("position"));

        return null;
    }

    public static int getDimension(ItemStack stack)
    {
        if (!stack.hasTagCompound())
            return Integer.MAX_VALUE;

        NBTTagCompound tags = stack.getTagCompound();
        if (tags.hasKey("dim"))
            return tags.getInteger("dim");

        return Integer.MAX_VALUE;
    }

    public static void setPosition(ItemStack stack, World world, BlockPos pos)
    {
        if(world.isRemote)
            return;

        NBTTagCompound tags;
        if (!stack.hasTagCompound())
            tags = new NBTTagCompound();
        else
            tags = stack.getTagCompound();

        if (pos == null)
        {
            tags.removeTag("position");
            tags.removeTag("dim");
        }
        else
        {
            tags.setTag("position", NBTUtil.createPosTag(pos));
            tags.setInteger("dim", world.provider.getDimension());
        }

        stack.setTagCompound(tags);
    }
}
