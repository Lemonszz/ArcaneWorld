package party.lemons.arcaneworld.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;
import party.lemons.arcaneworld.crafting.ritual.RitualScroll;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 16/09/2018.
 */
public class ItemRitualScroll extends ItemModel
{
    public ItemRitualScroll()
    {
        this.setHasSubtypes(true);
    }

    public static void setRitual(ItemStack stack, Ritual ritual)
    {
        NBTTagCompound tags;
        if(stack.hasTagCompound())
        {
            tags = stack.getTagCompound();
        }
        else
        {
            tags = new NBTTagCompound();
        }

        tags.setString("ritual", ritual.getRegistryName().toString());

        stack.setTagCompound(tags);
    }

    public static Ritual getRitual(ItemStack stack)
    {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("ritual"))
        {
            return RitualRegistry.REGISTRY.getValue(new ResourceLocation(stack.getTagCompound().getString("ritual")));
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        Ritual ritual = getRitual(stack);
        if (ritual != null)
            tooltip.add(TextFormatting.GOLD + I18n.format(ritual.getTranslationKey()));
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for(Ritual ritual : RitualRegistry.REGISTRY.getValuesCollection())
            {
                if(!ritual.isEmpty() && !(ritual instanceof RitualScroll))
                {
                    ItemStack stack = new ItemStack(this);
                    setRitual(stack, ritual);
                    items.add(stack);
                }
            }
        }
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        if (getRitual(stack) != null)
        {
            return I18n.format(getRitual(stack).getTranslationKey()) + " " + I18n.format(this.getTranslationKey() + ".name");
        }

        return super.getItemStackDisplayName(stack);
    }
}
