package com.nm.bridges.prices.queries;

import java.util.Collection;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.nm.bridges.prices.models.subject.PriceSubjectPart;
import com.nm.bridges.prices.models.subject.PriceSubjectProduct;
import com.nm.prices.dao.impl.PriceSubjectQueryBuilder;
import com.nm.prices.model.subject.PriceSubject;
import com.nm.products.dao.impl.ProductDefinitionQueryBuilder;
import com.nm.products.model.ProductDefinition;
import com.nm.products.model.ProductDefinitionPart;

/**
 * 
 * @author nabilmansouri
 *
 */
public class CustomPriceSubjectQueryBuilder extends PriceSubjectQueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CustomPriceSubjectQueryBuilder(Class<? extends PriceSubject> clazz) {
		super(clazz);
	}

	public static CustomPriceSubjectQueryBuilder get() {
		return new CustomPriceSubjectQueryBuilder(PriceSubject.class);
	}

	public static CustomPriceSubjectQueryBuilder getProduct() {
		return new CustomPriceSubjectQueryBuilder(PriceSubjectProduct.class);
	}

	public static CustomPriceSubjectQueryBuilder getPart() {
		return new CustomPriceSubjectQueryBuilder(PriceSubjectPart.class);
	}

	public CustomPriceSubjectQueryBuilder withProduct(ProductDefinition product) {
		this.criteria.add(Restrictions.eq("product", product));
		return this;
	}

	public CustomPriceSubjectQueryBuilder withProducts(ProductDefinitionQueryBuilder query) {
		createAlias("product", "pr");
		criteria.add(Subqueries.propertyIn("pr.id", query.withIdProjection().getQuery()));
		return this;
	}

	public CustomPriceSubjectQueryBuilder withPart(ProductDefinitionPart part) {
		this.criteria.add(Restrictions.eq("part", part));
		return this;
	}

	public CustomPriceSubjectQueryBuilder withProductProjection() {
		createAlias("product", "product");
		this.criteria.setProjection(Projections.property("product.id"));
		return this;
	}

	public CustomPriceSubjectQueryBuilder withProduct(Long product) {
		createAlias("product", "product");
		this.criteria.add(Restrictions.eq("product.id", product));
		return this;
	}

	public CustomPriceSubjectQueryBuilder withProducts(Collection<Long> ids) {
		if (!ids.isEmpty()) {
			createAlias("product", "pr");
			criteria.add(Restrictions.in("pr.id", ids));
		}
		return this;
	}

	public CustomPriceSubjectQueryBuilder withPart(Long part) {
		createAlias("part", "part");
		this.criteria.add(Restrictions.eq("part.id", part));
		return this;
	}
}
