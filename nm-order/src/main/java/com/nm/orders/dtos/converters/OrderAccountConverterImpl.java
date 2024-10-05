package com.nm.orders.dtos.converters;

import java.util.Collection;

import com.nm.orders.dao.DaoOrderAccount;
import com.nm.orders.dtos.impl.OrderAccountDtoImpl;
import com.nm.orders.models.OrderAccount;
import com.nm.orders.models.OrderAccountAny;
import com.nm.orders.models.OrderAccountSimple;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class OrderAccountConverterImpl extends DtoConverterDefault<OrderAccountDtoImpl, OrderAccount> {
	private DaoOrderAccount daoOrderAccount;

	public void setDaoOrderAccount(DaoOrderAccount daoOrderAccount) {
		this.daoOrderAccount = daoOrderAccount;
	}

	public Collection<Class<? extends OrderAccount>> managedEntity() {
		return ListUtils.all(OrderAccount.class, OrderAccountSimple.class, OrderAccountAny.class);
	}

	public OrderAccountDtoImpl toDto(OrderAccountDtoImpl dto, OrderAccount entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getId());
		if (entity instanceof OrderAccountSimple) {
			dto.setValue(((OrderAccountSimple) entity).getValue());
		}
		return dto;
	}

	public OrderAccountDtoImpl toDto(OrderAccount entity, OptionsList options) throws DtoConvertException {
		return toDto(new OrderAccountDtoImpl(), entity, options);
	}

	public OrderAccount toEntity(OrderAccountDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			boolean isAny = true;
			OrderAccount entity = new OrderAccountSimple();
			//
			if (dto.getId() != null) {
				isAny = false;
				entity = daoOrderAccount.get(dto.getId());
			}
			if (!dto.isAny()) {
				isAny = false;
			}
			//

			if (isAny) {
				return daoOrderAccount.getOrCreateAny();
			} else {
				if (entity instanceof OrderAccountSimple) {
					OrderAccountSimple simple = (OrderAccountSimple) entity;
					simple.setValue(dto.getValue());
				}
				return entity;
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
