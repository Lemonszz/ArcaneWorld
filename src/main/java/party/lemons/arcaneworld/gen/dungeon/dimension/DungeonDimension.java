package party.lemons.arcaneworld.gen.dungeon.dimension;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;

/**
 * Created by Sam on 22/09/2018.
 */
public class DungeonDimension
{
    public static DimensionType TYPE = DimensionType.register("dungeon", "dungeon", ArcaneWorldConfig.ConfigDungeonDimension.DIM_ID, DungeonDimensionProvider.class, false);

    public static void init()
    {
        DimensionManager.registerDimension(ArcaneWorldConfig.ConfigDungeonDimension.DIM_ID, TYPE);
    }

    private static void cancelIfInDim(World world, Event event)
    {
        if(isInDim(world))
            event.setCanceled(true);
    }

    private static void denyIfInDim(World world, Event event)
    {
        if(isInDim(world))
            event.setResult(Event.Result.DENY);
    }

    private static boolean isInDim(World world)
    {
        return world.provider.getDimension() == ArcaneWorldConfig.ConfigDungeonDimension.DIM_ID;
    }


    @Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
    public static class DungeonDimensionEvents
    {
        @SubscribeEvent
        public static void onPlaceBlock(BlockEvent.PlaceEvent event)
        {
            cancelIfInDim(event.getWorld(), event);
        }

        @SubscribeEvent
        public static void onBreakBlock(BlockEvent.BreakEvent event)
        {
            cancelIfInDim(event.getWorld(), event);
        }

        @SubscribeEvent
        public static void onExplode(ExplosionEvent.Detonate event)
        {
            if(isInDim(event.getWorld()))
                event.getAffectedBlocks().clear();
        }

        @SubscribeEvent
        public static void onMobGriefing(EntityMobGriefingEvent event)
        {
                denyIfInDim(event.getEntity().world, event);
        }

        @SubscribeEvent
        public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
        {
            cancelIfInDim(event.getEntityPlayer().world, event);
        }

        @SubscribeEvent
        public static void onUseItem(PlayerInteractEvent.RightClickItem event)
        {
            if(isInDim(event.getWorld()))
            {
                ItemStack stack = event.getItemStack();
                if(stack.getItem() instanceof ItemBlock)
                    event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onClickBlock(PlayerInteractEvent.LeftClickBlock event)
        {
            cancelIfInDim(event.getWorld(), event);
        }
    }

    @Mod.EventBusSubscriber(modid = ArcaneWorld.MODID, value = Side.CLIENT)
    public static class DungeonDimensionEventsClient
    {
        @SubscribeEvent
        public static void onDrawBlockHighlight(DrawBlockHighlightEvent event)
        {
            if(!isInDim(event.getPlayer().world))
                return;

            event.setCanceled(true);
            if(event.getTarget().getBlockPos() != null)
            {
                World world = event.getPlayer().world;
                BlockPos pos = event.getTarget().getBlockPos();
                IBlockState state = world.getBlockState(pos);

                if(state.getBlock().hasTileEntity(state) || (world.getTileEntity(pos) instanceof IInventory))
                    event.setCanceled(false);
            }
        }
    }
}
