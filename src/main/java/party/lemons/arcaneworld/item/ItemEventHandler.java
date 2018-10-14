package party.lemons.arcaneworld.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.arcaneworld.ArcaneWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 15/10/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID)
public class ItemEventHandler
{
    @SubscribeEvent
    public static void onItemDrops(BlockEvent.HarvestDropsEvent event)
    {
        if(event.getHarvester() == null || event.getWorld().isRemote)
            return;

        ItemStack stack = event.getHarvester().getHeldItemMainhand();
        if(!event.isSilkTouching() && !stack.isEmpty() && stack.getItem() instanceof IMolten)
        {
            handleMolten(stack, event.getHarvester(), event.getPos(), event.getState(), event.getWorld(), event.getDrops());
        }
    }

    private static void handleMolten(ItemStack stack, EntityPlayer harvester, BlockPos pos, IBlockState state, World world, List<ItemStack> drops)
    {
        IMolten molten = (IMolten) stack.getItem();

        List<ItemStack> toRemove = new ArrayList<>();
        List<ItemStack> toAdd = new ArrayList<>();

        for(ItemStack drop : drops)
        {
            ItemStack result = molten.getSmeltResult(drop);
            if(!result.isEmpty())
            {
                toRemove.add(drop);

                for(int i = 0; i < drop.getCount(); i++)
                {
                    toAdd.add(result.copy());
                }
            }
        }

        drops.removeAll(toRemove);
        drops.addAll(toAdd);
    }
}
