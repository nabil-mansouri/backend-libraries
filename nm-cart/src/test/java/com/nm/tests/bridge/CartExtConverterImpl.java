package com.nm.tests.bridge;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.carts.dtos.impl.CartDtoImpl;
import com.nm.carts.models.Cart;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author nabilmansouri
 *
 */
public class CartExtConverterImpl extends DtoConverterDefault<CartDtoExtImpl, Cart> {

	protected CartDtoExtImpl _toDto(Cart cart) {
		ObjectMapper map = new ObjectMapper();
		try {
			return map.readValue(cart.getDetail().getPayload(), CartDtoExtImpl.class);
		} catch (Exception e) {
			return new CartDtoExtImpl();
		}
	}

	public CartDtoExtImpl toDto(CartDtoExtImpl dto, Cart entity, OptionsList options) throws DtoConvertException {
		CartDtoExtImpl other = toDto(entity, options);
		BeanUtils.copyProperties(other, dto);
		return dto;
	}

	public CartDtoExtImpl toDto(Cart entity, OptionsList options) throws DtoConvertException {
		try {
			CartDtoExtImpl dto = _toDto(entity);
			DtoConverter<CartDtoImpl, Cart> c = registry().search(CartDtoImpl.class, Cart.class);
			c.toDto(dto, entity, options);
			return dto;
		} catch (NotFoundException e) {
			throw new DtoConvertException(e);
		}
	}

	public Cart toEntity(Cart cart, CartDtoExtImpl dto, OptionsList options) throws DtoConvertException {
		try {
			DtoConverter<CartDtoImpl, Cart> c = registry().search(CartDtoImpl.class, Cart.class);
			return c.toEntity(dto, options);
		} catch (NotFoundException e) {
			throw new DtoConvertException(e);
		}
	}

}
