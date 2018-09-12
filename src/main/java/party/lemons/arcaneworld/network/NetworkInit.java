package party.lemons.arcaneworld.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.arcaneworld.ArcaneWorld;

/**
 * Created by Sam on 5/05/2018.
 */
public class NetworkInit
{
	public static void init()
	{
		registerMessage(MessageEventArcaneHoeChange.Handler.class, MessageEventArcaneHoeChange.class, Side.CLIENT);
		registerMessage(MessageRitualCreateUpParticle.Handler.class, MessageRitualCreateUpParticle.class, Side.CLIENT);
		registerMessage(MessageRitualClientActivate.Handler.class, MessageRitualClientActivate.class, Side.SERVER);
		registerMessage(MessageServerActivateRitual.Handler.class, MessageServerActivateRitual.class, Side.CLIENT);
	}

	public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
	{
		ArcaneWorld.NETWORK.registerMessage(messageHandler, requestMessageType, ind++, side);
	}
	static int ind = 1;
}
