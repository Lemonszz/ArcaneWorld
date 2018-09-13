package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Sam on 12/09/2018.
 */
public class RitualWeather extends Ritual
{
    private WeatherType type;

    public RitualWeather(WeatherType type, Ingredient... ingredients) {
        super(ingredients);

        this.type = type;
    }

    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos)
    {
        WorldServer ws = (WorldServer) world;
        int weatherTime = (300 + (ws.rand.nextInt(600)) * 20);
        WorldInfo worldinfo = ws.getWorldInfo();

        if(!type.raining)
        {
            worldinfo.setCleanWeatherTime(weatherTime);
            worldinfo.setRainTime(0);
            worldinfo.setThunderTime(0);
            System.out.println("Clear");
        }
        else
        {
            worldinfo.setCleanWeatherTime(0);
            worldinfo.setRainTime(weatherTime);
            worldinfo.setThunderTime(weatherTime);
        }
        worldinfo.setRaining(type.raining);
        worldinfo.setThundering(type.thundering);

    }

    public enum WeatherType
    {
        CLEAR(false, false),
        RAIN(true, false),
        THUNDER(true, true);

        private boolean raining, thundering;
        WeatherType(boolean raining, boolean thundering)
        {
            this.raining = raining;
            this.thundering = thundering;
        }
    }
}
