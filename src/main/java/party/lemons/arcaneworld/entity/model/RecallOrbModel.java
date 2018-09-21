package party.lemons.arcaneworld.entity.model;

/**
 * Created by Sam on 21/09/2018.
 */
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RecallOrbModel extends ModelBase {

    //fields
    ModelRenderer base;
    ModelRenderer e1;
    ModelRenderer e2;
    ModelRenderer e3;
    ModelRenderer e4;
    ModelRenderer e5;
    ModelRenderer e6;

    public RecallOrbModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.base = new ModelRenderer(this, 0, 0);
        this.base.addBox(5.5F, 1F, 5.5F, 5, 5, 5, 1.0F);
        this.base.setRotationPoint(8F, 4F, 8F);
        this.setRotateAngle(base, 0.0F, 0F, 0.0F);

        this.e1 = new ModelRenderer(this, 0, 0);
        this.e1.addBox(6F, 1.5F, 4.5F, 4, 4, 1, 1.0F);
        this.e2 = new ModelRenderer(this, 0, 0);
        this.e2.addBox(6F, 1.5F, 10.5F, 4, 4, 1, 1.0F);

        this.e3 = new ModelRenderer(this, 0, 0);
        this.e3.addBox(4.5F, 1.5F, 6F, 1, 4, 4, 1.0F);

        this.e4 = new ModelRenderer(this, 0, 0);
        this.e4.addBox(10.5F, 1.5F, 6F, 1, 4, 4, 1.0F);

        this.e5 = new ModelRenderer(this, 0, 0);
        this.e5.addBox(6F, 6F, 6F, 4, 1, 4, 1.0F);

        this.e6 = new ModelRenderer(this, 0, 0);
        this.e6.addBox(6F, 0F, 6F, 4, 1, 4, 1.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.base.render(scale);
        this.e1.render(scale);
        this.e2.render(scale);
        this.e3.render(scale);
        this.e4.render(scale);
        this.e5.render(scale);
        this.e6.render(scale);

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}