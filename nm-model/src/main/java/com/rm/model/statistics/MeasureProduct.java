package com.rm.model.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.nm.app.stats.Dates;
import com.rm.contract.prices.constants.OrderType;
import com.rm.contract.products.constants.ProductType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity()
@Table(name = "nm_view_measure_product")
@Immutable()
public class MeasureProduct implements Serializable {

	private static final long serialVersionUID = 2197152804710966988L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Order infos
	 */
	@Id
	private Long id;
	@Column(name = "orderid")
	private Long idOrder;
	@Column(name = "cartid")
	private Long idCart;
	@Column(name = "ordertype")
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	@Column(name = "ordertotal")
	private Double orderTotal;
	@Column(name = "clientid")
	private Long idClient;
	@Column(name = "restaurantid")
	private Long idResto;
	/**
	 * Transaction date
	 */
	@Column(name = "transactionminute", insertable = false, updatable = false)
	private Date transactionminute;
	@Column(name = "transactionhour", insertable = false, updatable = false)
	private Date transactionhour;
	@Column(name = "transactionday", insertable = false, updatable = false)
	private Date transactionday;
	@Column(name = "transactionweek", insertable = false, updatable = false)
	private Date transactionweek;
	@Column(name = "transactionmonth", insertable = false, updatable = false)
	private Date transactionmonth;
	@Column(name = "transactionyear", insertable = false, updatable = false)
	private Date transactionyear;
	/**
	 * Product infos
	 */
	@Column(name = "productid")
	private Long idProduct;
	@Column(name = "productprice")
	private Double priceOfProduct;
	@Column(name = "producttype")
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	/**
	 * Dates
	 */
	@OneToMany
	@JoinColumn(name = "d_date", referencedColumnName = "transactionyear")
	private Collection<Dates> years = new ArrayList<Dates>();
	@OneToMany
	@JoinColumn(name = "d_date", referencedColumnName = "transactionmonth")
	private Collection<Dates> months = new ArrayList<Dates>();
	@OneToMany
	@JoinColumn(name = "d_date", referencedColumnName = "transactionweek")
	private Collection<Dates> weeks = new ArrayList<Dates>();
	@OneToMany
	@JoinColumn(name = "d_date", referencedColumnName = "transactionday")
	private Collection<Dates> days = new ArrayList<Dates>();
	@OneToMany
	@JoinColumn(name = "d_date", referencedColumnName = "transactionhour")
	private Collection<Dates> hours = new ArrayList<Dates>();
	@OneToMany
	@JoinColumn(name = "d_date", referencedColumnName = "transactionminute")
	private Collection<Dates> minutes = new ArrayList<Dates>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Dates> getYears() {
		return years;
	}

	public void setYears(Collection<Dates> years) {
		this.years = years;
	}

	public Collection<Dates> getMonths() {
		return months;
	}

	public void setMonths(Collection<Dates> months) {
		this.months = months;
	}

	public Collection<Dates> getWeeks() {
		return weeks;
	}

	public void setWeeks(Collection<Dates> weeks) {
		this.weeks = weeks;
	}

	public Collection<Dates> getDays() {
		return days;
	}

	public void setDays(Collection<Dates> days) {
		this.days = days;
	}

	public Collection<Dates> getHours() {
		return hours;
	}

	public void setHours(Collection<Dates> hours) {
		this.hours = hours;
	}

	public Collection<Dates> getMinutes() {
		return minutes;
	}

	public void setMinutes(Collection<Dates> minutes) {
		this.minutes = minutes;
	}

	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public Long getIdCart() {
		return idCart;
	}

	public void setIdCart(Long idCart) {
		this.idCart = idCart;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public Long getIdResto() {
		return idResto;
	}

	public void setIdResto(Long idResto) {
		this.idResto = idResto;
	}

	public Date getTransactionminute() {
		return transactionminute;
	}

	public void setTransactionminute(Date transactionminute) {
		this.transactionminute = transactionminute;
	}

	public Date getTransactionhour() {
		return transactionhour;
	}

	public void setTransactionhour(Date transactionhour) {
		this.transactionhour = transactionhour;
	}

	public Date getTransactionday() {
		return transactionday;
	}

	public void setTransactionday(Date transactionday) {
		this.transactionday = transactionday;
	}

	public Date getTransactionweek() {
		return transactionweek;
	}

	public void setTransactionweek(Date transactionweek) {
		this.transactionweek = transactionweek;
	}

	public Date getTransactionmonth() {
		return transactionmonth;
	}

	public void setTransactionmonth(Date transactionmonth) {
		this.transactionmonth = transactionmonth;
	}

	public Date getTransactionyear() {
		return transactionyear;
	}

	public void setTransactionyear(Date transactionyear) {
		this.transactionyear = transactionyear;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Double getPriceOfProduct() {
		return priceOfProduct;
	}

	public void setPriceOfProduct(Double priceOfProduct) {
		this.priceOfProduct = priceOfProduct;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}
