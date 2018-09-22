package party.lemons.arcaneworld.gen.dungeon.dimension;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import party.lemons.arcaneworld.ArcaneWorld;

/**
 * Created by Sam on 22/09/2018.
 */
public class DungeonSavedData extends WorldSavedData
{
    public static final String _NAME = ArcaneWorld.MODID + ":dungeondata";
    public static int dungeonCount = 0;

    public DungeonSavedData(String name)
    {
        super(name);
    }

    public DungeonSavedData()
    {
        super(_NAME);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        dungeonCount = nbt.getInteger("count");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("count", dungeonCount);
        return compound;
    }

    public void addDungeon()
    {
        dungeonCount++;
        this.markDirty();
    }

    public static DungeonSavedData getInstance(World world)
    {
        DungeonSavedData  inst = (DungeonSavedData)world.getPerWorldStorage().getOrLoadData(DungeonSavedData.class, _NAME);

        if(inst == null)
        {
            inst = new DungeonSavedData();
            world.getPerWorldStorage().setData(_NAME, inst);
        }

        return inst;
    }

    public int getDungeonCount()
    {
        return dungeonCount;
    }
}
