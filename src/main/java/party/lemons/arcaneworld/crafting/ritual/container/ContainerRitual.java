package party.lemons.arcaneworld.crafting.ritual.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Sam on 10/09/2018.
 */
public class ContainerRitual extends Container
{
	private final ItemStackHandler ritualInventory;

	public ContainerRitual(InventoryPlayer playerInv, ItemStackHandler inventory, EntityPlayer player)
	{
		this.ritualInventory = inventory;

		for (int j = 0; j < ritualInventory.getSlots(); ++j)
		{
			this.addSlotToContainer(new SlotItemHandler(ritualInventory, j, 44 + j * 18, 10));
		}

		for (int l = 0; l < 3; ++l)
		{
			for (int k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1)
		{
			this.addSlotToContainer(new Slot(playerInv, i1, 8 + i1 * 18, 109));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int size = ritualInventory.getSlots();

			if (index < size)
			{
				if (!this.mergeItemStack(itemstack1, size, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, size, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}
