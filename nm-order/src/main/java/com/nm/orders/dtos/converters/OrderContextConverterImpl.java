package com.nm.orders.dtos.converters;

import java.nio.channels.NotYetBoundException;

import org.springframework.statemachine.state.State;

import com.nm.orders.constants.OrderActionType;
import com.nm.orders.constants.OrderEventType;
import com.nm.orders.constants.OrderOptions;
import com.nm.orders.constants.OrderStateType;
import com.nm.orders.dtos.impl.OrderViewDtoImpl;
import com.nm.orders.models.Order;
import com.nm.orders.models.OrderState;
import com.nm.orders.workflow.OrderStateContext;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class OrderContextConverterImpl extends DtoConverterDefault<OrderViewDtoImpl, OrderStateContext> {

	public OrderViewDtoImpl toDto(OrderViewDtoImpl dto, OrderStateContext entity, OptionsList options)
			throws DtoConvertException {
		try {
			dto.getStatesFlow().clear();
			if (options.contains(OrderOptions.OrderFlow)) {
				for (State<OrderStateType, OrderEventType> s : entity.getMachine().getStates()) {
					dto.getStatesFlow().put(s.getId(), OrderActionType.CouldOccure);
				}
				for (OrderStateType t : entity.getNexts().getNear()) {
					dto.getStatesFlow().put(t, OrderActionType.CanOccure);
				}
				for (OrderStateType t : entity.getNexts().getFar()) {
					dto.getStatesFlow().put(t, OrderActionType.CanOccureFar);
				}
				for (OrderState s : entity.getOrder().getStates()) {
					dto.getStatesFlow().put(s.getType(), OrderActionType.Occured);
				}
			}
			DtoConverter<OrderViewDtoImpl, Order> converter = registry().search(dto, Order.class);
			converter.toDto(dto, entity.getOrder(), options);
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public OrderViewDtoImpl toDto(OrderStateContext entity, OptionsList options) throws DtoConvertException {
		return toDto(new OrderViewDtoImpl(), entity, options);
	}

	public OrderStateContext toEntity(OrderViewDtoImpl dto, OptionsList options) throws DtoConvertException {
		throw new NotYetBoundException();
	}

}
