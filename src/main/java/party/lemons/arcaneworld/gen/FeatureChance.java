package party.lemons.arcaneworld.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Sam on 14/10/2018.
 */
public class FeatureChance extends WorldGenerator
{
    private final int chance;
    private final WorldGenerator feature;

    public FeatureChance(WorldGenerator feature, int chance)
    {
        this.chance = chance;
        this.feature = feature;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        if(rand.nextInt(chance) == 0)
        {
            return feature.generate(worldIn, rand, position);
        }

        return false;
    }
}
