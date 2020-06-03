package party.lemons.arcaneworld.util;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class AdminExecute implements ICommandSender
{
    private final EntityPlayer player;
    private final BlockPos pos;

    public AdminExecute(EntityPlayer player, BlockPos pos)
    {
        this.player = player;
        this.pos = pos;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return player.getName();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName()
    {
        return player.getDisplayName();
    }

    @Override
    public void sendMessage(ITextComponent p_145747_1_)
    {
        player.sendMessage(p_145747_1_);
    }

    @Override
    public boolean canUseCommand(int p_70003_1_, @Nonnull String p_70003_2_)
    {
        return true;
    }

    @Nonnull
    @Override
    public BlockPos getPosition()
    {
        return pos;
    }

    @Nonnull
    @Override
    public World getEntityWorld()
    {
        return player.getEntityWorld();
    }

    @Nonnull
    @Override
    public Vec3d getPositionVector()
    {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public Entity getCommandSenderEntity()
    {
        return player.getCommandSenderEntity();
    }

    @Override
    public boolean sendCommandFeedback()
    {
        return player.sendCommandFeedback();
    }

    @Override
    public void setCommandStat(CommandResultStats.Type type, int amount)
    {
        player.setCommandStat(type, amount);
    }

    @Override
    public MinecraftServer getServer()
    {
        return player.getServer();
    }
}
