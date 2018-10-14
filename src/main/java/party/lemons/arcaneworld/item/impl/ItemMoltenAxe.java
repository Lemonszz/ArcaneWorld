package party.lemons.arcaneworld.item.impl;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.item.IModel;
import party.lemons.arcaneworld.item.IMolten;

/**
 * Created by Sam on 15/10/2018.
 */
public class ItemMoltenAxe extends ItemAxe implements IMolten, IModel
{
    public ItemMoltenAxe(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return getRegistryName();
    }
}
