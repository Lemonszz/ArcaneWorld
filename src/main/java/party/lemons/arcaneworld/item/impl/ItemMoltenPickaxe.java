package party.lemons.arcaneworld.item.impl;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;
import party.lemons.arcaneworld.item.IModel;
import party.lemons.arcaneworld.item.IMolten;

/**
 * Created by Sam on 15/10/2018.
 */
public class ItemMoltenPickaxe extends ItemPickaxe implements IMolten, IModel
{
    public ItemMoltenPickaxe(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public ResourceLocation getModelLocation()
    {
        return getRegistryName();
    }
}
