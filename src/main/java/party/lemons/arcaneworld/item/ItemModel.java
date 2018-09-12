package party.lemons.arcaneworld.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Sam on 2/09/2018.
 */
public class ItemModel extends Item implements IModel
{
	@Override
	public ResourceLocation getModelLocation()
	{
		return this.getRegistryName();
	}
}
