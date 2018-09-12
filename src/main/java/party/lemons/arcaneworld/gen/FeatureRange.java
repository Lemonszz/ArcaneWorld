package party.lemons.arcaneworld.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Sam on 13/09/2018.
 * Stole from pau101
 * https://github.com/pau101/Wings/blob/master/src/main/java/me/paulf/wings/server/world/feature/FeatureRange.java
 */
public final class FeatureRange extends WorldGenerator {
    private final WorldGenerator feature;

    private final int count;

    private final int minHeight;

    private final int maxHeight;

    public FeatureRange(WorldGenerator feature, int count, int minHeight, int maxHeight) {
        this.feature = feature;
        this.count = count;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean generate(World world, Random rng, BlockPos pos) {
        boolean result = false;
        for (int n = count; n --> 0; ) {
            result |= feature.generate(world, rng, pos.add(
                    rng.nextInt(16),
                    rng.nextInt(maxHeight - minHeight) + minHeight,
                    rng.nextInt(16)
            ));
        }
        return result;
    }
}