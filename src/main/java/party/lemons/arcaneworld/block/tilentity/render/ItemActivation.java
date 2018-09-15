package party.lemons.arcaneworld.block.tilentity.render;

import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * Created by Sam on 11/09/2018.
 */
public class ItemActivation
{
	private static final Random random = new Random();
	public static final int startValue = 150;

	private ItemStack stack;
	private float activationTime, offsetX, offsetZ;
	private float dirX = 0, dirZ = 0;

	public ItemActivation(ItemStack stack)
	{
		this.stack = stack;
		activationTime = startValue;

        if(stack.isEmpty())
			activationTime = -1;
		dirX = (random.nextFloat() / 75) * (random.nextBoolean() ? -1 : 1);
		dirZ = (random.nextFloat() / 75) * (random.nextBoolean() ? -1 : 1);
	}

	public float getAliveTicks() {
		return activationTime;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetZ()
	{
		return offsetZ;
	}

	public void setActivationTime(int activationTime) {
		this.activationTime = activationTime;
	}

	public void decreaseTime()
	{
		activationTime--;
	}

	public void addOffset() {
		this.offsetX += dirX;
		this.offsetZ += dirZ;

	}

	public ItemStack getItem() {
		return stack;
	}

	public float getVelocityZ() {
		return dirZ;
	}
	public float getVelocityX() {
		return dirX;
	}
}
