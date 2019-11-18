package party.lemons.arcaneworld.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import party.lemons.arcaneworld.util.capabilities.IRitualCoordinate;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinate;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinateHandler;
import party.lemons.arcaneworld.util.capabilities.RitualCoordinateStorage;

/**
 * Created by Sam on 30/08/2018.
 */
public class ServerProxy implements IProxy
{
    @Override
    public void capabilityInit() {
        CapabilityManager.INSTANCE.register(IRitualCoordinate.class, new RitualCoordinateStorage(), RitualCoordinate.class);
    }
}
