package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.crafting.ritual.Ritual;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualSummon extends Ritual
{
    private final Class<? extends Entity> entity;

    public RitualSummon(Class<? extends Entity> entity, Ingredient... ing)
    {
        super(ing);

        this.entity = entity;
    }

    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items)
    {
        try {
            Entity mob = entity.getConstructor(World.class).newInstance(world);
            if(mob instanceof EntityLiving)
                ((EntityLiving)mob).onInitialSpawn(world.getDifficultyForLocation(pos), null);
            mob.setPosition(pos.getX() + 0.5F, pos.getY() + mob.height, pos.getZ() + 0.5F);
            world.spawnEntity(mob);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
