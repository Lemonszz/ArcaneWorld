package party.lemons.arcaneworld.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.handler.ticker.TickerHandler;
import party.lemons.arcaneworld.handler.ticker.impl.TickerHoe;

/**
 * Created by Sam on 9/09/2018.
 */
public class ItemArcaneHoe extends ItemHoe implements IModel
{
	public ItemArcaneHoe()
	{
		super(ToolMaterial.GOLD);
		this.setMaxDamage(500);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
		{
			return EnumActionResult.FAIL;
		}

		if(!worldIn.isRemote)
			TickerHandler.addTicker(new TickerHoe(worldIn, 10, player.getHorizontalFacing(), pos), worldIn.provider.getDimension());

		itemstack.damageItem(1, player);
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ResourceLocation getModelLocation()
	{
		return getRegistryName();
	}
}
