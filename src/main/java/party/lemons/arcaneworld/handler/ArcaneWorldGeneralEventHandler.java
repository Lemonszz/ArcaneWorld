package party.lemons.arcaneworld.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.gen.dungeon.dimension.DungeonDimension;
import party.lemons.arcaneworld.gen.dungeon.dimension.TeleporterDungeonReturn;
import party.lemons.arcaneworld.util.ArcaneWorldUtil;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinateProvider;

/**
 * Created by Sam on 12/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public final class ArcaneWorldGeneralEventHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event)
    {
        if(ArcaneWorldConfig.ENTITIES.EVOKER_SPAWN.enabled && event.getEntity() instanceof EntityEvoker)
        {
            event.getDrops().removeIf(e -> e.getItem().getItem() == Items.TOTEM_OF_UNDYING && event.getEntity().world.rand.nextInt(100) < 95);
        }

        if(event.getEntity() instanceof AbstractIllager)
        {
            Entity e = event.getEntity();
            if(e.world.rand.nextInt(ArcaneWorldConfig.ENTITIES.SCROLL_CHANCE) == 0)
            {
                EntityItem item = new EntityItem(e.world, e.posX, e.posY, e.posZ, ArcaneWorldUtil.getRandomRitualScrollForDrop(e.world.rand));
                event.getDrops().add(item);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(TickEvent.PlayerTickEvent event)
    {
        if(event.player.world.provider.getDimension() == DungeonDimension.TYPE.getId())
        {
            if(event.player.posY < 10)
            {
                event.player.fallDistance = 0;
                event.player.sendStatusMessage(new TextComponentString("You experienced a bug in Arcane World, please report this on the Curse page!"), false);

                if(!event.player.world.isRemote)
                {
                    int returnDim = event.player.getCapability(RitualCoordinateProvider.RITUAL_COORDINATE_CAPABILITY,null).getDim();
                    event.player.changeDimension(returnDim, new TeleporterDungeonReturn((WorldServer) event.player.world));
                }
            }
        }
    }

}
