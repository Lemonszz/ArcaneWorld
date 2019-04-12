package party.lemons.arcaneworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import party.lemons.lemonlib.item.IItemModel;

/**
 * Created by Sam on 10/09/2018.
 */
public class BlockModel extends Block implements IItemModel
{
	public BlockModel(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
	}

	public BlockModel(Material materialIn)
	{
		super(materialIn);
	}
}
