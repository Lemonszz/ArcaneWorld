package party.lemons.arcaneworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.gen.dungeon.dimension.TeleporterDungeonReturn;
import party.lemons.arcaneworld.item.IModel;
import party.lemons.arcaneworld.util.ArcaneWorldUtil;

import java.util.Random;

/**
 * Created by Sam on 22/09/2018.
 */
public class BlockReturnPortal extends BlockPortal implements IModel
{
    public BlockReturnPortal()
    {
        super();
    }
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            worldIn.setBlockState(pos, state.withProperty(AXIS, EnumFacing.Axis.Z));
        }

        return true;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {

    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    }

    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
        if(worldIn.provider.getDimension() != ArcaneWorldConfig.ConfigDungeonDimension.DIM_ID)
            return;

        if (!entity.isRiding() && !entity.isBeingRidden() && entity.isNonBoss())
        {
            entity.setPortal(pos);
        }

        if (!worldIn.isRemote && !entity.isRiding())
        {
            int i =  entity.getMaxInPortalTime() - 1;
            ArcaneWorldUtil.setEntityPortalTime(entity, ArcaneWorldUtil.getEntityPortalTime(entity) + 1);

            if ((entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative()) || ArcaneWorldUtil.getEntityPortalTime(entity) >= i)
            {
                entity.timeUntilPortal = entity.getPortalCooldown();
                entity.changeDimension(0, new TeleporterDungeonReturn((WorldServer) worldIn));
            }
        }
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return getRegistryName();
    }
}
