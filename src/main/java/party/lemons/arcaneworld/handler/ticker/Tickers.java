package party.lemons.arcaneworld.handler.ticker;

import party.lemons.arcaneworld.handler.ticker.impl.TickerHoe;

/**
 * Created by Sam on 6/05/2018.
 */
public class Tickers
{
	public static void init()
	{
		TickerHandler.registerTicker(TickerHoe.class);
	}
}
