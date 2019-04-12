package party.lemons.arcaneworld.item.impl;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import party.lemons.lemonlib.item.IItemModel;

/**
 * Created by Sam on 15/10/2018.
 */
public class ItemEtherealSword extends ItemSword implements IItemModel
{
    public ItemEtherealSword(ToolMaterial material)
    {
        super(material);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        PotionEffect spectral = new PotionEffect(MobEffects.GLOWING, 250, 0, false, false);
        PotionEffect invis = new PotionEffect(MobEffects.INVISIBILITY, 250, 0, false, false);
        target.addPotionEffect(spectral);
        target.addPotionEffect(invis);

        return super.hitEntity(stack, target, attacker);
    }
}
