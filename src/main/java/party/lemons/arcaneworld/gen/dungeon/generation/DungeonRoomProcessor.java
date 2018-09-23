package party.lemons.arcaneworld.gen.dungeon.generation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.init.Biomes.PLAINS;

/**
 * Created by Sam on 22/09/2018.
 */
public class DungeonRoomProcessor implements ITemplateProcessor
{
    @Nullable
    @Override
    public Template.BlockInfo processBlock(World world, BlockPos pos, Template.BlockInfo blockInfo)
    {
        if(blockInfo.blockState.getBlock() == Blocks.STRUCTURE_BLOCK)
        {
            switch (blockInfo.tileentityData.getString("metadata"))
            {
                case "random_entity":
                    List<Biome.SpawnListEntry> spawns =  Biomes.PLAINS.getSpawnableList(EnumCreatureType.MONSTER);
                    Biome.SpawnListEntry entry = spawns.get(world.rand.nextInt(spawns.size()));

                    EntityLiving entity = null;
                    try
                    {
                        entity = entry.newInstance(world);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(entity != null)
                    {
                        entity.setPosition(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
                        entity.enablePersistence();
                        entity.onInitialSpawn(world.getDifficultyForLocation(pos), null);

                        world.spawnEntity(entity);
                    }
                    break;
                case "loot_random":
                    TileEntity tileentity = world.getTileEntity(pos.down());
                    if (tileentity instanceof TileEntityChest)
                    {
                        ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, world.rand.nextLong());
                    }
                    break;
            }
        }
        return blockInfo;
    }
}
