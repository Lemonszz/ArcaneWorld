package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.gen.dungeon.ArenaGenerator;
import party.lemons.arcaneworld.gen.dungeon.dimension.TeleporterDungeon;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualArena extends Ritual
{
    private final Class<? extends Entity> entityClass;

    public RitualArena(Class<? extends Entity> entityClass, Ingredient... ingredients) {
        super(ingredients);
        this.entityClass = entityClass;
    }

    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items)
    {
        WorldServer ws = (WorldServer) world;

        TeleporterDungeon teleporter = new TeleporterArena(ws, entityClass);
        List<EntityLivingBase> players = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(5, 5, 5));

        players.forEach(p -> p.changeDimension(ArcaneWorldConfig.DUNGEONS.DIM_ID, teleporter));
    }

    public static class TeleporterArena extends TeleporterDungeon
    {
        private final Class<? extends Entity> entityClass;

        public TeleporterArena(WorldServer worldIn, Class<? extends Entity> entityClass)
        {
            super(worldIn);
            this.entityClass = entityClass;
        }

        @Override
        public void doGeneration(World world)
        {
            hasGenerated = true;
            ArenaGenerator.generate(world, offsetPos);

            Entity ent = null;
            try
            {
                ent = entityClass.getConstructor(World.class).newInstance(world);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if(ent != null)
            {
                ent.addTag("arena_entity");

                ent.setPosition(offsetPos.getX() + 16, offsetPos.up(13).getY(), offsetPos.getZ() + 16);
                world.spawnEntity(ent);

                if(ent instanceof EntityWither)
                {
                    ((EntityWither)ent).ignite();
                }
            }

        }

        @Override
        public double getTeleportOffsetX()
        {
            return 16.5D;
        }

        @Override
        public double getTeleportOffsetZ()
        {
            return 5;
        }
    }
}
