package de.kxmischesdomi.horsebanners.common.horsebanners;

import net.minecraft.item.ItemStack;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public interface Bannerable {

	int getHandlerX();
	int getHandlerY();

	int getInvY();
	int getInvX();

	int getSlot();
	int getNewInventorySize();

	ItemStack getBannerItem();


}
    