package com.nm.carts.dtos.converters;

import java.util.Collection;

import com.google.common.base.Strings;
import com.nm.carts.dtos.impl.CartOwnerDtoImpl;
import com.nm.carts.models.CartOwner;
import com.nm.carts.models.CartOwnerQueryBuilder;
import com.nm.carts.models.CartOwnerSession;
import com.nm.carts.models.DaoCartOwner;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CartOwnerConverterImpl extends DtoConverterDefault<CartOwnerDtoImpl, CartOwner> {
	private DaoCartOwner daoOwner;

	public void setDaoOwner(DaoCartOwner daoOwner) {
		this.daoOwner = daoOwner;
	}

	public CartOwnerDtoImpl toDto(CartOwner entity, OptionsList options) {
		CartOwnerDtoImpl dto = new CartOwnerDtoImpl();
		if (entity instanceof CartOwnerSession) {
			dto.setSessionId(((CartOwnerSession) entity).getSession());
		}
		dto.setId(entity.getId());
		return dto;
	}

	public CartOwnerDtoImpl toDto(CartOwnerDtoImpl dto, CartOwner entity, OptionsList options)
			throws DtoConvertException {
		return toDto(entity, options);
	}

	public CartOwner toEntity(CartOwnerDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			if (Strings.isNullOrEmpty(dto.getSessionId())) {
				throw new IllegalArgumentException("Session ID could notbe null");
			}
			CartOwnerQueryBuilder query = CartOwnerQueryBuilder.getSession().withSession(dto.getSessionId());
			Collection<CartOwner> owners = daoOwner.find(query);
			if (owners.isEmpty()) {
				CartOwnerSession owner = new CartOwnerSession();
				owner.setSession(dto.getSessionId());
				return owner;
			} else {
				return owners.iterator().next();
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
