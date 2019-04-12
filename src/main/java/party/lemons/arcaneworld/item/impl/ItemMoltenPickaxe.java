package party.lemons.arcaneworld.item.impl;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.item.IMolten;
import party.lemons.lemonlib.item.IItemModel;

/**
 * Created by Sam on 15/10/2018.
 */
public class ItemMoltenPickaxe extends ItemPickaxe implements IMolten, IItemModel
{
    public ItemMoltenPickaxe(ToolMaterial material)
    {
        super(material);
    }
}
