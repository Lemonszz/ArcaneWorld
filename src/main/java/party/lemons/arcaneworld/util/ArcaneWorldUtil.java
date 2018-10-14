package party.lemons.arcaneworld.util;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.item.impl.ItemRitualScroll;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Sam on 15/09/2018.
 */
public class ArcaneWorldUtil
{
    public static ItemStack getRandomRitualScrollForDrop(Random random)
    {
        Collection<Ritual> list = RitualRegistry.REGISTRY.getValuesCollection().stream().filter(Ritual::canDrop).collect(Collectors.toList());
        Ritual ritual = list.stream().skip(random.nextInt(list.size()-1)).findFirst().get();
        while (ritual.isEmpty())
        {
            ritual = list.stream().skip(random.nextInt(list.size()-1)).findFirst().get();
        }

        ItemStack stack = new ItemStack(ArcaneWorldItems.RITUAL_SCROLL);
        ItemRitualScroll.setRitual(stack, ritual);

        return stack;
    }

    public static void dropInventoryItems(World worldIn, BlockPos pos, IItemHandler inventory)
    {
        dropInventoryItems(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), inventory);
    }

    public static void dropInventoryItems(World worldIn, Entity entityAt, IItemHandler inventory)
    {
        dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
    }

    private static void dropInventoryItems(World worldIn, double x, double y, double z, IItemHandler inventory)
    {
        for (int i = 0; i < inventory.getSlots(); ++i)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                InventoryHelper.spawnItemStack(worldIn, x, y, z, itemstack);
            }
        }
    }

    public static boolean ingredientMatch(Ingredient ingredient, ItemStack stack)
    {
        if (stack == null)
        {
            return false;
        }
        else
        {
            for (ItemStack itemstack : ingredient.getMatchingStacks())
            {
                if (itemstack.getItem() == stack.getItem())
                {
                    if(itemstack.getCount() <= stack.getCount())
                    {
                        int i = itemstack.getMetadata();
                        if (i == Short.MAX_VALUE || i == stack.getMetadata())
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    public static void copyFile(String inputPath, String outputPath ) throws IOException
    {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try
        {

            inputStream = ArcaneWorld.class.getResourceAsStream(inputPath);
            outputStream = new FileOutputStream(outputPath);

            byte[] buf = new byte[1024];

            int bytesRead;

            while ((bytesRead = inputStream.read(buf)) > 0)
            {

                outputStream.write(buf, 0, bytesRead);

            }

        } finally
        {
            inputStream.close();
            outputStream.close();

        }
    }

    public static int getEntityPortalTime(Entity entity)
    {
        if(portalTimeField == null)
            getPortalTimeField();

        try
        {
            return portalTimeField.getInt(entity);

        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return entity.getMaxInPortalTime();
    }

    public static void setEntityPortalTime(Entity entity, int value)
    {
        if(portalTimeField == null)
            getPortalTimeField();

        try
        {
            portalTimeField.setInt(entity, value);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private static void getPortalTimeField()
    {
        portalTimeField = ReflectionHelper.findField(Entity.class, "portalCounter", "field_82153_h");
        portalTimeField.setAccessible(true);
    }



    private static Field portalTimeField = null;
}
