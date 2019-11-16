package party.lemons.arcaneworld.util.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RitualCoordinateProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IRitualCoordinate.class)
    public static final Capability<IRitualCoordinate> RITUAL_COORDINATE_CAPABILITY = null;
    private IRitualCoordinate instance = RITUAL_COORDINATE_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing)
    {
        return capability == RITUAL_COORDINATE_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing)
    {
        return capability == RITUAL_COORDINATE_CAPABILITY ? RITUAL_COORDINATE_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return RITUAL_COORDINATE_CAPABILITY.getStorage().writeNBT(RITUAL_COORDINATE_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbtBase)
    {
        RITUAL_COORDINATE_CAPABILITY.getStorage().readNBT(RITUAL_COORDINATE_CAPABILITY, this.instance, null, nbtBase);
    }
}
