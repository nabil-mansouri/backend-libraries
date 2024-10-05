package com.nm.templates.daos;

import java.util.Arrays;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.google.common.collect.Sets;
import com.nm.templates.constants.TemplateNameEnum;
import com.nm.templates.models.Template;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class TemplateQueryBuilder extends AbstractQueryBuilder<TemplateQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TemplateQueryBuilder(Class<? extends Template> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private final DetachedCriteria criteria;

	public static TemplateQueryBuilder get() {
		return new TemplateQueryBuilder(Template.class);
	}

	public TemplateQueryBuilder withCriteria(String criteria) {
		if (criteria == null) {
			this.criteria.add(Restrictions.isNull("criteria"));
		} else {
			this.criteria.add(Restrictions.eq("criteria", criteria));
		}
		return this;
	}

	public TemplateQueryBuilder withChildrenIn(Collection<Template> children) {
		createAlias("children", "children");
		Collection<Long> i = Sets.newHashSet();
		for (Template t : children) {
			i.add(t.getId());
		}
		this.criteria.add(Restrictions.in("children.id", i));
		return this;
	}

	public TemplateQueryBuilder withName(TemplateNameEnum name) {
		this.criteria.add(Restrictions.eq("templateName", name));
		return this;
	}

	public TemplateQueryBuilder withName(TemplateNameEnum... name) {
		this.criteria.add(Restrictions.in("templateName", Arrays.asList(name)));
		return this;
	}

	public TemplateQueryBuilder withName(Collection<TemplateNameEnum> name) {
		this.criteria.add(Restrictions.in("templateName", name));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}
}
