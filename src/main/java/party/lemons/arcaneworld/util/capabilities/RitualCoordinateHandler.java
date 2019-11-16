package party.lemons.arcaneworld.util.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.arcaneworld.ArcaneWorld;


@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public class RitualCoordinateHandler
{
    public static final ResourceLocation RITUAL_CAP = new ResourceLocation(ArcaneWorld.MODID, "RitualLocation");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(RITUAL_CAP, new RitualCoordinateProvider());
        }
    }

}
