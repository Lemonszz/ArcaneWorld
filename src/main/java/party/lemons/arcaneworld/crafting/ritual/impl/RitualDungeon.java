package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.gen.dungeon.dimension.TeleporterDungeon;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinateProvider;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualDungeon extends Ritual
{
    public RitualDungeon(Ingredient... ingredients) {
        super(ingredients);
    }

    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items)
    {
        WorldServer ws = (WorldServer) world;


        TeleporterDungeon teleporter = new TeleporterDungeon(ws);
        List<EntityLivingBase> players = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(5, 5, 5));


        for (EntityLivingBase p:players)
        {
            p.getCapability(RitualCoordinateProvider.RITUAL_COORDINATE_CAPABILITY, null).setPos(pos);
            p.getCapability(RitualCoordinateProvider.RITUAL_COORDINATE_CAPABILITY, null).setDim(world.provider.getDimension());

            p.changeDimension(ArcaneWorldConfig.DUNGEONS.DIM_ID, teleporter);


        }
    }
}
