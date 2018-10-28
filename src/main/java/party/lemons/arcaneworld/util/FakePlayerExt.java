package party.lemons.arcaneworld.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

/**
 * Created by Sam on 25/10/2018.
 */
public class FakePlayerExt extends FakePlayer
{
    private static final GameProfile MINECRAFT = new GameProfile(UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77"), "[Minecraft]");
    private static FakePlayerExt player = null;

    public static FakePlayerExt get(WorldServer worldServer)
    {
        FakePlayerExt pl = player != null ? player : new FakePlayerExt(worldServer, MINECRAFT);
        player = pl;

        return pl;
    }

    public void displayGUIChest(IInventory chestInventory){}
    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock){}

    public FakePlayerExt(WorldServer world, GameProfile name)
    {
        super(world, name);
    }

    @Override
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) { }

    @Override
    public float getCooledAttackStrength(float adjustTicks)
    {
        return 1;
    }

    @Override
    public float getEyeHeight()
    {
        return 0;
    }

}
