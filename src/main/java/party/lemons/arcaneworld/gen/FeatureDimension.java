package party.lemons.arcaneworld.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Sam on 14/10/2018.
 */
public class FeatureDimension extends WorldGenerator
{
    private final int[] dims;
    private final WorldGenerator feature;

    public FeatureDimension(WorldGenerator feature, int... dims)
    {
        this.dims = dims;
        this.feature = feature;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for(int  i = 0; i < dims.length; i++)
        {
            if(dims[i] == worldIn.provider.getDimension())
                return feature.generate(worldIn, rand, position);
        }
        return false;
    }
}
