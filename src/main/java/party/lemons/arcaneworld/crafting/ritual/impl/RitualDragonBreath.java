package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.MobEffects;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.crafting.ritual.Ritual;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 13/09/2018.
 */
public class RitualDragonBreath extends Ritual
{
    public RitualDragonBreath(Ingredient... ingredients)
    {
        super(ingredients);
    }

    @Override
    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos)
    {
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world, pos.getX() + 0.5F, pos.getY() + 1F, pos.getZ() + 0.5F);
        entityareaeffectcloud.setOwner(new EntityDragon(world));
        entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setDuration(600);
        entityareaeffectcloud.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));

        world.playEvent(2006, pos, 0);
        world.spawnEntity(entityareaeffectcloud);
    }
}
