package party.lemons.arcaneworld.crafting.ritual.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.ItemStackHandler;
import party.lemons.arcaneworld.ArcaneWorld;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;
import party.lemons.arcaneworld.crafting.ritual.RitualRegistry;
import party.lemons.arcaneworld.crafting.ritual.impl.Ritual;
import party.lemons.arcaneworld.network.MessageRitualClientActivate;

/**
 * Created by Sam on 10/09/2018.
 */
public class GuiRitual extends GuiContainer
{
	public static final ResourceLocation RITUAL_BG = new ResourceLocation(ArcaneWorld.MODID, "textures/gui/ritual.png");
	private final InventoryPlayer inventoryPlayer;
	private final ItemStackHandler ritualInventory;
	private GuiButtonExt castButton;
	private BlockPos blockPos;

	public GuiRitual(InventoryPlayer playerInv, ItemStackHandler inventory, BlockPos pos)
	{
		super(new ContainerRitual(playerInv, inventory, Minecraft.getMinecraft().player));

		this.inventoryPlayer = playerInv;
		this.ritualInventory = inventory;
		this.ySize = 133;

		this.blockPos = pos;

	}

	@Override
	public void initGui()
	{
		super.initGui();
		int width = (this.width - this.xSize) / 2;
		int height = (this.height - this.ySize) / 2;

		castButton = new GuiButtonExt(0, width + 61, height + 32, 54, 10, I18n.format("arcaneworld.gui.ritual.cast"));
		castButton.enabled = false;
		this.buttonList.add(castButton);
	}

	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
			case 0:
				ArcaneWorld.NETWORK.sendToServer(new MessageRitualClientActivate(blockPos));
				Minecraft.getMinecraft().displayGuiScreen(null);
				break;
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		updateButton();

		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		if(Loader.isModLoaded("jei"))
		{
			int width = (this.width - this.xSize) / 2;
			int height = (this.height - this.ySize) / 2;
			this.drawString(mc.fontRenderer, "?", width +5, height +5, 0xCCCCCC);
		}

		this.renderHoveredToolTip(mouseX, mouseY);
	}

	private void updateButton()
	{
		castButton.enabled = false;

		TileEntity te = Minecraft.getMinecraft().world.getTileEntity(blockPos);
		if(te instanceof TileEntityRitualTable)
		{
			if(((TileEntityRitualTable)te).getState() != TileEntityRitualTable.RitualState.NONE)
				return;
		}

		for(Ritual ritual : RitualRegistry.REGISTRY.getValuesCollection())
		{
			if(ritual.isEmpty())
				continue;

			NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
			for(int i = 0; i < stacks.size(); i++)
				stacks.set(i, ritualInventory.getStackInSlot(i));

			if(ritual.matches(stacks))
			{
				castButton.enabled = true;
				break;
			}
		}
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(this.inventoryPlayer.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(RITUAL_BG);
		int width = (this.width - this.xSize) / 2;
		int height = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(width, height, 0, 0, this.xSize, this.ySize);
	}
}
