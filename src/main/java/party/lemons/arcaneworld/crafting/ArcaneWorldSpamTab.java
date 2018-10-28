package party.lemons.arcaneworld.crafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.item.ArcaneWorldItems;

/**
 * Created by Sam on 9/09/2018.
 */
public class ArcaneWorldSpamTab extends CreativeTabs
{
	public static final ArcaneWorldSpamTab INSTANCE = new ArcaneWorldSpamTab();

	public ArcaneWorldSpamTab()
	{
		super(ArcaneWorld.MODID);
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(ArcaneWorldItems.RITUAL_SCROLL);
	}
}
