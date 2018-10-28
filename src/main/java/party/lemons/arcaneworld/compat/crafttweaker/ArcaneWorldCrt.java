package party.lemons.arcaneworld.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
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
    public static void createRitualSummon(String name, String displayName, String entity, IIngredient... inputs)
    {
        Class<? extends Entity> e = EntityList.getClass(new ResourceLocation(entity));
        createRitual(name, displayName, new RitualSummon(e, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualDragonBreath(String name,String displayName, IIngredient... inputs)
    {
        createRitual(name, displayName, new RitualDragonBreath(getIngredients(inputs)));
    }

    @ZenMethod
    public static void createArenaRitual(String name, String displayName, String entity, IIngredient... inputs)
    {
        Class<? extends Entity> e = EntityList.getClass(new ResourceLocation(entity));
        createRitual(name, displayName, new RitualArena(e, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualWeather(String name, String displayName, String weather, IIngredient... inputs)
    {
        RitualWeather.WeatherType type = RitualWeather.WeatherType.CLEAR;
        switch (weather.toLowerCase())
        {
            case "clear":
                type = RitualWeather.WeatherType.CLEAR;
                break;
            case "rain":
                type = RitualWeather.WeatherType.RAIN;
                break;
            case "thunder":
                type = RitualWeather.WeatherType.THUNDER;
                break;
        }

        createRitual(name, displayName, new RitualWeather(type, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualTime(String name, String displayName, int timeChange, IIngredient... inputs)
    {
        createRitual(name, displayName, new RitualTime(timeChange, getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualCreateItem(String name, String displayName, IItemStack result, IIngredient... inputs)
    {
        createRitual(name,displayName, new RitualCreateItem(CraftTweakerMC.getItemStack(result), getIngredients(inputs)));
    }

    @ZenMethod
    public static void createRitualDungeon(String name, String displayName, IIngredient... inputs)
    {
        createRitual(name, displayName, new RitualDungeon(getIngredients(inputs)));
    }

    public static Ingredient[] getIngredients(IIngredient... inputs)
    {
        Ingredient[] ingreds = new Ingredient[inputs.length];
        for(int i = 0; i < inputs.length; i++)
            ingreds[i] = CraftTweakerMC.getIngredient(inputs[i]);

        return ingreds;
    }

    public static void createRitual(String name, String displayName, Ritual ritual)
    {
        ResourceLocation location = new ResourceLocation("crafttweaker", name);
        ritual.setRegistryName(location);
        ritual.setTranslationKey(displayName);

        RitualRegistry.REGISTRY.register(ritual);
    }
}
