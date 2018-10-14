package party.lemons.arcaneworld.entity.model;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.entity.EntityRift;

import javax.annotation.Nullable;

/**
 * Created by Sam on 3/10/2018.
 */
public class RenderRift extends Render<EntityRift>
{
    private static final ResourceLocation RIFT_TEXTURE = new ResourceLocation(ArcaneWorld.MODID, "textures/entities/rift_texture.png");
    private static int sphereIdOutside;

    public RenderRift(RenderManager renderManager)
    {
        super(renderManager);
    }

    public void doRender(EntityRift entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        Sphere sphere = new Sphere();
        sphere.setDrawStyle(GLU.GLU_FILL);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.setOrientation(GLU.GLU_OUTSIDE);
        bindTexture(RIFT_TEXTURE);
        sphere.setTextureFlag(true);
        sphereIdOutside = GlStateManager.glGenLists(1);
        GlStateManager.glNewList(sphereIdOutside, GL11.GL_COMPILE);

        float radius = (float) (2 + (Math.sin(entity.ticksExisted) / 10));
        float slices = 4 + Math.abs((float) (Math.sin(entity.ticksExisted) * 5));

        sphere.draw(radius, (int) slices, (int) (slices + entity.world.rand.nextFloat() * 5));
        GlStateManager.glEndList();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + entity.height / 2.0F, z);
        GlStateManager.rotate(entity.ticksExisted, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.ticksExisted * 2, 1.0F, 0.0F, 1.0F);

        float col = (float) Math.asin(entity.ticksExisted) / 10;
        GlStateManager.color(col, col, col, 1F);
        GlStateManager.callList(sphereIdOutside);

        GlStateManager.popMatrix();
        GlStateManager.glDeleteLists(sphereIdOutside, 1);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRift entity)
    {
        return null;
    }
}
