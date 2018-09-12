package party.lemons.arcaneworld.block.tilentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.handler.ArcaneWorldSounds;

/**
 * Created by Sam on 10/09/2018.
 */
public class TESRRitualTable extends TileEntitySpecialRenderer<TileEntityRitualTable>
{
	private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
	private final ModelBook modelBook = new ModelBook();

	public void render(TileEntityRitualTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
		float f = (float)te.tickCount + partialTicks;
		GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
		float f1;

		for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F))
		{
		}

		while (f1 < -(float)Math.PI)
		{
			f1 += ((float)Math.PI * 2F);
		}

		float f2 = te.bookRotationPrev + f1 * partialTicks;
		GlStateManager.rotate(-f2 * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
		this.bindTexture(TEXTURE_BOOK);
		float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
		float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
		f3 = (f3 - (float)MathHelper.fastFloor((double)f3)) * 1.6F - 0.3F;
		f4 = (f4 - (float)MathHelper.fastFloor((double)f4)) * 1.6F - 0.3F;

		if (f3 < 0.0F)
		{
			f3 = 0.0F;
		}

		if (f4 < 0.0F)
		{
			f4 = 0.0F;
		}

		if (f3 > 1.0F)
		{
			f3 = 1.0F;
		}

		if (f4 > 1.0F)
		{
			f4 = 1.0F;
		}

		float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
		GlStateManager.enableCull();
		this.modelBook.render(null, f, f3, f4, f5, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
		GlStateManager.translate(0, 0.25F, 0);
		for(ItemActivation activation : te.getItemActivations())
		{
			renderItemActivation(activation, partialTicks);
		}
		GlStateManager.popMatrix();
		boolean remove = te.getItemActivations().removeIf(a -> a.getAliveTicks() <= 0);
		if(remove)
			Minecraft.getMinecraft().world.playSound(te.getPos(), ArcaneWorldSounds.RITUAL_ITEM_POP, SoundCategory.BLOCKS, 1F, 1F, false);
	}

	private void renderItemActivation(ItemActivation activation, float delta)
	{
		float i = ItemActivation.startValue - activation.getAliveTicks();
		float f = (i + delta) / (float) ItemActivation.startValue;
		float f1 = f * f;
		float f2 = f * f1;
		float f3 = 10.25F * f2 * f1 + -24.95F * f1 * f1 + 25.5F * f2 + -13.8F * f1 + 4.0F * f;
		float f4 = f3 * (float)Math.PI;
		GlStateManager.enableAlpha();
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();GlStateManager.disableCull();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.translate(activation.getOffsetX(), i / 75F, activation.getOffsetZ());
		float f7 = 0.25F + 1.0F * (MathHelper.sin(f4) / 2);
		GlStateManager.scale(f7, -f7, f7);
		GlStateManager.rotate(900.0F * MathHelper.abs(MathHelper.sin(f4)), 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(6.0F * MathHelper.cos(f * 8.0F), 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(6.0F * MathHelper.cos(f * 8.0F), 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(Math.signum(activation.getVelocityZ()) * (i / 1F * activation.getVelocityZ()), 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(Math.signum(activation.getVelocityX()) * (i / 1F * activation.getOffsetX()), 1.0F, 0.0F, 0.0F);
		Minecraft.getMinecraft().getRenderItem().renderItem(activation.getItem(), ItemCameraTransforms.TransformType.FIXED);
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableCull();

		activation.decreaseTime();
		activation.addOffset();
	}
}
