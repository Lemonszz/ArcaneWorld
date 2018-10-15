package party.lemons.arcaneworld.gen.dungeon.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import party.lemons.arcaneworld.gen.dungeon.generation.DungeonGenerator;

/**
 * Created by Sam on 22/09/2018.
 */
public class TeleporterDungeon extends Teleporter
{
    private boolean hasGenerated = false;
    private BlockPos offsetPos;

    public TeleporterDungeon(WorldServer worldIn)
    {
        super(worldIn);
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
        if(world.isRemote)
            return;

        if(!hasGenerated)
        {

            DungeonSavedData data = DungeonSavedData.getInstance(world);
            int offset = data.getDungeonCount() * 250;
            data.addDungeon();

            offsetPos = new BlockPos(offset, 40, 0);
            world.setBlockState(new BlockPos(offsetPos.getX() + (DungeonGenerator.ROOM_WIDTH / 2), offsetPos.up(3).getY(), offsetPos.getZ() + (DungeonGenerator.ROOM_WIDTH / 2)), Blocks.BEDROCK.getDefaultState());

            DungeonGenerator generator = new DungeonGenerator(world, offsetPos);
            generator.generateRoom(0, 0);
            generator.generate();

            hasGenerated = true;
        }

        entity.setLocationAndAngles(offsetPos.getX() + (DungeonGenerator.ROOM_WIDTH / 2), offsetPos.up(3).getY(), offsetPos.getZ() + (DungeonGenerator.ROOM_WIDTH / 2), entity.rotationYaw, 0.0F);
        entity.motionX = 0.0D;
        entity.motionY = 0.0D;
        entity.motionZ = 0.0D;
    }

    public void placeInPortal(Entity entity, float rotationYaw)
    {

    }
}
