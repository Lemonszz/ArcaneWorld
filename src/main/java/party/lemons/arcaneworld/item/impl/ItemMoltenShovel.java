package party.lemons.arcaneworld.item.impl;

import net.minecraft.item.ItemSpade;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.item.IMolten;
import party.lemons.lemonlib.item.IItemModel;

/**
 * Created by Sam on 15/10/2018.
 */
public class ItemMoltenShovel extends ItemSpade implements IMolten, IItemModel
{
    public ItemMoltenShovel(ToolMaterial material)
    {
        super(material);
    }
}
