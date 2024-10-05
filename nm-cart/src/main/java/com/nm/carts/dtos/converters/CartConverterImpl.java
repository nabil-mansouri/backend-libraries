package com.nm.carts.dtos.converters;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.impl.CartDtoImpl;
import com.nm.carts.dtos.impl.CartOwnerDtoImpl;
import com.nm.carts.models.Cart;
import com.nm.carts.models.CartOwner;
import com.nm.carts.models.DaoCart;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CartConverterImpl extends DtoConverterDefault<CartDtoImpl, Cart> {
	private DaoCart daoCart;

	public void setDaoCart(DaoCart daoCart) {
		this.daoCart = daoCart;
	}

	protected String _toString(CartDtoImpl dto) {
		ObjectMapper map = new ObjectMapper();
		try {
			return map.writeValueAsString(dto);
		} catch (Exception e) {
			return "";
		}
	}

	protected CartDtoImpl _toDto(Cart cart) {
		ObjectMapper map = new ObjectMapper();
		try {
			return (CartDtoImpl) map.readValue(cart.getDetail().getPayload(), CartDto.class);
		} catch (Exception e) {
			return new CartDtoImpl();
		}
	}

	public CartDtoImpl toDto(CartDtoImpl dto, Cart entity, OptionsList options) throws DtoConvertException {
		CartDtoImpl other = toDto(entity, options);
		BeanUtils.copyProperties(other, dto);
		return dto;
	}

	public CartDtoImpl toDto(Cart entity, OptionsList options) throws DtoConvertException {
		try {
			DtoConverter<CartOwnerDtoImpl, CartOwner> converter = registry().search(CartOwnerDtoImpl.class,
					CartOwner.class);
			CartDtoImpl dto = _toDto(entity);
			dto.setId(entity.getId());
			dto.setOwner(converter.toDto(entity.getOwner(), options));
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public Cart toEntity(Cart cart, CartDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			String payload = _toString(dto);
			if (dto.getId() != null) {
				cart = daoCart.get(dto.getId());
			}
			cart.getDetail().setPayload(payload);
			return cart;
		} catch (NoDataFoundException e) {
			throw new DtoConvertException(e);
		}
	}

}
