package party.lemons.arcaneworld.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.entity.EntityRecallOrb;

/**
 * Created by Sam on 21/09/2018.
 */
public class RenderRecallOrb extends Render<EntityRecallOrb>
{
    private RecallOrbModel model = new RecallOrbModel();
    private final ResourceLocation TEXTURE = new ResourceLocation(ArcaneWorld.MODID, "textures/entities/recall_orb.png");

    public RenderRecallOrb(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    public void doRender(EntityRecallOrb entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.translate((float)x, (float)y, (float)z);

        GL11.glTranslatef(0.4F, 0.4F, -0.4F);

        GlStateManager.scale(0.07F, 0.07F, 0.07F);

        GlStateManager.rotate((float) ((entity.ticksExisted * 3) + (Math.sin(entity.ticksExisted) / 20)), 0, -1, 0);
        GlStateManager.rotate((float) (entity.ticksExisted * 6 + (Math.sin(entity.ticksExisted) / 2)), 1, 0, 0);
        GlStateManager.rotate((float) (entity.ticksExisted + (Math.sin(entity.ticksExisted))), 0, 0, 1);

        GL11.glTranslatef(-0.4F, -0.4F, +0.4F);

        Minecraft.getMinecraft().renderEngine.bindTexture(getEntityTexture(entity));
        model.render(entity, 0, 0,0,  0, 0, 0F);

        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRecallOrb entity)
    {
        return TEXTURE;
    }
}
