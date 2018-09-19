package party.lemons.arcaneworld.crafting.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.item.ItemRitualScroll;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 16/09/2018.
 */
public class RitualScroll extends Ritual
{
    public RitualScroll(Ingredient... ingredients)
    {
        super(ingredients);
    }

    @Override
    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items)
    {
        ItemStack stack = items[0];
        Ritual ritual = ItemRitualScroll.getRitual(stack);

        if(ritual == null || ritual instanceof RitualScroll)
        {
            //TODO: some cool effect?
            ///Thonk
        }
        else
        {
            ritual.onActivate(world, pos, player, items);
        }
    }
}
