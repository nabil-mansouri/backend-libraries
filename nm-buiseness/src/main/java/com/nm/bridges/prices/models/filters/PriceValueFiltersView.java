package com.nm.bridges.prices.models.filters;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.nm.bridges.prices.OrderType;
import com.nm.prices.contract.ContractPriceFilterViewModel;
import com.nm.prices.model.Price;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.prices.model.values.PriceValue;
import com.nm.shop.model.Shop;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity()
@Immutable()
@Table(name = "nm_view_price_value_filters")
public class PriceValueFiltersView implements Serializable, ContractPriceFilterViewModel {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	private Long id;
	@Column(name = "valueid")
	private Long idValue;
	@Column(name = "priceid")
	private Long idPrice;
	@Column(name = "ordertype")
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	@Column(name = "restaurantid")
	private Long idResto;
	@Column(name = "subjectid")
	private Long idSubject;
	@Column(name = "datefrom")
	private Date from;
	@Column(name = "dateto")
	private Date to;
	@Column(name = "subjectroot")
	private Boolean root;
	@ManyToOne()
	@JoinColumn(referencedColumnName = "id", name = "priceid", insertable = false, updatable = false)
	private Price price;
	@ManyToOne()
	@JoinColumn(referencedColumnName = "id", name = "subjectid", insertable = false, updatable = false)
	private PriceSubject subject;
	@ManyToOne()
	@JoinColumn(referencedColumnName = "id", name = "valueid", insertable = false, updatable = false)
	private PriceValue value;
	@ManyToOne()
	@JoinColumn(referencedColumnName = "id", name = "restaurantid", insertable = false, updatable = false)
	private Shop shop;

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Boolean getRoot() {
		return root;
	}

	public void setRoot(Boolean root) {
		this.root = root;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public PriceSubject getSubject() {
		return subject;
	}

	public void setSubject(PriceSubject subject) {
		this.subject = subject;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdValue() {
		return idValue;
	}

	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}

	public Long getIdPrice() {
		return idPrice;
	}

	public void setIdPrice(Long idPrice) {
		this.idPrice = idPrice;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Long getIdResto() {
		return idResto;
	}

	public void setIdResto(Long idResto) {
		this.idResto = idResto;
	}

	public PriceValue getValue() {
		return value;
	}

	public void setValue(PriceValue value) {
		this.value = value;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

}
