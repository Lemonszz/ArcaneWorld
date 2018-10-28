package party.lemons.arcaneworld.handler;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.arcaneworld.ArcaneWorld;

/**
 * Created by Sam on 11/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class ArcaneWorldSounds
{
    public static final SoundEvent RITUAL_OUT = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RITUAL_ITEM = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RITUAL_CHARGE = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RITUAL_START = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RITUAL_ITEM_POP = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent GENERAL_WOOSH = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent GENERAL_BREAK = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent GENERAL_BREAK_SQUISH = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent MUSIC_DUNGEON = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RIFT_START = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RIFT_END = SoundEvents.UI_TOAST_OUT;
    public static final SoundEvent RIFT_AMBIENT = SoundEvents.UI_TOAST_OUT;


    @SubscribeEvent
    public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event)
    {
        registerSound(event.getRegistry(), "ritual_out");
        registerSound(event.getRegistry(), "ritual_item");
        registerSound(event.getRegistry(), "ritual_charge");
        registerSound(event.getRegistry(), "ritual_start");
        registerSound(event.getRegistry(), "ritual_item_pop");
        registerSound(event.getRegistry(), "general_woosh");
        registerSound(event.getRegistry(), "general_break");
        registerSound(event.getRegistry(), "general_break_squish");
        registerSound(event.getRegistry(), "music_dungeon");
        registerSound(event.getRegistry(), "rift_start");
        registerSound(event.getRegistry(), "rift_end");
        registerSound(event.getRegistry(), "rift_ambient");

    }

    public static void registerSound(IForgeRegistry<SoundEvent> r, String name)
    {
        ResourceLocation loc = new ResourceLocation(ArcaneWorld.MODID, name);
        r.register(new SoundEvent(loc).setRegistryName(loc));
    }
}
