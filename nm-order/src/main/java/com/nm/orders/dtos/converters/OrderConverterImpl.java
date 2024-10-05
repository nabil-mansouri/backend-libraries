package com.nm.orders.dtos.converters;

import com.nm.orders.constants.OrderOptions;
import com.nm.orders.contracts.OrderAdapter;
import com.nm.orders.dtos.OrderAccountDto;
import com.nm.orders.dtos.OrderDetailsDto;
import com.nm.orders.dtos.impl.OrderStateDtoImpl;
import com.nm.orders.dtos.impl.OrderViewDtoImpl;
import com.nm.orders.models.Order;
import com.nm.orders.models.OrderAccount;
import com.nm.orders.models.OrderDetails;
import com.nm.orders.models.OrderState;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class OrderConverterImpl extends DtoConverterDefault<OrderViewDtoImpl, Order> {

	public OrderViewDtoImpl toDto(OrderViewDtoImpl dto, Order entity, OptionsList options) throws DtoConvertException {
		OrderAdapter adapter = options.get(OrderOptions.KEY_ADAPTER, OrderAdapter.class);
		try {
			//
			if (options.contains(OrderOptions.OnlyReferences)) {
				dto.setUuid(entity.getUuid());
				return dto;
			}
			//
			dto.setCreated(entity.getCreated());
			dto.setId(entity.getId());
			dto.setUuid(entity.getUuid());
			//
			if (options.contains(OrderOptions.Details)) {
				DtoConverter<OrderDetailsDto, OrderDetails> converter = registry().search(adapter.dtoDetailsClass(),
						entity.getDetails().getClass());
				dto.setDetails(converter.toDto(entity.getDetails(), options));
			}
			//
			OrderState state = entity.getLastState();
			if (state != null) {
				dto.setLastState(new OrderStateDtoImpl(state.getCreated(), state.getType()));
			}
			DtoConverter<OrderAccountDto, OrderAccount> converter = registry().search(adapter.dtoAccountClass(),
					entity.getBuyer().getClass());
			if (options.contains(OrderOptions.Buyer)) {
				dto.setBuyer(converter.toDto(entity.getBuyer(), options));
			}
			if (options.contains(OrderOptions.Seller)) {
				dto.setSeller(converter.toDto(entity.getSeller(), options));
			}
			//
			if (options.contains(OrderOptions.States)) {
				for (OrderState s : entity.getStates()) {
					dto.getStates().add(new OrderStateDtoImpl(s.getCreated(), s.getType()));
				}
			}
			//
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
		return dto;
	}

	public OrderViewDtoImpl toDto(Order entity, OptionsList options) throws DtoConvertException {
		return toDto(new OrderViewDtoImpl(), entity, options);
	}

	public Order toEntity(OrderViewDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			OrderAdapter adapter = options.get(OrderOptions.KEY_ADAPTER, OrderAdapter.class);
			Order entity = new Order();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(Order.class).get(dto.getId());
			} else {
				entity.setUuid(UUIDUtils.UUID());
			}
			//
			{
				DtoConverter<OrderAccountDto, OrderAccount> converter = registry().search(dto.getBuyer().getClass(),
						adapter.entityAccountClass());
				entity.setBuyer(converter.toEntity(dto.getBuyer(), options));
				entity.setSeller(converter.toEntity(dto.getSeller(), options));
			}
			{
				DtoConverter<OrderDetailsDto, OrderDetails> converter = registry().search(dto.getDetails().getClass(),
						adapter.entityDetailsClass());
				entity.setDetails(converter.toEntity(dto.getDetails(), options));
			}
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
