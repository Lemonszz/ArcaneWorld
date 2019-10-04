package party.lemons.arcaneworld.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by ewnm89 on 4/10/2019.
 */
public class BiomeArcaneDungeon extends BiomeVoid {
    public BiomeArcaneDungeon(BiomeProperties properties) { super(properties); }

    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 0x333333;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 0x666666;
    }
}
