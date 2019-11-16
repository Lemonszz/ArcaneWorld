package party.lemons.arcaneworld.util.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;




public class RitualCoordinateStorage implements IStorage<IRitualCoordinate>
{
    @Override
    public NBTBase writeNBT(Capability<IRitualCoordinate> capability, IRitualCoordinate instance, EnumFacing side)
    {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setInteger("RitualDim", instance.getDim());
        tag.setInteger("RitualX", instance.getPos().getX());
        tag.setInteger("RitualY", instance.getPos().getY());
        tag.setInteger("RitualZ", instance.getPos().getZ());
        return tag;
    }

    @Override
    public void readNBT(Capability<IRitualCoordinate> capability, IRitualCoordinate instance, EnumFacing side, NBTBase nbt)
    {
        instance.setDim(((NBTTagCompound) nbt).getInteger("RitualDim"));
        instance.setPos(new BlockPos(((NBTTagCompound) nbt).getInteger("RitualX"),((NBTTagCompound) nbt).getInteger("RitualY"),((NBTTagCompound) nbt).getInteger("RitualZ")));
    }
}
