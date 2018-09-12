package party.lemons.arcaneworld.handler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import party.lemons.arcaneworld.block.ArcaneWorldBlocks;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.util.Pair;

/**
 * Created by Sam on 10/09/2018.
 */
public class OreDictHandler
{
	public static void init()
	{
		for(Pair<Item, String[]> pair : ArcaneWorldItems.getOreDictEntries())
		{
			Item item = pair.getFirst();
			String[] entries = pair.getSecond();

			for(String entry : entries)
			{
				OreDictionary.registerOre(entry, item);
			}
		}

		for(Pair<Block, String[]> pair : ArcaneWorldBlocks.getOreDictEntries())
		{
			Block item = pair.getFirst();
			String[] entries = pair.getSecond();

			for(String entry : entries)
			{
				OreDictionary.registerOre(entry, item);
			}
		}
	}
}
