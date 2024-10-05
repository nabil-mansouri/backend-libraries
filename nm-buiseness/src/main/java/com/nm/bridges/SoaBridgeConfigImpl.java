package com.nm.bridges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.bridges.prices.OrderType;
import com.nm.bridges.prices.dtos.OrderTypeFormDto;
import com.nm.config.ModuleConfigKeyDefault;
import com.nm.config.SoaModuleConfig;
import com.nm.config.dtos.ModuleConfigDto;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Service
public class SoaBridgeConfigImpl implements SoaBridgeConfig {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private SoaModuleConfig soaModuleConfig;

	public Collection<OrderTypeFormDto> getOrderTypes() {
		Collection<OrderType> selected = convert(getSelectedOrderTypes());
		Collection<OrderTypeFormDto> res = new ArrayList<OrderTypeFormDto>();
		for (OrderType o : OrderType.values()) {
			res.add(new OrderTypeFormDto(o).setSelected(selected.contains(o)));
		}
		return res;
	}

	protected Collection<OrderType> convert(Collection<OrderTypeFormDto> a) {
		Collection<OrderType> res = new ArrayList<OrderType>();
		for (OrderTypeFormDto t : a) {
			res.add(t.getOrderType());
		}
		return res;
	}

	public Collection<OrderTypeFormDto> getSelectedOrderTypes() {
		try {
			ModuleConfigDto config = soaModuleConfig.fetch(ModuleConfigKeyDefault.OrderTypes);
			Collection<OrderType> lists = mapper.readValue(config.getPayload(), new TypeReference<List<OrderType>>() {
			});
			Collection<OrderTypeFormDto> res = new ArrayList<OrderTypeFormDto>();
			for (OrderType o : lists) {
				res.add(new OrderTypeFormDto(o).setSelected(lists.contains(o)));
			}
			return res;
		} catch (Exception e) {
			return new ArrayList<OrderTypeFormDto>();
		}
	}

	public void hasOrderType() throws NoDataFoundException {
		try {
			ModuleConfigDto config = soaModuleConfig.fetch(ModuleConfigKeyDefault.OrderTypes);
			mapper.readValue(config.getPayload(), new TypeReference<List<OrderType>>() {
			});
		} catch (Exception e) {
			throw new NoDataFoundException(e);
		}
	}

	public void setSelectedOrderTypes(Collection<OrderType> form) {
		Collection<OrderTypeFormDto> res = new ArrayList<OrderTypeFormDto>();
		for (OrderType o : form) {
			res.add(new OrderTypeFormDto(o).setSelected(true));
		}
		setSelectedOrderTypesBean(res);
	}

	public void setSelectedOrderTypesBean(Collection<OrderTypeFormDto> form) {
		Collection<OrderType> t = new ArrayList<OrderType>();
		for (OrderTypeFormDto tt : form) {
			if (tt.isSelected()) {
				t.add(tt.getOrderType());
			}
		}
		try {
			soaModuleConfig.setText(ModuleConfigKeyDefault.OrderTypes, mapper.writeValueAsString(t));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
