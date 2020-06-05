package party.lemons.arcaneworld.crafting.ritual.impl;

import net.minecraft.command.FunctionObject;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.arcaneworld.crafting.ritual.Ritual;
import party.lemons.arcaneworld.util.AdminExecute;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RitualCommand extends Ritual {
    private String[] command;

    public RitualCommand(String[] command, Ingredient... ingredients) {
        super(ingredients);
        this.command = command;
    }

    @Override
    public void onActivate(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player, ItemStack... items) {
        if(player.world.isRemote) return;

        MinecraftServer server = player.world.getMinecraftServer();
        ICommandSender sender = new AdminExecute(player, pos);

        for(int i = 0; i < command.length; i++)
            if(command[i].startsWith("/")) command[i] = command[i].replaceFirst("/", "");

        FunctionObject func = FunctionObject.create(server.getFunctionManager(), Arrays.asList(command));

        server.getFunctionManager().execute(func, sender);
    }
}
