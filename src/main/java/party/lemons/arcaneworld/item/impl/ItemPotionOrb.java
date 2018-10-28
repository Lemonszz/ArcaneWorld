package party.lemons.arcaneworld.item.impl;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ArcaneWorldSpamTab;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sam on 16/10/2018.
 */
public class ItemPotionOrb extends ItemModel
{
    public ItemPotionOrb()
    {

    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || !PotionUtils.getEffectsFromStack(stack).isEmpty();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        List<PotionEffect> list = PotionUtils.getEffectsFromStack(stack);
        List<Tuple<String, AttributeModifier>> list1 = Lists.newArrayList();

        if (list.isEmpty())
        {
            String s = I18n.translateToLocal("effect.none").trim();
            tooltip.add(TextFormatting.GRAY + s);
        }
        else
        {
            for (PotionEffect potioneffect : list)
            {
                String s1 = I18n.translateToLocal(potioneffect.getEffectName()).trim();
                Potion potion = potioneffect.getPotion();
                Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();

                if (!map.isEmpty())
                {
                    for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet())
                    {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Tuple(entry.getKey().getName(), attributemodifier1));
                    }
                }

                if (potioneffect.getAmplifier() > 0)
                {
                    s1 = s1 + " " + I18n.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
                }

                if (potion.isBadEffect())
                {
                    tooltip.add(TextFormatting.RED + s1);
                } else
                {
                    tooltip.add(TextFormatting.BLUE + s1);
                }
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(this.isInCreativeTab(tab))
        {
            for(PotionType effect : ForgeRegistries.POTION_TYPES)
            {
                if(!effect.getEffects().isEmpty())
                {
                    ItemStack stack = new ItemStack(this);
                    PotionUtils.addPotionToItemStack(stack, effect);
                    items.add(stack);
                }
            }
        }
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        boolean isPlayer = entityIn instanceof EntityPlayer;
        boolean isOffhand = isPlayer ? ((EntityPlayer)entityIn).getHeldItemOffhand() == stack : itemSlot == 0;

        if((isOffhand || isSelected) && entityIn instanceof EntityLivingBase)
        {
            applyPotionEffects(stack, (EntityLivingBase) entityIn);
        }
    }

    public List<PotionEffect> getPotionEffects(ItemStack stack)
    {
        return PotionUtils.getEffectsFromStack(stack);
    }

    public void applyPotionEffects(ItemStack stack, EntityLivingBase entity)
    {
        for(PotionEffect effect : getPotionEffects(stack))
        {
            PotionEffect addedEffect = new PotionEffect(effect.getPotion(), 3, effect.getAmplifier(), true, false);
            entity.addPotionEffect(addedEffect);
        }
    }

    @Nullable
    public CreativeTabs getCreativeTab()
    {
        return ArcaneWorldSpamTab.INSTANCE;
    }

    public static class SetPotionLoot extends LootFunction
    {
        Potion[] effects = new Potion[]{
                MobEffects.GLOWING,
                MobEffects.POISON,
                MobEffects.WITHER,
                MobEffects.SLOWNESS,
                MobEffects.SPEED,
                MobEffects.BLINDNESS,
                MobEffects.HASTE,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.JUMP_BOOST,
                MobEffects.LUCK,
                MobEffects.MINING_FATIGUE,
                MobEffects.NAUSEA,
                MobEffects.WEAKNESS,
                MobEffects.WATER_BREATHING,
                MobEffects.UNLUCK,
                MobEffects.STRENGTH,
                MobEffects.RESISTANCE,
                MobEffects.REGENERATION,
                MobEffects.NIGHT_VISION
        };


        protected SetPotionLoot(LootCondition[] conditionsIn)
        {
            super(conditionsIn);
        }

        @Override
        public ItemStack apply(ItemStack stack, Random rand, LootContext context)
        {
            PotionType[] effect_list = ForgeRegistries.POTION_TYPES.getValuesCollection().toArray(new PotionType[0]);
            int size = effect_list.length;

            PotionType effect = effect_list[rand.nextInt(size)];
            while(!isValidEffect(effect))
            {
                effect = effect_list[rand.nextInt(size)];
            }
            PotionUtils.addPotionToItemStack(stack, effect);

            return stack;
        }

        public boolean isValidEffect(PotionType effect)
        {
            for(int i = 0; i < effects.length; i++)
            {
                if(effect.getEffects().get(0).getPotion() == effects[i])
                {
                    return true;
                }
            }

            return false;
        }

        public static class Serializer extends LootFunction.Serializer<SetPotionLoot>
        {
            public Serializer()
            {
                super(new ResourceLocation(ArcaneWorld.MODID,"random_potion"), SetPotionLoot.class);
            }

            public  void serialize(@Nonnull JsonObject object, @Nonnull SetPotionLoot functionClazz, @Nonnull JsonSerializationContext serializationContext) {
            }

            @Nonnull
            public  SetPotionLoot deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext deserializationContext, @Nonnull LootCondition[] conditionsIn) {
                return new SetPotionLoot(conditionsIn);
            }
        }
    }
}
