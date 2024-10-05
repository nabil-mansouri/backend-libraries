package com.nm.plannings.soa;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nm.plannings.dao.impl.PlanningQueryBuilder;
import com.nm.plannings.dao.impl.TimeSlotQueryBuilder;
import com.nm.plannings.dtos.DtoPlanning;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.dtos.DtoTimeSlot;
import com.nm.plannings.model.Planning;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlots;
import com.nm.plannings.operations.PlanningOperation;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaPlanningImpl implements SoaPlanning {

	private DtoConverterRegistry registry;
	private Collection<PlanningOperation> operations = Sets.newHashSet();

	public void setOperations(Collection<PlanningOperation> operations) {
		this.operations = operations;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public void remove(PlanningQueryBuilder query, OptionsList options) throws PlanningException {
		IGenericDao<Planning, Long> dao = AbstractGenericDao.get(Planning.class);
		Collection<Planning> cms = dao.find(query);
		dao.deleteAll(cms);
	}

	public Collection<DtoPlanning> fetch(PlanningQueryBuilder query, OptionsList options) throws PlanningException {
		try {
			Collection<Planning> cms = AbstractGenericDao.get(Planning.class).find(query);
			if (cms.isEmpty()) {
				return Lists.newArrayList();
			} else {
				Class<DtoPlanning> cmsClazz = options.dtoForModel(Planning.class, DtoPlanning.class);
				DtoConverter<DtoPlanning, Planning> converter = registry.search(cmsClazz, Planning.class);
				return converter.toDto(cms, options);
			}
		} catch (Exception e) {
			throw new PlanningException(e);
		}
	}

	public Collection<DtoTimeSlot> fetch(TimeSlotQueryBuilder query, OptionsList options) throws PlanningException {
		try {
			Collection<TimeSlot> cms = AbstractGenericDao.get(TimeSlot.class).find(query);
			if (cms.isEmpty()) {
				return Lists.newArrayList();
			} else {
				Class<DtoTimeSlot> cmsClazz = options.dtoForModel(TimeSlot.class, DtoTimeSlot.class);
				DtoConverter<DtoTimeSlot, TimeSlot> converter = registry.search(cmsClazz, TimeSlot.class);
				return converter.toDto(cms, options);
			}
		} catch (Exception e) {
			throw new PlanningException(e);
		}
	}

	public Planning saveOrUpdate(DtoPlanning dto, OptionsList options) throws PlanningException {
		try {
			DtoConverter<DtoPlanning, Planning> converter = registry.search(dto, Planning.class);
			Planning entity = converter.toEntity(dto, options);
			AbstractGenericDao.get(Planning.class).saveOrUpdate(entity);
			dto.setId(entity.getId());
			return entity;
		} catch (Exception e) {
			throw new PlanningException(e);
		}
	}

	public DtoPlanningResult operation(DtoPlanningQuery query, OptionsList options) throws PlanningException {
		try {
			for (PlanningOperation op : this.operations) {
				if (op.accept(query)) {
					return op.operation(query, options);
				}
			}
			throw new PlanningException("No operation compatible founded");
		} catch (PlanningException e) {
			throw e;
		} catch (Exception e) {
			throw new PlanningException(e);
		}
	}

	public TimeSlot removeSlot(DtoTimeSlot form, OptionsList options) throws PlanningException {
		IGenericDao<TimeSlot, Long> daoTimeSlot = AbstractGenericDao.get(TimeSlot.class);
		TimeSlot slot = daoTimeSlot.load(form.getId());
		daoTimeSlot.delete(slot);
		return slot;
	}

	public Planning remove(DtoPlanning dtoPlanning, OptionsList options) throws PlanningException {
		try {
			IGenericDao<Planning, Long> daoPlanning = AbstractGenericDao.get(Planning.class);
			Planning planning = daoPlanning.get(dtoPlanning.getId());
			planning.getSlots().clear();
			daoPlanning.delete(planning);
			return planning;
		} catch (Exception e) {
			throw new PlanningException(e);
		}
	}

	public Collection<TimeSlot> saveOrUpdateSlot(DtoPlanning dtoPlanning, DtoTimeSlot dto, OptionsList options)
			throws PlanningException {
		try {
			IGenericDao<TimeSlot, Long> daoTimeSlot = AbstractGenericDao.get(TimeSlot.class);
			IGenericDao<Planning, Long> daoPlanning = AbstractGenericDao.get(Planning.class);
			Planning planning = daoPlanning.loadById(dtoPlanning.getId());
			//
			DtoConverter<DtoTimeSlot, TimeSlots> converter = registry.search(dto, TimeSlots.class);
			TimeSlots entity = converter.toEntity(dto, options);
			for (TimeSlot slot : entity) {
				slot.setPlanning(planning);
				daoTimeSlot.saveOrUpdate(slot);
			}
			return entity;
		} catch (Exception e) {
			throw new PlanningException(e);
		}
	}

}
