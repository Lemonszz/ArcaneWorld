package party.lemons.arcaneworld.util.capabilities;

import net.minecraft.util.math.BlockPos;

public class RitualCoordinate implements IRitualCoordinate
{
    private BlockPos position = new BlockPos(0, 0, 0);
    private int dimension = 0;


    @Override
    public BlockPos getPos()
    {
        return this.position;
    }

    @Override
    public void setPos(BlockPos pos)
    {
        this.position = pos;
    }

    @Override
    public int getDim()
    {
        return this.dimension;
    }

    @Override
    public void setDim(int dim)
    {
        this.dimension = dim;
    }
}
