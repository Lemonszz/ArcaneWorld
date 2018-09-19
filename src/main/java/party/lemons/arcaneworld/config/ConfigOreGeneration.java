package party.lemons.arcaneworld.config;

import net.minecraftforge.common.config.Config;

/**
 * Created by Sam on 19/09/2018.
 */
public class ConfigOreGeneration
{
    @Config.Name("Min Height")
    @Config.RangeInt(min = 0, max = 255)
    public int min_height;

    @Config.Name("Max Height")
    @Config.RangeInt(min = 0, max = 255)
    public int max_height;

    @Config.Name("Vein Size")
    @Config.RangeInt(min = 0)
    public int vein_size;

    @Config.Name("Vein Count")
    @Config.RangeInt(min = 0)
    public int vein_count;

    public ConfigOreGeneration(int min_height, int max_height, int vein_size, int vein_count)
    {
        this.min_height = min_height;
        this.max_height = max_height;
        this.vein_size = vein_size;
        this.vein_count = vein_count;
    }
}
