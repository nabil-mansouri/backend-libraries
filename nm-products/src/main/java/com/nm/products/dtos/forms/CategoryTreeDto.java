package com.nm.products.dtos.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nm.products.dtos.views.CategoryViewDto;
import com.nm.utils.dates.UUIDUtils;
import com.nm.utils.graphs.IGraph;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class CategoryTreeDto extends CategoryViewDto implements IGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<CategoryTreeDto> childrens = new ArrayList<CategoryTreeDto>();
	private int sumProducts;
	private int level;
	private int countProducts;
	private boolean expanded = true;
	private String uuid = UUIDUtils.UUID();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String uuid() {
		return getUuid();
	}

	public boolean root() {
		return level == 0;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void computeLevelRecursively() {
		for (CategoryTreeDto categoryBean : childrens) {
			categoryBean.setLevel(getLevel() + 1);
			categoryBean.computeLevelRecursively();
		}
	}

	@JsonIgnore
	public List<? extends IGraph> childrens() {
		return (List<? extends IGraph>) getChildrens();
	}

	public Collection<CategoryTreeDto> getChildrens() {
		return childrens;
	}

	public int getCountProducts() {
		return countProducts;
	}

	public Boolean getHasChildren() {
		return !this.childrens.isEmpty();
	}

	public Boolean getHasProductDescendant() {
		if (getHasProducts()) {
			return true;
		}
		for (CategoryTreeDto c : this.childrens) {
			if (c.getHasProductDescendant()) {
				return true;
			}
		}
		return false;
	}

	public boolean getHasProducts() {
		return countProducts > 0;
	}

	public int getLevel() {
		return level;
	}

	public int getSumProducts() {
		return sumProducts;
	}

	public void setChildrens(Collection<CategoryTreeDto> children) {
		this.childrens = children;
	}

	public void setCountProducts(int countProducts) {
		this.countProducts = countProducts;
	}

	public void setHasChildren(Boolean has) {
	}

	public void setHasProductDescendant(Boolean b) {

	}

	public void setHasProducts(Boolean b) {

	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setSumProducts(int sumProducts) {
		this.sumProducts = sumProducts;
	}

	public void sumCounts() {
		int total = this.getCountProducts();
		for (CategoryTreeDto categoryBean : childrens) {
			categoryBean.sumCounts();
			total += categoryBean.getSumProducts();
		}
		this.setSumProducts(total);
	}
}
