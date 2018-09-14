package party.lemons.arcaneworld.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;
import party.lemons.arcaneworld.crafting.ritual.impl.*;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by Sam on 14/09/2018.
 */
@ZenRegister
@ZenClass("mods.ArcaneWorld")
public class ArcaneWorldCrt
{
    @ZenMethod
    public static void removeAll()
    {
        ((IForgeRegistryModifiable) RitualRegistry.REGISTRY).clear();
    }

    @ZenMethod
    public static void remove(String registryName)
    {
        ((IForgeRegistryModifiable) RitualRegistry.REGISTRY).remove(new ResourceLocation(registryName));
    }

    @ZenMethod
    public static void createRitualSummon(String name, String entity, IIngredient... inputs)
    {
        Class<? extends Entity> e = EntityList.getClass(new ResourceLocation(entity));
        if(e == null || !EntityLiving.class.isAssignableFrom(e))
            return;

        createRitual(name, new RitualSummon((Class<? extends EntityLiving>) e, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualDragonBreath(String name, IIngredient... inputs)
    {
        createRitual(name, new RitualDragonBreath(getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualWeather(String name, String weather, IIngredient... inputs)
    {
        RitualWeather.WeatherType type = RitualWeather.WeatherType.CLEAR;
        switch (weather.toLowerCase())
        {
            case "clear":
                type = RitualWeather.WeatherType.CLEAR;
                break;
            case "rain":
                type = RitualWeather.WeatherType.RAIN;
            case "thunder":
                type = RitualWeather.WeatherType.THUNDER;
                break;
        }

        createRitual(name, new RitualWeather(type, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualTime(String name, int timeChange, IIngredient... inputs)
    {
        createRitual(name, new RitualTime(timeChange, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualCreateItem(String name, IItemStack result, IIngredient... inputs)
    {
        createRitual(name, new RitualCreateItem(CraftTweakerMC.getItemStack(result), getIngredients(inputs)));
    }

    public static Ingredient[] getIngredients(IIngredient... inputs)
    {
        Ingredient[] ingreds = new Ingredient[inputs.length];
        for(int i = 0; i < inputs.length; i++)
            ingreds[i] = CraftTweakerMC.getIngredient(inputs[i]);

        return ingreds;
    }

    public static void createRitual(String name, Ritual ritual)
    {
        ResourceLocation location = new ResourceLocation("crafttweaker", name);
        ritual.setRegistryName(location);

        RitualRegistry.REGISTRY.register(ritual);
    }
}
