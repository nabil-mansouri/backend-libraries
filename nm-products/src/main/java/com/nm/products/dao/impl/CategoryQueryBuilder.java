package com.nm.products.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.products.dtos.filters.CategoryFilterDto;
import com.nm.products.model.Category;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class CategoryQueryBuilder extends AbstractQueryBuilder<CategoryQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static CategoryQueryBuilder get() {
		return new CategoryQueryBuilder(Category.class, Category.class.getSimpleName());
	}

	public static CategoryQueryBuilder get(String alias) {
		return new CategoryQueryBuilder(Category.class, alias);
	}

	private final DetachedCriteria criteria;

	public CategoryQueryBuilder(Class<? extends Category> clazz, String alias) {
		criteria = DetachedCriteria.forClass(clazz, alias);
		withMainAlias(alias);
	}

	public String getIdName() {
		return wrapMain("id");
	}

	public CategoryQueryBuilder withAutoJoin(CategoryQueryBuilder other) {
		this.criteria.add(Property.forName(getIdName()).eqProperty(other.getIdName()));
		return this;
	}

	public CategoryQueryBuilder withId(Long idProductDef) {
		criteria.add(Restrictions.idEq(idProductDef));
		return this;
	}

	public CategoryQueryBuilder withNoChildren() {
		this.criteria.add(
				Subqueries.notExists(CategoryQueryBuilder.get("sub").withAutoJoin(this).withIdProjection().getQuery()));
		return this;
	}

	public CategoryQueryBuilder withChild(Long id) {
		createAlias(wrapMain("children"), "children");
		this.criteria.add(Restrictions.eq("children.id", id));
		return this;
	}

	public CategoryQueryBuilder withChildrendIdProjection() {
		createAlias(wrapMain("children"), "children");
		this.criteria.setProjection(Projections.property("children.id"));
		return this;
	}

	public CategoryQueryBuilder withJoinChildren(CategoryQueryBuilder other) {
		createAlias(wrapMain("children"), "children");
		this.criteria.add(Property.forName("children.id").eqProperty(other.getIdName()));
		return this;
	}

	public CategoryQueryBuilder withNoParent() {
		this.criteria.add(Subqueries
				.notExists(CategoryQueryBuilder.get("sub").withJoinChildren(this).withIdProjection().getQuery()));
		return this;
	}

	public CategoryQueryBuilder withFilter(CategoryFilterDto filter) {
		if (filter.getId() != null) {
			this.withId(filter.getId());
		}
		withRange(filter);
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
