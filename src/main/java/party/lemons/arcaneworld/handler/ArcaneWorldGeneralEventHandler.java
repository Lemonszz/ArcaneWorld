package party.lemons.arcaneworld.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.config.ArcaneWorldConfig;
import party.lemons.arcaneworld.util.ArcaneWorldUtil;

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
}
