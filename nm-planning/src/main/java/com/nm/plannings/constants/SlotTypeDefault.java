package com.nm.plannings.constants;

/**
 * 
 * @author nabilmansouri
 *
 */
public enum SlotTypeDefault implements SlotType {
	Close, Open;
	public boolean equals(SlotType type) {
		if (type instanceof SlotTypeDefault) {
			return ((SlotTypeDefault) type).ordinal() == this.ordinal();
		}
		return false;
	}
}
