package de.kxmischesdomi.horsebanners.mixin;

import de.kxmischesdomi.horsebanners.common.horsebanners.Bannerable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(HorseEntity.class)
public abstract class HorseEntityMixin extends HorseBaseEntity implements Bannerable {

	private static final TrackedData<ItemStack> BANNER_ITEM;

	protected HorseEntityMixin(EntityType<? extends HorseBaseEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public int getHandlerX() {
		return 8;
	}

	@Override
	public int getSlot() {
		return 2;
	}

	@Override
	public int getNewInventorySize() {
		return 3;
	}

	@Override
	public ItemStack getBannerItem() {
		return dataTracker.get(BANNER_ITEM);
	}

	@Override
	public int getHandlerY() {
		return 54;
	}

	@Override
	public int getInvY() {
		return 18;
	}

	@Override
	public int getInvX() {
		return 7;
	}

	@Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
	private void write(NbtCompound nbt, CallbackInfo ci) {
		if (!this.items.getStack(getSlot()).isEmpty()) {
			nbt.put("BannerItem", this.items.getStack(getSlot()).writeNbt(new NbtCompound()));
		}
	}

	@Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
	private void read(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains("BannerItem", 10)) {
			ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("BannerItem"));
			if (!itemStack.isEmpty() && itemStack.getItem() instanceof BannerItem) {
				dataTracker.set(BANNER_ITEM, itemStack);
				this.items.setStack(getSlot(), itemStack);
			}

		}
	}

	@Inject(method = "onInventoryChanged", at = @At(value = "TAIL"))
	private void change(Inventory sender, CallbackInfo ci) {
		ItemStack newStack = sender.getStack(2);
		ItemStack oldStack = dataTracker.get(BANNER_ITEM);
		dataTracker.set(BANNER_ITEM, newStack);

		if (newStack != oldStack && newStack.getItem() instanceof BannerItem) {
			this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
		}

	}

	@Inject(method = "initDataTracker", at = @At(value = "TAIL"))
	private void read(CallbackInfo ci) {
		this.dataTracker.startTracking(BANNER_ITEM, ItemStack.EMPTY);
	}

	static {
		BANNER_ITEM = DataTracker.registerData(HorseEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	}

}
