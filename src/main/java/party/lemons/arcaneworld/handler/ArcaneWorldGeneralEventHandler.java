package party.lemons.arcaneworld.handler;

import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.arcaneworld.ArcaneWorld;

/**
 * Created by Sam on 12/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public final class ArcaneWorldGeneralEventHandler {

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event)
    {
        if(event.getEntity() instanceof EntityEvoker)
        {
            event.getDrops().removeIf(e -> e.getItem().getItem() == Items.TOTEM_OF_UNDYING && event.getEntity().world.rand.nextInt(100) < 95);
        }
    }
}
