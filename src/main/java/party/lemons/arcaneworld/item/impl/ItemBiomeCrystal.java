package party.lemons.arcaneworld.item.impl;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.arcaneworld.crafting.ArcaneWorldSpamTab;
import party.lemons.arcaneworld.gen.ArcaneWorldGen;
import party.lemons.arcaneworld.handler.ArcaneWorldSounds;
import party.lemons.arcaneworld.handler.ticker.TickerSetBiome;
import party.lemons.lemonlib.ticker.TickerHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 13/09/2018.
 */
public class ItemBiomeCrystal extends ItemModel
{
    private int radius;

    public ItemBiomeCrystal(int radius)
    {
        this.radius = radius;
        this.maxStackSize = 1;
        this.setMaxDamage(30);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);

        if (facing == EnumFacing.UP)
        {
            if (world.isRemote)
                return EnumActionResult.SUCCESS;

            boolean success;
            if (hasBiome(stack))
            {
                success = setBiomeInWorld(world, stack, pos);
                if (success)
                    setBiome(stack, null);
            } else
            {
                success = takeBiomeFromWorld(world, stack, pos);
            }
            int cooldown = success ? 20 : 4;
            SoundEvent sound = success ? ArcaneWorldSounds.GENERAL_WOOSH : ArcaneWorldSounds.GENERAL_BREAK;

            stack.damageItem(success ? 1 : 0, player);
            world.playSound(null, pos, sound, SoundCategory.PLAYERS, 1F, 0.75F + world.rand.nextFloat());
            player.getCooldownTracker().setCooldown(this, cooldown);
            return EnumActionResult.SUCCESS;

        }

        return EnumActionResult.FAIL;
    }

    protected boolean setBiomeInWorld(World world, ItemStack stack, BlockPos pos)
    {
        Biome biome = getBiome(stack);
        if (biome != world.getBiome(pos))
        {
            setBiomeInWorld(world, biome, pos);
            return true;
        }

        return false;
    }

    protected boolean setBiomeInWorld(World world, Biome biome, BlockPos pos)
    {
        if (biome == null)
            return false;

        TickerHandler.addTicker(new TickerSetBiome(world, biome, pos, radius), world.provider.getDimension());
        return true;
    }

    protected boolean takeBiomeFromWorld(World world, ItemStack stack, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        if (biome != ArcaneWorldGen.ARCANE_VOID)
        {
            setBiome(stack, biome);

            return setBiomeInWorld(world, ArcaneWorldGen.ARCANE_VOID, pos);
        }

        return false;
    }

    public boolean hasBiome(ItemStack stack)
    {
        if (!stack.hasTagCompound())
            return false;

        return stack.getTagCompound().hasKey("biome");
    }

    public @Nullable
    Biome getBiome(ItemStack stack)
    {
        if (!stack.hasTagCompound())
            return null;

        NBTTagCompound tags = stack.getTagCompound();
        if (tags.hasKey("biome"))
            return ForgeRegistries.BIOMES.getValue(new ResourceLocation(tags.getString("biome")));

        return null;
    }

    public void setBiome(ItemStack stack, Biome biome)
    {
        NBTTagCompound tags;
        if (!stack.hasTagCompound())
            tags = new NBTTagCompound();
        else
            tags = stack.getTagCompound();

        if (biome == null)
            tags.removeTag("biome");
        else
            tags.setString("biome", biome.getRegistryName().toString());

        stack.setTagCompound(tags);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        Biome biome = getBiome(stack);
        if (biome != null)
            tooltip.add(TextFormatting.GOLD + biome.getBiomeName());
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));

            for(Biome biome : ForgeRegistries.BIOMES.getValuesCollection())
            {
                if(!biome.isMutation())
                {
                    ItemStack biomeStack = new ItemStack(this);
                    this.setBiome(biomeStack, biome);

                    items.add(biomeStack);
                }
            }
        }
    }

    @Nullable
    public CreativeTabs getCreativeTab()
    {
        return ArcaneWorldSpamTab.INSTANCE;
    }
}
