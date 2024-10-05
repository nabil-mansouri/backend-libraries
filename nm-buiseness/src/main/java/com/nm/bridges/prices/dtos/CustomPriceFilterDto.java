package com.nm.bridges.prices.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.nm.bridges.prices.OrderType;
import com.nm.prices.dtos.filters.PriceFilterDto;
import com.nm.products.constants.ProductType;
import com.nm.products.dtos.filters.ProductFilterDto;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.products.dtos.views.ProductViewDto;
import com.nm.shop.dtos.ShopViewDto;

/**
 * 
 * @author nabilmansouri
 *
 */
@JsonAutoDetect
@JsonTypeName("CustomPriceFilterDto")
public class CustomPriceFilterDto extends PriceFilterDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductType type;
	private CategoryViewDto category;
	private List<ProductViewDto> allProducts = new ArrayList<ProductViewDto>();
	private List<ShopViewDto> allRestaurants = new ArrayList<ShopViewDto>();
	private List<OrderType> allTypes = new ArrayList<OrderType>();
	private boolean anyOrder = true;
	private boolean anyRestaurant = true;
	private boolean anyProduct = true;
	private List<Long> products = new ArrayList<Long>();
	private List<OrderType> orderTypes = new ArrayList<OrderType>();
	private List<Long> restaurants = new ArrayList<Long>();
	private List<Long> idProducts = new ArrayList<Long>();
	private Long idCategory;
	private Long idRestaurant;
	private OrderType orderType;

	public CategoryViewDto getCategory() {
		return category;
	}

	public void setCategory(CategoryViewDto category) {
		this.category = category;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public ProductFilterDto toProductFilter() {
		ProductFilterDto filter = new ProductFilterDto();
		filter.setType(this.getType());
		filter.setIdCategory(this.getIdCategory());
		if (this.getCategory() != null) {
			filter.setIdCategory(this.getCategory().getId());
		}
		filter.setIds(this.getIdProducts());
		return filter;
	}

	public boolean hasProductFilter() {
		if (this.getType() != null) {
			return true;
		}
		if (this.getIdCategory() != null) {
			return true;
		}
		if (this.getCategory() != null) {
			return true;
		}
		return false;
	}

	public List<ProductViewDto> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<ProductViewDto> allProducts) {
		this.allProducts = allProducts;
	}

	public List<ShopViewDto> getAllRestaurants() {
		return allRestaurants;
	}

	public void setAllRestaurants(List<ShopViewDto> allRestaurants) {
		this.allRestaurants = allRestaurants;
	}

	public List<OrderType> getAllTypes() {
		return allTypes;
	}

	public void setAllTypes(List<OrderType> allTypes) {
		this.allTypes = allTypes;
	}

	public boolean isAnyOrder() {
		return anyOrder;
	}

	public void setAnyOrder(boolean anyOrder) {
		this.anyOrder = anyOrder;
	}

	public boolean isAnyRestaurant() {
		return anyRestaurant;
	}

	public void setAnyRestaurant(boolean anyRestaurant) {
		this.anyRestaurant = anyRestaurant;
	}

	public boolean isAnyProduct() {
		return anyProduct;
	}

	public void setAnyProduct(boolean anyProduct) {
		this.anyProduct = anyProduct;
	}

	public List<Long> getProducts() {
		return products;
	}

	public void setProducts(List<Long> products) {
		this.products = products;
	}

	public List<OrderType> getOrderTypes() {
		return orderTypes;
	}

	public void setOrderTypes(List<OrderType> orderTypes) {
		this.orderTypes = orderTypes;
	}

	public List<Long> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Long> restaurants) {
		this.restaurants = restaurants;
	}

	public List<Long> getIdProducts() {
		return idProducts;
	}

	public void setIdProducts(List<Long> idProducts) {
		this.idProducts = idProducts;
	}

	public Long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}

	public Long getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(Long idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	@Override
	public boolean assertRequired() {
		Assert.notNull(this.getIdRestaurant());
		Assert.notNull(this.getOrderType());
		return super.assertRequired();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
