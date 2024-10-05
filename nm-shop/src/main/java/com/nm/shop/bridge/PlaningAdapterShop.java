package com.nm.shop.bridge;

import com.nm.plannings.constants.PlanningType;
import com.nm.plannings.dao.impl.PlanningableQueryBuilder;
import com.nm.plannings.model.Planningable;
import com.nm.plannings.soa.PlanningAdapter;
import com.nm.shop.dao.DaoShop;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author nabilmansouri
 *
 */
public class PlaningAdapterShop implements PlanningAdapter {
	private DaoShop daoShop;

	public void setDaoShop(DaoShop daoShop) {
		this.daoShop = daoShop;
	}

	public boolean manage(PlanningType type) {
		return type.key().equals(new PlanningTypeShop().key());
	}

	public Planningable create(Long idPlannigeable, PlanningType type) throws NoDataFoundException {
		ShopPlanningable s = new ShopPlanningable();
		s.setShop(daoShop.get(idPlannigeable));
		return s;
	}

	public PlanningableQueryBuilder query(Long idPlannigeable, PlanningType type) {
		return new PlanningableShopQueryBuilder().withShop(idPlannigeable);
	}

}
