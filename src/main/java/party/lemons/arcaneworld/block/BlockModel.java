package party.lemons.arcaneworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.item.IModel;

/**
 * Created by Sam on 10/09/2018.
 */
public class BlockModel extends Block implements IModel
{
	public BlockModel(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
	}

	public BlockModel(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public ResourceLocation getModelLocation()
	{
		return getRegistryName();
	}
}
