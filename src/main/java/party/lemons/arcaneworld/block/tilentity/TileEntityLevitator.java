package party.lemons.arcaneworld.block.tilentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import party.lemons.arcaneworld.block.BlockLevitator;

import java.util.List;

/**
 * Created by Sam on 24/09/2018.
 */
public class TileEntityLevitator extends TileEntity implements ITickable
{
    private static final int STEP_TIME = 1;
    private static final float SPEED = 1F;
    private static final float RANGE = 8F;

    @Override
    public void update()
    {
        if(world.getTotalWorldTime() % STEP_TIME == 0)
        {
            IBlockState state = world.getBlockState(pos);

            if(!(state.getBlock() instanceof BlockLevitator))
                return;

            EnumFacing facing = state.getValue(BlockLevitator.FACING);

            AxisAlignedBB bb = new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(pos.offset(facing)).expand(facing.getXOffset() * RANGE, facing.getYOffset() * RANGE, facing.getZOffset() * RANGE);
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, bb);

            for(int i = 0; i < entities.size(); i++)
            {
                Entity e = entities.get(i);

                int xCheck = facing.getXOffset() * (MathHelper.floor(e.posX) - this.pos.getX());
                int yCheck = facing.getYOffset() * (MathHelper.floor(e.posY) - this.pos.getY());
                int zCheck = facing.getZOffset() * (MathHelper.floor(e.posZ) - this.pos.getZ());

                for(int b = 1; b < Math.abs(xCheck + yCheck + zCheck); b++)
                {
                    if(world.isBlockFullCube(this.pos.offset(facing, b)))
                    {
                        return;
                    }
                }

                double distance = e.getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ());
                float distanceDecay =  Math.max(0, (float) ((RANGE - distance) / (RANGE * 8)));
                float speed = SPEED;
                if(facing == EnumFacing.UP || facing == EnumFacing.DOWN)
                    speed += 1;

                float velX = speed * (facing.getXOffset() * distanceDecay);
                float velY = speed * (facing.getYOffset() * distanceDecay);
                float velZ = speed * (facing.getZOffset() * distanceDecay);

                if(velX != 0)
                    e.motionX += velX;

                if(velY != 0)
                    e.motionY += velY;

                if(velZ != 0)
                    e.motionZ += velZ;

                e.fallDistance = Math.max(0, e.fallDistance - 1);
            }
        }
    }
}
