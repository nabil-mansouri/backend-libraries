package com.nm.geo.daos;

import java.math.BigInteger;
import java.util.Collection;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.nm.geo.beans.GeoLocationPoint;
import com.nm.geo.dtos.DtoAddressFilter;
import com.nm.geo.models.Address;
import com.nm.utils.hibernate.impl.AbstractQueryBuilder;

/**
 * 
 * @author Nabil
 * 
 */
public class QueryAddressBuilder extends AbstractQueryBuilder<QueryAddressBuilder> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryAddressBuilder() {

	}

	private DetachedCriteria criteria = DetachedCriteria.forClass(Address.class, getMainAlias());

	public static QueryAddressBuilder get() {
		return new QueryAddressBuilder();
	}

	public QueryAddressBuilder withAboutIdProjection() {
		createAlias("about", "about");
		this.criteria.setProjection(Projections.property("about.id"));
		return this;
	}

	public QueryAddressBuilder withAboutId(BigInteger ids) {
		createAlias("about", "about");
		this.criteria.add(Restrictions.eq("about.id", ids));
		return this;
	}

	public QueryAddressBuilder withAboutId(Collection<BigInteger> ids) {
		createAlias("about", "about");
		this.criteria.add(Restrictions.in("about.id", ids));
		return this;
	}

	public QueryAddressBuilder withFilter(DtoAddressFilter filter) {
		withClosestInDegree(GeoLocationPoint.fromDegrees(filter.getLatitude(), filter.getLongitude()), filter.getRadius());
		return this;
	}

	public QueryAddressBuilder withClosestInDegree(GeoLocationPoint location, double radius) {
		GeoLocationPoint[] boundingCoordinates = location.boundingCoordinates(radius);
		boolean meridian180WithinDistance = boundingCoordinates[0].getLongitudeInRadians() > boundingCoordinates[1].getLongitudeInRadians();
		//
		this.criteria
				.add(Restrictions.between("latRadian", boundingCoordinates[0].getLatitudeInRadians(), boundingCoordinates[1].getLatitudeInRadians()));
		//
		if (meridian180WithinDistance) {
			this.withDisjunction();
			this.disjunction.add(Restrictions.ge("lonRadian", boundingCoordinates[0].getLongitudeInRadians()));
			this.disjunction.add(Restrictions.le("lonRadian", boundingCoordinates[1].getLongitudeInRadians()));
		} else {
			Conjunction and = Restrictions.and();
			and.add(Restrictions.ge("lonRadian", boundingCoordinates[0].getLongitudeInRadians()));
			and.add(Restrictions.le("lonRadian", boundingCoordinates[1].getLongitudeInRadians()));
			this.criteria.add(and);
		}
		//
		Object[] value = new Object[] { location.getLatitudeInRadians(), location.getLatitudeInRadians(), location.getLongitudeInRadians(),
				GeoLocationPoint.getRatioRadius(radius) };
		Type[] types = new Type[] { StandardBasicTypes.DOUBLE, StandardBasicTypes.DOUBLE, StandardBasicTypes.DOUBLE, StandardBasicTypes.DOUBLE };
		this.criteria
				.add(Restrictions.sqlRestriction("acos(sin(?) * sin(latRadian) + cos(?) * cos(latRadian) * cos(lonRadian - ?)) <= ?", value, types));
		return this;
	}

	public DetachedCriteria getQuery() {
		return criteria;
	}

}
