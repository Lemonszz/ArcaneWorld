package party.lemons.arcaneworld.entity;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.item.ArcaneWorldItems;

import java.util.List;

/**
 * Created by Sam on 3/10/2018.
 */
public class EntityRift extends Entity
{
    private int moveX, moveZ;
    private boolean hasCreatedExit;

    public EntityRift(World worldIn)
    {
        super(worldIn);
        this.setNoGravity(true);
        this.isImmuneToFire = true;
        this.setSize(3, 3);
    }

    public EntityRift(World world, int moveX, int moveZ)
    {
        this(world);

        this.moveX = moveX;
        this.moveZ = moveZ;
        this.hasCreatedExit = true;
    }

    @Override
    protected void entityInit()
    {
        int bound = 5000000;
        this.moveX = -bound + rand.nextInt(bound * 2);
        this.moveZ = -bound + rand.nextInt(bound * 2);

        if(this.posX + this.moveX > world.getWorldBorder().maxX() || this.posX + this.moveX < world.getWorldBorder().minX())
            this.moveX = 0;

        if(this.posZ + this.moveZ > world.getWorldBorder().maxZ() || this.posZ + this.moveZ < world.getWorldBorder().minZ())
            this.moveZ = 0;

        hasCreatedExit = false;
    }

    public void onKillCommand()
    {
        //No
    }

    public void onUpdate()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 5; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D), -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D));
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }

        if(world.isRemote || this.ticksExisted < 100)
            return;

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity = list.get(i);

            if (!entity.isDead)
            {
                teleportEntity(entity);
            }
        }
    }


    public void teleportEntity(Entity e)
    {
        if(world.isRemote || e.isRiding() || e.isBeingRidden())
            return;

        if(e instanceof EntityPlayer)
        {
            if(((EntityPlayer) e).getCooldownTracker().getCooldown(ArcaneWorldItems.RECALL_EYE, 0) != 0)
                return;
            else
                ((EntityPlayer) e).getCooldownTracker().setCooldown(ArcaneWorldItems.RECALL_EYE, 120);
        }

        if(!hasCreatedExit)
            createExit();

        BlockPos exitPos = getExitPosition();
        e.setPositionAndUpdate(exitPos.getX(), exitPos.getY(), exitPos.getZ());
    }

    public BlockPos getExitPosition()
    {
        return this.getPosition().add(moveX, 0, moveZ);
    }

    private void createExit()
    {
        BlockPos exitPos = getExitPosition();
        int radius = 5;

        for(int x = exitPos.getX() - radius; x < exitPos.getX() + radius; x++)
        {
            for(int y = exitPos.getY() - radius; y < exitPos.getY() + radius; y++)
            {
                for(int z = exitPos.getZ() - radius; z < exitPos.getZ() + radius; z++)
                {
                    float squareDistance = (x-exitPos.getX())*(x-exitPos.getX()) + (y-exitPos.getY())*(y-exitPos.getY()) + (z-exitPos.getZ())*(z-exitPos.getZ());
                    if(squareDistance <= Math.pow(radius, 2))
                    {
                        BlockPos p = new BlockPos(x, y, z);
                        if(world.getBlockState(p).getBlockHardness(world, p) > 0)
                            world.setBlockToAir(p);
                    }
                }
            }
        }

        EntityRift rift = new EntityRift(world, moveX * -1, moveZ * -1);
        rift.setPositionAndUpdate(exitPos.getX(), exitPos.getY(), exitPos.getZ());
        world.spawnEntity(rift);
        this.hasCreatedExit = true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        if(compound.getInteger("x") != 0 || compound.getInteger("z") != 0)
        {
            moveX = compound.getInteger("x");
            moveZ = compound.getInteger("z");
            hasCreatedExit = compound.getBoolean("exit");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("x", moveX);
        compound.setInteger("z", moveZ);
        compound.setBoolean("exit", hasCreatedExit);
    }

    @Override
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength();

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 256;
        return distance < d0 * d0;
    }

    public boolean canBeAttackedWithItem()
    {
        return false;
    }
    public EnumPushReaction getPushReaction()
    {
        return EnumPushReaction.IGNORE;
    }

}
