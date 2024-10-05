package com.nm.carts.soa;

import com.nm.carts.contract.CartAdapter;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemCompatibleDto;
import com.nm.carts.dtos.CartOwnerDto;
import com.nm.carts.dtos.impl.CartRequestDtoImpl;
import com.nm.carts.exceptions.CartManagementException;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author Nabil
 * 
 */
public interface SoaCart {
	public CartDto getOrCreate(CartOwnerDto subject, CartAdapter adapter)
			throws NotFoundException, DtoConvertException, CartManagementException;

	public CartDto refresh(CartDto dto, CartAdapter adapter)
			throws NotFoundException, DtoConvertException, CartManagementException;

	public CartDto get(Long id, CartAdapter adapter)
			throws DtoConvertException, NotFoundException, NoDataFoundException;

	public void push(CartDto cart, final CartAdapter adapter, CartItemCompatibleDto item)
			throws CartManagementException, DtoConvertException, NotFoundException;

	public CartDto request(CartDto cart, final CartAdapter adapter, CartRequestDtoImpl context)
			throws CartManagementException, DtoConvertException, NotFoundException;

	public void save(CartDto cart, CartAdapter adapter) throws DtoConvertException, NotFoundException;

	public void remove(CartDto order);
}
