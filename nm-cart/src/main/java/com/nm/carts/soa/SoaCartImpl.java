package com.nm.carts.soa;

import java.util.Collection;

import com.nm.carts.constants.CartOperation;
import com.nm.carts.contract.CartAdapter;
import com.nm.carts.dtos.CartDto;
import com.nm.carts.dtos.CartItemCompatibleDto;
import com.nm.carts.dtos.CartOwnerDto;
import com.nm.carts.dtos.impl.CartRequestDtoImpl;
import com.nm.carts.exceptions.CartManagementException;
import com.nm.carts.models.Cart;
import com.nm.carts.models.CartOwner;
import com.nm.carts.models.CartQueryBuilder;
import com.nm.carts.models.DaoCart;
import com.nm.carts.models.DaoCartOwner;
import com.nm.carts.operations.CartOperationChain;
import com.nm.carts.operations.CartOperationChainContext;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.NotFoundException;


/**
 * 
 * @author Nabil
 * 
 */
public class SoaCartImpl implements SoaCart {
	private DaoCart daoCart;
	private DaoCartOwner daoOwner;
	private DtoConverterRegistry registry;

	public void setDaoCart(DaoCart daoCart) {
		this.daoCart = daoCart;
	}

	public void setDaoOwner(DaoCartOwner daoOwner) {
		this.daoOwner = daoOwner;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public CartDto getOrCreate(CartOwnerDto subject, CartAdapter adapter)
			throws NotFoundException, DtoConvertException, CartManagementException {
		OptionsList options = new OptionsList();
		DtoConverter<CartOwnerDto, CartOwner> converter = registry.search(subject, CartOwner.class);
		Class<? extends CartDto> dtoClazz = adapter.dtoClass();
		DtoConverter<CartDto, Cart> converter2 = registry.search(dtoClazz, Cart.class);
		//
		CartOwner owner = converter.toEntity(subject, options);
		daoOwner.saveOrUpdate(owner);
		//
		Cart cart = new Cart();
		Collection<Cart> carts = daoCart.find(CartQueryBuilder.get().withOwner(owner));
		if (carts.isEmpty()) {
			cart.setOwner(owner);
			daoCart.saveOrUpdate(cart);
		} else {
			cart = carts.iterator().next();
		}
		// CHECK CART
		CartDto dto = converter2.toDto(cart, options);
		//
		CartOperationChainContext context = new CartOperationChainContext().setCart(dto).setAdapter(adapter)
				.setOperation(CartOperation.Validate);
		CartOperationChain.process(context);
		return dto;
	}

	public CartDto refresh(CartDto dto, CartAdapter adapter)
			throws NotFoundException, DtoConvertException, CartManagementException {
		CartOperationChainContext context = new CartOperationChainContext().setCart(dto).setAdapter(adapter)
				.setOperation(CartOperation.Validate);
		CartOperationChain.process(context);
		save(dto, adapter);
		return dto;
	}

	public CartDto get(Long id, CartAdapter adapter)
			throws DtoConvertException, NotFoundException, NoDataFoundException {
		Class<? extends CartDto> dtoClazz = adapter.dtoClass();
		OptionsList options = new OptionsList();
		DtoConverter<CartDto, Cart> converter2 = registry.search(dtoClazz, Cart.class);
		Cart cart = daoCart.get(id);
		return converter2.toDto(cart, options);
	}

	public void push(CartDto dto, CartAdapter adapter, CartItemCompatibleDto item)
			throws CartManagementException, DtoConvertException, NotFoundException {
		CartOperationChainContext context = new CartOperationChainContext().setCart(dto).setAdapter(adapter)
				.setToPush(item).setOperation(CartOperation.Push);
		CartOperationChain.process(context);
		save(dto, adapter);
	}

	public CartDto request(CartDto cart, CartAdapter adapter, CartRequestDtoImpl request)
			throws CartManagementException, DtoConvertException, NotFoundException {
		CartOperationChainContext context = new CartOperationChainContext().setAdapter(adapter).setCart(cart)
				.setRequest(request);
		CartOperationChain.process(context);
		save(cart, adapter);
		return cart;
	}

	public void save(CartDto dto, CartAdapter adapter) throws DtoConvertException, NotFoundException {
		DtoConverter<CartDto, Cart> converter = registry.search(dto, Cart.class);
		Cart cart = converter.toEntity(dto, new OptionsList());
		daoCart.saveOrUpdate(cart);
	}

	public void remove(CartDto cart) {
		daoCart.delete(daoCart.load(cart.getId()));
	}

}
