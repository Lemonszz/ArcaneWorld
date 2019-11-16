package party.lemons.arcaneworld.util.capabilities;

import net.minecraft.util.math.BlockPos;

public interface IRitualCoordinate
{
    public BlockPos getPos();

    public void setPos(BlockPos pos);

    public int getDim();

    public void setDim(int dim);
}
