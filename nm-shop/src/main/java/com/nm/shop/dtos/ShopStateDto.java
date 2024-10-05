package com.nm.shop.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Nabil
 * 
 */
@JsonAutoDetect
public class ShopStateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isOpen;
	private boolean canBuy;

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isCanBuy() {
		return canBuy;
	}

	public void setCanBuy(boolean canBuy) {
		this.canBuy = canBuy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
