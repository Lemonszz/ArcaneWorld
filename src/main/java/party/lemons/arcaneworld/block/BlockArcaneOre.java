package party.lemons.arcaneworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.item.IModel;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by Sam on 10/09/2018.
 */
public class BlockArcaneOre extends Block implements IModel
{
	private final Supplier<Item> drop;
	private final int maxDrop, xp;

	public BlockArcaneOre(int maxDrop, int xp, Supplier<Item> drop)
	{
		super(Material.ROCK);
		this.drop = drop;
		this.maxDrop = maxDrop;
		this.xp = xp;
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return drop.get();
	}

	public int quantityDropped(Random random)
	{
		return 1 + random.nextInt(maxDrop);
	}

	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune))
		{
			int i = random.nextInt(fortune + 2) - 1;

			if (i < 0)
			{
				i = 0;
			}

			return this.quantityDropped(random) * (i + 1);
		}
		else
		{
			return this.quantityDropped(random);
		}
	}

    @Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
		{
			return 2 + rand.nextInt(xp);
		}
		return 0;
	}

	@Override
	public ResourceLocation getModelLocation()
	{
		return getRegistryName();
	}
}
