package party.lemons.arcaneworld.crafting.ritual;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ritual.impl.*;

/**
 * Created by Sam on 10/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
@GameRegistry.ObjectHolder(ArcaneWorld.MODID)
public class RitualRegistry
{
	public static IForgeRegistry<Ritual> REGISTRY;
	public static final Ritual EMPTY = null;

	@SubscribeEvent
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = new RegistryBuilder<Ritual>()
				.setType(Ritual.class)
				.setDefaultKey(new ResourceLocation(ArcaneWorld.MODID, "empty"))
				.setName(new ResourceLocation(ArcaneWorld.MODID, "rituals")).allowModification()
				.create();
	}
}
