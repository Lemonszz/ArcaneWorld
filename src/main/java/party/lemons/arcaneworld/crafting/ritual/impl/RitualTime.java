package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.handler.ticker.TickerHandler;
import party.lemons.arcaneworld.handler.ticker.impl.TickerTime;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 10/09/2018.
 */
public class RitualTime extends Ritual
{
	private final int targetTime;

	public RitualTime(int targetTime, Ingredient... ingredients)
	{
		super(ingredients);

		this.targetTime = targetTime;
	}

	public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items)
	{
		TickerHandler.addTicker(new TickerTime(targetTime, world), world.provider.getDimension());
	}
}
