package party.lemons.arcaneworld.item.impl;

import net.minecraft.item.ItemAxe;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.item.IMolten;
import party.lemons.lemonlib.item.IItemModel;

/**
 * Created by Sam on 15/10/2018.
 */
public class ItemMoltenAxe extends ItemAxe implements IMolten, IItemModel
{
    public ItemMoltenAxe(ToolMaterial material)
    {
        super(material);
    }
}
