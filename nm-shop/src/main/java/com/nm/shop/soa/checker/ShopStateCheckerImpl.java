package com.nm.shop.soa.checker;

import org.joda.time.MutableDateTime;

import com.nm.plannings.dtos.PlanningDto;
import com.nm.plannings.soa.SoaPlanning;
import com.nm.shop.bridge.PlanningTypeShop;
import com.nm.shop.bridge.ShopEventRuleContext;
import com.nm.shop.constants.ShopConfigType;
import com.nm.shop.dtos.ShopStateDto;
import com.nm.shop.dtos.ShopViewDto;
import com.nm.shop.model.Shop;
import com.nm.shop.soa.impl.SoaShopConfiguration;

/**
 * Check wether restaurant is open
 * 
 * @author Nabil
 * 
 */
public class ShopStateCheckerImpl implements ShopChecker {
	private SoaPlanning soaPlanning;
	private SoaShopConfiguration soaShopConfiguration;

	public void setSoaPlanning(SoaPlanning soaPlanning) {
		this.soaPlanning = soaPlanning;
	}

	public void setSoaShopConfiguration(SoaShopConfiguration soaShopConfiguration) {
		this.soaShopConfiguration = soaShopConfiguration;
	}

	public void convert(Shop resto, ShopStateDto state) {
		check(resto.getId(), state);
	}

	private void check(Long idResto, ShopStateDto state) {
		boolean canBuy = soaShopConfiguration.getBoolean(ShopConfigType.ClosedCanBuy, true);
		if (canBuy) {
			state.setCanBuy(true);
			state.setOpen(false);
		} else {
			state.setCanBuy(false);
			state.setOpen(false);
			try {
				PlanningDto planning = soaPlanning.getOrCreate(idResto, new PlanningTypeShop());
				int nbMinuteBefore = soaShopConfiguration.getInt(ShopConfigType.ClosedCanBuyTimeBefore, 0);
				int nbMinuteUntil = soaShopConfiguration.getInt(ShopConfigType.ClosedCanBuyTimeUntil, 0);
				int max = Math.max(nbMinuteBefore, nbMinuteUntil);
				MutableDateTime start = new MutableDateTime();
				start.addMinutes(max);
				MutableDateTime end = new MutableDateTime();
				end.addMinutes(max + 1);
				boolean open = soaPlanning.isOpen(planning, start.toDate(), end.toDate(), new ShopEventRuleContext());
				if (open) {
					state.setOpen(true);
					state.setCanBuy(true);
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void convert(ShopViewDto resto, ShopStateDto state) {
		check(resto.getId(), state);
	}
}
