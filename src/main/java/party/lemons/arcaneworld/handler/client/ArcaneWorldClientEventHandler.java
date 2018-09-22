package party.lemons.arcaneworld.handler.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.item.ArcaneWorldItems;
import party.lemons.arcaneworld.item.ItemRecaller;
import party.lemons.arcaneworld.util.ArcaneWorldUtil;

/**
 * Created by Sam on 19/09/2018.
 */
@Mod.EventBusSubscriber(modid = ArcaneWorld.MODID,  value = Side.CLIENT)
public class ArcaneWorldClientEventHandler
{
    @GameRegistry.ItemStackHolder("arcaneworld:recall_eye")
    public static final ItemStack RECALL_EYE = new ItemStack(Items.ENDER_EYE);
    public static EntityItem entityItem = null;

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP player = minecraft.player;

        renderRecallerHand(minecraft, player, EnumHand.MAIN_HAND, event.getPartialTicks());
        renderRecallerHand(minecraft, player, EnumHand.OFF_HAND, event.getPartialTicks());
    }

    public static void renderRecallerHand(Minecraft mc, EntityPlayerSP player, EnumHand hand, float partialTicks)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(stack.getItem() == ArcaneWorldItems.RECALLER)
        {
            if(ItemRecaller.getPosition(stack) != null && ItemRecaller.getDimension(stack) != Integer.MAX_VALUE)
            {
                BlockPos pos = ItemRecaller.getPosition(stack);

                if(entityItem == null)
                {
                    entityItem = new EntityItem(mc.world, 0, 0, 0, RECALL_EYE);
                    entityItem.setInfinitePickupDelay();
                    entityItem.setNoDespawn();
                    entityItem.setGlowing(true);
                }
                double playerX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
                double playerY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
                double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
                GlStateManager.translate(-playerX, -playerY, -playerZ);
                entityItem.setPosition(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
                mc.getRenderManager().renderEntity(entityItem, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, 0, partialTicks, false);
                GlStateManager.translate(0,0,0);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(entityItem != null)
            entityItem.onUpdate();
    }
}
