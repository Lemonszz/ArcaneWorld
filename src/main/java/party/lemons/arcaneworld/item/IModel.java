package party.lemons.arcaneworld.item;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Sam on 30/08/2018.
 */
public interface IModel
{
	default boolean hasModel(){
		return true;
	}

	ResourceLocation getModelLocation();
}
