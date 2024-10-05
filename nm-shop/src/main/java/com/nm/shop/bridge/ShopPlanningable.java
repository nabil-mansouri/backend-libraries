package com.nm.shop.bridge;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nm.plannings.model.Planningable;
import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_shop_planningable")
@DiscriminatorValue("SHOP")
public class ShopPlanningable extends Planningable implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Shop shop;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
