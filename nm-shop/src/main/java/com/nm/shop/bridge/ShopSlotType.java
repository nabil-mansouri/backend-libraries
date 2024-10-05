package com.nm.shop.bridge;

import com.nm.plannings.constants.SlotType;

/**
 * 
 * @author nabilmansouri
 *
 */
public enum ShopSlotType implements SlotType {
	ShopClose, ShopOpen;
	public boolean equals(SlotType type) {
		if (type instanceof ShopSlotType) {
			return ((ShopSlotType) type).ordinal() == this.ordinal();
		}
		return false;
	}
}
