package party.lemons.arcaneworld.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import party.lemons.arcaneworld.entity.EntityRift;

import java.util.Random;

/**
 * Created by Sam on 14/10/2018.
 */
public class FeatureRift extends WorldGenerator
{
    final int MIN_Y = 5;

    @Override
    public boolean generate(World world, Random rand, BlockPos position)
    {
        EntityRift rift = new EntityRift(world);
        float yPos = 0.5F + MIN_Y + rand.nextInt(world.getHeight() - MIN_Y);

        if(yPos > 120)  //reroll if it's a high position, because having lower expectations is better
            yPos = 0.5F + MIN_Y + rand.nextInt(world.getHeight() - MIN_Y);

        rift.setPosition(position.getX() + 0.5F, yPos, position.getZ() + 0.5F);
        world.spawnEntity(rift);

        System.out.println(rift.getPosition());
        return true;
    }
}
