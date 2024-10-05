package com.nm.plannings.converters.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;

import com.nm.plannings.constants.SlotType;
import com.nm.plannings.dao.impl.PlanningQueryBuilder;
import com.nm.plannings.dtos.DtoPlanning;
import com.nm.plannings.model.Planning;
import com.nm.plannings.sorters.EventComparator;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.node.ModelNode;

/***
 * 
 * @author nabilmansouri
 * 
 */
public class ConverterPlanningImpl extends DtoConverterDefault<DtoPlanning, Planning> {

	public Collection<Class<? extends DtoPlanning>> managed() {
		return ListUtils.all(DtoPlanning.class);
	}

	public Collection<Class<? extends Planning>> managedEntity() {
		return ListUtils.all(Planning.class);
	}

	public DtoPlanning toDto(DtoPlanning dto, Planning entity, OptionsList options) throws DtoConvertException {
		try {
			DtoPlanning bean = new DtoPlanning();
			bean.setId(entity.getId());
			for (SlotType type : bean.getGroups().keySet()) {
				Collections.sort(bean.getGroups().get(type), new EventComparator());
			}
			return bean;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public Planning toEntity(DtoPlanning dto, OptionsList options) throws DtoConvertException {
		try {
			Planning any = new Planning();
			if (dto.getId() != null) {
				any = AbstractGenericDao.get(Planning.class).get(dto.getId());
			} else if (dto.getAbout().getUuid() != null) {
				Collection<Planning> plannings = AbstractGenericDao.get(Planning.class).find(
						PlanningQueryBuilder.get().withAbout(dto.getAbout().getUuid()));
				if (!plannings.isEmpty()) {
					any = plannings.iterator().next();
				}
				any.setAbout(AbstractGenericDao.get(ModelNode.class, BigInteger.class).load(dto.getAbout().getUuid()));
			}
			return any;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
