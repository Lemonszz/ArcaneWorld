package party.lemons.arcaneworld.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.util.ArcaneWorldUtil;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Sam on 10/09/2018.
 */
public class BlockRitualTable extends BlockModel
{
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

	public BlockRitualTable()
	{
		super(Material.IRON);
		this.setLightOpacity(0);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityRitualTable)
			{
                if(playerIn instanceof FakePlayer)
                {
                    return doFakePlayerInteraction((FakePlayer) playerIn, (TileEntityRitualTable) tileentity, pos, worldIn);
                }
                else
                {
                    playerIn.openGui(ArcaneWorld.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
			}
		}
		return true;
	}

    private boolean doFakePlayerInteraction(FakePlayer player, TileEntityRitualTable tile, BlockPos pos, World world)
    {
        if(tile.canCast())
        {
            tile.attemptActivateRitual(player);
            return true;
        }
        return false;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityRitualTable)
		{
			ArcaneWorldUtil.dropInventoryItems(worldIn, pos, ((TileEntityRitualTable) tileentity).getInventory());
		}

		super.breakBlock(worldIn, pos, state);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){ return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		super.randomDisplayTick(stateIn, worldIn, pos, rand);

		for (int i = -2; i <= 2; ++i)
		{
			for (int j = -2; j <= 2; ++j)
			{
				if (i > -2 && i < 2 && j == -1)
				{
					j = 2;
				}

				if (rand.nextInt(16) == 0)
				{
					for (int k = 0; k <= 1; ++k)
					{
						BlockPos blockpos = pos.add(i, k, j);

						if (net.minecraftforge.common.ForgeHooks.getEnchantPower(worldIn, blockpos) > 0)
						{
							if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2)))
							{
								break;
							}

							worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.0D, (double)pos.getZ() + 0.5D, (double)((float)i + rand.nextFloat()) - 0.5D, (double)((float)k - rand.nextFloat() - 1.0F), (double)((float)j + rand.nextFloat()) - 0.5D);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityRitualTable();
	}
}
