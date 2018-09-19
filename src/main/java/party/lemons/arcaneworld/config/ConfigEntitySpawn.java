package party.lemons.arcaneworld.config;

import net.minecraftforge.common.config.Config;

/**
 * Created by Sam on 19/09/2018.
 */
public class ConfigEntitySpawn
{
    @Config.Name("Rarity")
    @Config.RangeInt(min = 0)
    public int rarity;

    @Config.Name("Pack Size Min")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int min;

    @Config.Name("Pack Size max")
    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0)
    public int max;

    @Config.Name("Enabled")
    @Config.RequiresMcRestart
    public boolean enabled;

    public ConfigEntitySpawn(int rarity, int min, int max, boolean enabled)
    {
        this.rarity = rarity;
        this.min = min;
        this.max = max;
        this.enabled = enabled;
    }
}
