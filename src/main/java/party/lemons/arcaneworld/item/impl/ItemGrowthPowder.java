package party.lemons.arcaneworld.item.impl;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import party.lemons.arcaneworld.entity.EntityOvergrownSheep;

import java.util.Random;

/**
 * Created by Sam on 27/10/2018.
 */
public class ItemGrowthPowder extends ItemModel
{
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        boolean isRegularSheep = target.getClass() == EntitySheep.class;    //If we should handle the entity as a regular sheep
        World world = target.world;

        if(target instanceof EntitySheep)   //Only deal with sheep
        {
            EntitySheep sheep = (EntitySheep) target;
            boolean canGrow;

            if(!world.isRemote)
            {
                if (isRegularSheep) //If it's a regular sheep, turn the sheep into an overgrown sheep
                {
                    //Copy sheep as overgrown sheep
                    EntityOvergrownSheep overgrownSheep = new EntityOvergrownSheep(world);
                    overgrownSheep.setLocationAndAngles(sheep.posX, sheep.posY, sheep.posZ, sheep.rotationYaw, sheep.rotationPitch);
                    overgrownSheep.setFleeceColor(sheep.getFleeceColor());
                    overgrownSheep.rotationYaw = sheep.rotationYaw;
                    overgrownSheep.rotationPitch = sheep.rotationPitch;
                    overgrownSheep.rotationYawHead = sheep.rotationYawHead;
                    overgrownSheep.prevRotationPitch = sheep.prevRotationPitch;
                    overgrownSheep.prevRotationYaw = sheep.prevRotationYaw;
                    overgrownSheep.prevRotationYawHead = sheep.prevRotationYawHead;
                    overgrownSheep.setCustomNameTag(sheep.getCustomNameTag());

                    world.spawnEntity(overgrownSheep);
                    sheep.setDead();
                    sheep = overgrownSheep;

                }
                //If the sheep can grow
                canGrow = ((EntityOvergrownSheep)sheep).getGrowth() < EntityOvergrownSheep.MAX_GROWTH;

                if(canGrow) //Grow the sheep
                {
                    stack.shrink(1);
                    sheep.setSheared(false);
                }

                return canGrow;
            }
            else    //if we're on the client
            {
                //If we're a regular sheep or the sheep can grow, spawn particles
                if(isRegularSheep || ((EntityOvergrownSheep)sheep).getGrowth() < EntityOvergrownSheep.MAX_GROWTH)
                {
                    Random rand = world.rand;
                    double posX = sheep.posX;
                    double posY = sheep.posY;
                    double posZ = sheep.posZ;
                    double width = sheep.width * 2F;
                    double height = sheep.height * 2F;

                    for (int i = 0; i < 5; i++)
                        world.spawnParticle(
                                EnumParticleTypes.VILLAGER_HAPPY,
                                posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width,
                                (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D
                        );

                    return true;
                }
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
