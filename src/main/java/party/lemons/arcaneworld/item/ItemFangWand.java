package party.lemons.arcaneworld.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Created by Sam on 9/09/2018.
 */
public class ItemFangWand extends ItemModel
{
	public ItemFangWand()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(520);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote)
			return EnumActionResult.SUCCESS;

		float f = (float) MathHelper.atan2((pos.getZ() + hitZ) - player.posZ, (pos.getX() + hitX) - player.posX);
		double pY = pos.up().getY();

		for (int l = -1; l < 15; ++l)
		{
			double time_offset = 1.25D * (double)(l + 1);
			double pX = (pos.getX() + hitX) + (double) MathHelper.cos(f) * time_offset;
			double pZ = (pos.getZ() + hitZ)+ (double)MathHelper.sin(f) * time_offset;

			BlockPos p = new BlockPos(pX, pY, pZ);
			IBlockState state = worldIn.getBlockState(p);
			if(state.getBlock().isFullBlock(state))
			{
				if(worldIn.isAirBlock(p.up()))
				{
					pY++;
				}
				else
					break;
			}

			if(worldIn.isAirBlock(p.down()))
			{
				if(worldIn.isAirBlock(p.down(2)))
					break;

				pY--;
			}

			EntityEvokerFangs fangs = new EntityEvokerFangs(worldIn, pX, pY, pZ, player.rotationYaw, (int)time_offset, player);
			worldIn.spawnEntity(fangs);
		}
		player.getHeldItem(hand).damageItem(1, player);
		player.getCooldownTracker().setCooldown(this, 30);
		return EnumActionResult.SUCCESS;
	}
}
