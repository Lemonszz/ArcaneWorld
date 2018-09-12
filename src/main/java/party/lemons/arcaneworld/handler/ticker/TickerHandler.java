package party.lemons.arcaneworld.handler.ticker;

import net.minecraftforge.fml.common.Mod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.arcaneworld.ArcaneWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam on 6/05/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public class TickerHandler
{
	private static HashMap<Integer, List<ITicker>> worldTickers = new HashMap<>();
	private static List<Class<ITicker>> registeredTickers = new ArrayList<>();

	public static void addTicker(ITicker ticker, int dim)
	{
		if(!worldTickers.containsKey(dim))
			worldTickers.put(dim, new ArrayList<>());

		worldTickers.get(dim).add(ticker);
	}

	private static void removeFinishedTickers(World world)
	{
		for(List<ITicker> tickerList : worldTickers.values())
		{
			tickerList.removeIf(t -> t.isTaskFinished(world));
		}
	}

	private static void tick(World world)
	{
		if(!worldTickers.containsKey(world.provider.getDimension()))
			return;

		List<ITicker> tickerList = worldTickers.get(world.provider.getDimension());
		tickerList.stream().forEach(t -> t.update(world));
	}

	public static <T extends ITicker> void registerTicker(Class<T> clazz)
	{
		registeredTickers.add((Class<ITicker>) clazz);
	}

	public static NBTTagList writeToNBT()
	{
		NBTTagList list = new NBTTagList();
		for(List<ITicker> tickerList : worldTickers.values())
		{
			for(ITicker ticker : tickerList)
			{
				NBTTagCompound tickerTags = ticker.writeToNBT();
				tickerTags.setInteger("key", registeredTickers.indexOf(ticker.getClass()));
				tickerTags.setInteger("dim", ticker.getDimension());

				list.appendTag(tickerTags);
			}
		}

		return list;
	}

	public static void readFromNBT(NBTTagList tickerList)
	{
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
		for(int i = 0; i < tickerList.tagCount(); i++)
		{
			NBTTagCompound tag = tickerList.getCompoundTagAt(i);
			Class<ITicker> clazz = registeredTickers.get(tag.getInteger("key"));
			int dimID = tag.getInteger("dim");
			try
			{
				ITicker ticker = clazz.getConstructor(World.class).newInstance(world);
				ticker.readFromNBT(tag);
				addTicker(ticker, dimID);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
			return;

		tick(event.world);
		removeFinishedTickers(event.world);
	}

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event)
	{
		TickerSavedData.get(event.getWorld());
	}
}