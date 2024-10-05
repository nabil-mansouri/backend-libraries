package com.rm.dao.discounts.tracking;

import java.util.Collection;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.rm.contract.discounts.constants.DiscountLifeCycleStateType;
import com.rm.dao.discounts.impl.DiscountQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleQueryBuilder;
import com.rm.dao.discounts.lifecycle.DiscountLifeCycleRuleQueryBuilder;
import com.rm.model.discounts.DiscountDefinition;
import com.rm.model.discounts.tracking.DiscountTracking;
import com.rm.utils.dao.impl.AbstractQueryBuilder;
import com.rm.utils.dao.impl.ProjectionAliasSQL;

/**
 * 
 * @author Nabil
 * 
 */
public class DiscountTrackingQueryBuilder extends AbstractQueryBuilder<DiscountTrackingQueryBuilder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static DiscountTrackingQueryBuilder get() {
		return new DiscountTrackingQueryBuilder(DiscountTracking.class);
	}

	private DetachedCriteria criteria;

	private DiscountTrackingQueryBuilder(Class<?> clazz) {
		this.criteria = DetachedCriteria.forClass(clazz, getMainAlias());
	}

	private DiscountTrackingQueryBuilder(DetachedCriteria c) {
		this.criteria = c;
	}

	public DiscountTrackingQueryBuilder withStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("lifecycle"), "lifecycle");
		createAlias("lifecycle.states", "states");
		this.criteria.add(Restrictions.eq("states.type", type));
		return this;
	}

	public DiscountTrackingQueryBuilder withDisjunctionNotExists(DiscountTrackingLifeCycleRuleQueryBuilder query) {
		this.disjunction.add(Subqueries.notExists(query.withJoin(this).withIdProjection().getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withDisjunction(Client client, DiscountDefinition discount) {
		createAlias(wrapMain("client"), "client");
		createAlias(wrapMain("discount"), "discount");
		Conjunction conjunction = Restrictions.and();
		conjunction.add(Restrictions.eq("client.id", client.getId()));
		conjunction.add(Restrictions.eq("discount.id", discount.getId()));
		this.disjunction.add(conjunction);
		return this;
	}

	public DiscountTrackingQueryBuilder withExists(DiscountTrackingLifeCycleRuleQueryBuilder query) {
		this.criteria.add(Subqueries.exists(query.withJoin(this).withIdProjection().getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withNotExists(DiscountTrackingLifeCycleRuleQueryBuilder query) {
		this.criteria.add(Subqueries.notExists(query.withJoin(this).withIdProjection().getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withJoin(DiscountLifeCycleRuleQueryBuilder query) {
		createAlias(wrapMain("discount"), ("discount"));
		this.criteria.add(Property.forName(wrapMain("discount.id")).eqProperty(query.getDiscountIdName()));
		return this;
	}

	public DiscountTrackingQueryBuilder withJoin(DiscountLifeCycleQueryBuilder query) {
		createAlias(wrapMain("discount"), ("discount"));
		this.criteria.add(Property.forName(wrapMain("discount.id")).eqProperty(query.getDiscountIdName()));
		return this;
	}

	public String getClientIdName() {
		createAlias(wrapMain("client"), "client");
		return "client.id";
	}

	public String getLifeCycleDateName() {
		createAlias(wrapMain("lifecycle"), "lifecycle");
		return "lifecycle.created";
	}

	public String getStateDateName() {
		createAlias(wrapMain("lifecycle"), "lifecycle");
		createAlias("lifecycle.states", "states");
		return "states.created";
	}

	public String getCountName() {
		return wrapMain("nb_count");
	}

	public String getDiscountIdName() {
		createAlias(wrapMain("discount"), ("discount"));
		return ("discount.id");
	}

	public String gettrackingIdName() {
		return wrapMain("id");
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

	public DiscountTrackingQueryBuilder withClient(ClientQueryBuilder client) {
		createAlias(wrapMain("client"), ("client"));
		this.criteria.add(Subqueries.propertyIn("client.id", client.withIdProjection().getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withClient(Collection<Long> client) {
		createAlias(wrapMain("client"), ("client"));
		this.criteria.add(Restrictions.in("client.id", client));
		return this;
	}

	public DiscountTrackingQueryBuilder withClient(Long client) {
		createAlias(wrapMain("client"), ("client"));
		this.criteria.add(Restrictions.eq("client.id", client));
		return this;
	}

	public DiscountTrackingQueryBuilder withDiscount(Long query) {
		createAlias(wrapMain("discount"), ("discount"));
		this.criteria.add(Restrictions.eq("discount.id", query));
		return this;
	}

	public DiscountTrackingQueryBuilder withDiscount(DiscountQueryBuilder query) {
		createAlias(wrapMain("discount"), ("discount"));
		this.criteria.add(Subqueries.propertyIn(("discount.id"), query.withProjectionId().getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withLastState(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("lastState"), "lastState");
		this.criteria.add(Restrictions.eq(("lastState.type"), type));
		return this;
	}

	public DiscountTrackingQueryBuilder withNotLastState(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("lastState"), "lastState");
		this.criteria.add(Restrictions.not(Restrictions.eq(("lastState.type"), type)));
		return this;
	}

	public DiscountTrackingQueryBuilder withProjectionClientId() {
		createAlias(wrapMain("client"), ("client"));
		criteria.setProjection(Projections.property(("client.id")));
		return this;
	}

	public DiscountTrackingQueryBuilder withHistoryStateType(DiscountLifeCycleStateType type) {
		createAlias(wrapMain("history"), ("history"));
		createAlias(("history.states"), ("states"));
		this.criteria.add(Restrictions.eq("states.type", type));
		return this;
	}

	public DiscountTrackingQueryBuilder withCountByDiscountLt(DiscountLifeCycleRuleQueryBuilder query) {
		createAlias(wrapMain("discount"), ("discount"));
		String groupBy = String.format("discount_id having count(*) < %s", query.getMaxNameNative());
		String[] alias = new String[] { "discount_id" };
		Type[] types = new Type[] { StandardBasicTypes.LONG };
		this.criteria.setProjection(
				new ProjectionAliasSQL(Projections.sqlGroupProjection("discount_id", groupBy, alias, types)));
		return this;
	}

	public DiscountTrackingQueryBuilder withCountByDiscountGe(DiscountLifeCycleRuleQueryBuilder query) {
		createAlias(wrapMain("discount"), ("discount"));
		String groupBy = String.format("discount_id having count(*) >= %s", query.getMaxNameNative());
		String[] alias = new String[] { "discount_id" };
		Type[] types = new Type[] { StandardBasicTypes.LONG };
		this.criteria.setProjection(
				new ProjectionAliasSQL(Projections.sqlGroupProjection("discount_id", groupBy, alias, types)));
		return this;
	}

	public DiscountTrackingQueryBuilder withTracking(AbstractQueryBuilder<?> query) {
		this.criteria.add(Subqueries.propertyIn("id", query.getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withDisjunctionIdIn(DiscountTrackingStatViewQueryBuilder query) {
		this.disjunction.add(Subqueries.propertyIn(wrapMain("id"), query.getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withIdIn(DiscountTrackingStatViewQueryBuilder query) {
		this.criteria.add(Subqueries.propertyIn(wrapMain("id"), query.getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withDisjunctionTracking(AbstractQueryBuilder<?> query) {
		this.disjunction.add(Subqueries.propertyIn("id", query.getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withLifeCycle(AbstractQueryBuilder<?> query) {
		createAlias("lifecycle", "lifecycle");
		this.criteria.add(Subqueries.propertyIn("lifecycle.id", query.getQuery()));
		return this;
	}

	public DiscountTrackingQueryBuilder withLifeCycle(DiscountTrackingLifeCycleQueryBuilder query) {
		createAlias("lifecycle", "lifecycle");
		this.criteria.add(Subqueries.propertyIn("lifecycle.id", query.withIdProjection().getQuery()));
		return this;
	}

}
