package com.nm.tests.stats;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.google.common.base.Strings;
import com.nm.utils.dates.DateUtilsExt;
import com.nm.utils.jdbc.ICriteria;
import com.nm.utils.jdbc.IParameter;
import com.nm.utils.jdbc.ISelect;
import com.nm.utils.jdbc.ITable;
import com.nm.utils.jdbc.JdbcCriteria;
import com.nm.utils.jdbc.JdbcCriteriaList;
import com.nm.utils.jdbc.JdbcCriteriaSingle;
import com.nm.utils.jdbc.JdbcQueryBuilderSelect;

/**
 * 
 * @author Mansouri Nabil
 *
 */
class QueryBuilder extends JdbcQueryBuilderSelect {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Select implements ISelect {
		ApporteurId, //
		Age, //
		ActivMonth, //
		ActivationDate, //
		LocationRef, //
		Count, CountByNumberOfResident, Min, Max, Avg, SelectTown, SelectCanton, SelectCantonId
	}

	@Override
	public String getSelectAlias(ISelect ss) {
		if (ss instanceof Select) {
			Select s = (Select) ss;
			switch (s) {
			case ActivationDate:
				return "activation_date";
			case Avg:
				return "moy";
			case Count:
				return "nb";
			case CountByNumberOfResident:
				return "nb";
			case Max:
				return "maxi";
			case Min:
				return "mini";
			case SelectTown:
				return "address_town";
			case SelectCanton:
				return "text";
			case SelectCantonId:
				return "zone_id";
			case Age:
				return "age";
			case ActivMonth:
				break;
			case ApporteurId:
				break;
			case LocationRef:
				break;
			}
		}
		return null;
	}

	protected enum Criteria implements ICriteria {
		ActivationLe, ActivatedMonth, ActivatedYear, ActivateBetween, //
		BuisenessFinder, Category, NotDead, MustBirth, //
		Sexe, NotHavingSexe, //
		AgeGe, AgeLt, AgeLe, NoAge, //
		Alone, NotAlone, //
		TerminatedMonth, TerminatedYear, TerminationGt, //
		DepartementEqual, AttrCategoryRef, Dead, TownEqual,
	}

	protected enum Table implements ITable {
		AttributeChoice, Location
	}

	public QueryBuilder withActivBetween(Date from, Date to) {
		this.criterias.put(Criteria.ActivateBetween, new JdbcCriteriaList(from, to));
		return this;
	}

	public static QueryBuilder get() {
		return new QueryBuilder();
	}

	public QueryBuilder withCountMinAge() {
		this.selects.add(Select.Min);
		return this;
	}

	public QueryBuilder withCountMaxAge() {
		this.selects.add(Select.Max);
		return this;
	}

	public QueryBuilder withCountAvgAge() {
		this.selects.add(Select.Avg);
		return this;
	}

	public QueryBuilder withTownEqual(String town) {
		if (!Strings.isNullOrEmpty(town)) {
			this.tables.add(Table.Location);
			this.criterias.put(Criteria.TownEqual, new JdbcCriteriaSingle(town));
		}
		return this;
	}

	public QueryBuilder withGroupByTown() {
		this.selects.add(Select.SelectTown);
		this.tables.add(Table.Location);
		this.groups.add(Select.SelectTown);
		this.orders.add(Select.SelectTown);
		return this;
	}

	public QueryBuilder withCountByNumberOfResident() {
		this.selects.add(Select.CountByNumberOfResident);
		this.groups.add(Select.LocationRef);
		return this;
	}

	public QueryBuilder withActivationLe(Date from) {
		from = DateUtilsExt.maxMsOfDay(from);
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.ActivationLe, new JdbcCriteriaSingle(from));
		return this;
	}

	public QueryBuilder withTerminationGt(Date to) {
		to = DateUtilsExt.minMsOfDay(to);
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.TerminationGt, new JdbcCriteriaSingle(to));
		return this;
	}

	public QueryBuilder withDepartementEqual(String code) {
		this.criterias.put(Criteria.DepartementEqual, new JdbcCriteriaSingle(code));
		return this;
	}

	public QueryBuilder withBuisenessFinder(String id) {
		if (!Strings.isNullOrEmpty(id)) {
			this.criterias.put(Criteria.BuisenessFinder, new JdbcCriteriaSingle(id));
		}
		return this;
	}

	public QueryBuilder withActivOnMonth(Date d) {
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.ActivatedMonth, new JdbcCriteriaSingle(d));
		return this;
	}

	public QueryBuilder withActivOnYear(Date d) {
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.ActivatedYear, new JdbcCriteriaSingle(d));
		return this;
	}

	public QueryBuilder withNoAge(boolean b) {
		this.criterias.put(Criteria.NoAge, new JdbcCriteriaSingle(b));
		return this;
	}

	public QueryBuilder withTerminatedOnMonth(Date d) {
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.TerminatedMonth, new JdbcCriteriaSingle(d));
		return this;
	}

	public QueryBuilder withTerminatedOnYear(Date d) {
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.TerminatedYear, new JdbcCriteriaSingle(d));
		return this;
	}

	public QueryBuilder withCategory(Collection<String> id) {
		if (!id.isEmpty()) {
			this.criterias.put(Criteria.Category, new JdbcCriteriaList(id));
		}
		return this;
	}

	public QueryBuilder withCategory(String id) {
		if (!Strings.isNullOrEmpty(id)) {
			this.criterias.put(Criteria.Category, new JdbcCriteriaSingle(id));
		}
		return this;
	}

	public QueryBuilder withMustBirth(boolean b) {
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.MustBirth, new JdbcCriteriaSingle(b));
		return this;
	}

	public QueryBuilder withNotDead(boolean b) {
		this.tables.add(Table.Location);
		this.criterias.put(Criteria.NotDead, new JdbcCriteriaSingle(b));
		return this;
	}

	public QueryBuilder withDead(boolean b) {
		this.criterias.put(Criteria.Dead, new JdbcCriteriaSingle(b));
		return this;
	}

	public QueryBuilder withOnlyAlone(boolean b) {
		this.criterias.put(Criteria.Alone, new JdbcCriteriaSingle(b));
		return this;
	}

	public QueryBuilder withOnlyNotAlone(boolean b) {
		this.criterias.put(Criteria.NotAlone, new JdbcCriteriaSingle(b));
		return this;
	}

	public QueryBuilder withAgeLt(Long date) {
		if (date != null) {
			this.criterias.put(Criteria.AgeLt, new JdbcCriteriaSingle(date));
		}
		return this;
	}

	public QueryBuilder withAgeLe(Long date) {
		if (date != null) {
			this.criterias.put(Criteria.AgeLe, new JdbcCriteriaSingle(date));
		}
		return this;
	}

	public QueryBuilder withGroupByCanton() {
		this.groups.add(Select.SelectCanton);
		this.withSelectCanton();
		return this;
	}

	public QueryBuilder withGroupByCantonId() {
		this.selects.add(Select.SelectCantonId);
		this.groups.add(Select.SelectCantonId);
		withAttrCategoryRef(24l);
		return this;
	}

	public QueryBuilder withSelectCanton() {
		this.selects.add(Select.SelectCanton);
		this.withCantonCategory();
		return this;
	}

	public QueryBuilder withCantonCategory() {
		withAttrCategoryRef(24l);
		return this;
	}

	public QueryBuilder withAttrCategoryRef(Long id) {
		if (id != null) {
			this.tables.add(Table.AttributeChoice);
			this.criterias.put(Criteria.AttrCategoryRef, new JdbcCriteriaSingle(id));
		}
		return this;
	}

	public QueryBuilder withAgeGe(Long date) {
		if (date != null) {
			this.criterias.put(Criteria.AgeGe, new JdbcCriteriaSingle(date));
		}
		return this;
	}

	public QueryBuilder withCount() {
		this.selects.add(Select.Count);
		return this;
	}

	public QueryBuilder withSexeMan() {
		this.criterias.put(Criteria.Sexe, new JdbcCriteriaList(Arrays.asList("Mr", "M.")));
		return this;
	}

	public QueryBuilder withSexeWoman() {
		this.criterias.put(Criteria.NotHavingSexe, new JdbcCriteriaList(Arrays.asList("Mr", "M.")));
		return this;
	}

	@Override
	public String getSelectName(ISelect ss) {
		if (ss instanceof Select) {
			Select s = (Select) ss;
			switch (s) {
			case ActivationDate:
				return "activation_date";
			case Avg:
				return "avg(datediff(yy,r.date_of_birth,now()))";
			case Count:
				return "COUNT(*) as nb";
			case CountByNumberOfResident:
				return "COUNT(*) as nb";
			case Max:
				return "max(datediff(yy,r.date_of_birth,now()))";
			case Min:
				return "min(datediff(yy,r.date_of_birth,now()))";
			case SelectTown:
				return "TRIM(e.address_town)";
			case SelectCanton:
				return "DISTINCT c.text";
			case SelectCantonId:
				return "c.attr_choice_def";
			case Age:
				return "datediff(yy,r.date_of_birth,now())";
			case ActivMonth:
				return "month(activation_date)";
			case ApporteurId:
				// TODO
				break;
			case LocationRef:
				return "r.location_ref";
			}
		}
		return null;
	}

	@Override
	public String getTableName(ITable ss) {
		if (ss instanceof Table) {
			Table s = (Table) ss;
			switch (s) {
			case AttributeChoice:
				String str = "INNER JOIN attr_def AS d ON (d.entity_ref=e.location_def) ";
				str += " INNER JOIN attr_choice AS c ON (d.attr_choice_ref = c.attr_choice_def) ";
				return str;
			case Location:
				return "INNER JOIN epec_location AS e ON (e.location_def = r.location_ref) ";

			}
		}
		return null;
	}

	@Override
	public String getTableNameDefault() {
		return "resident r ";
	}

	@Override
	public String getWhere(ICriteria ss, JdbcCriteria crit) {
		if (ss instanceof Criteria) {
			Criteria s = (Criteria) ss;
			switch (s) {
			case ActivateBetween: {
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						List<Date> dates = getCriteriaList(Criteria.ActivateBetween, Date.class);
						p.setTimestamp(index, DateUtilsExt.toSqlTimestamp(dates.get(0)));
					}
				});
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						List<Date> dates = getCriteriaList(Criteria.ActivateBetween, Date.class);
						p.setTimestamp(index, DateUtilsExt.toSqlTimestamp(dates.get(1)));
					}
				});
				return ("e.activation_date BETWEEN ? AND ?");
			}

			case ActivatedMonth:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						int m = new DateTime(getCriteria(Criteria.ActivatedMonth, Date.class)).monthOfYear().get();
						p.setLong(index, m);
					}
				});
				return ("month(e.activation_date) = ?");
			case ActivatedYear:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						int m = new DateTime(getCriteria(Criteria.ActivatedYear, Date.class)).year().get();
						p.setLong(index, m);
					}
				});
				return ("year(e.activation_date) = ?");
			case ActivationLe: {
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setTimestamp(index, getCriteriaSqlTimestampFromDate(Criteria.ActivationLe));
					}
				});
				return ("e.activation_date <= ?");
			}

			case AgeGe: {
				push(new IParameter() {

					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setLong(index, getCriteria(Criteria.AgeGe, Long.class));
					}
				});
				return ("? <= datediff(yy,r.date_of_birth,now())");
			}
			case AgeLt: {
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setLong(index, getCriteria(Criteria.AgeLt, Long.class));
					}
				});
				return ("datediff(yy,r.date_of_birth,now()) < ?");
			}
			case AgeLe: {
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setLong(index, getCriteria(Criteria.AgeLe, Long.class));
					}
				});
				return ("datediff(yy,r.date_of_birth,now()) <= ?");
			}
			case Alone:
				if (getCriteria(Criteria.Alone, Boolean.class)) {
					String sub = "SELECT r2.location_ref FROM resident r2 WHERE r2.resident_def <> r.resident_def AND date_of_death IS NULL";
					return (String.format("r.location_ref NOT IN (%s)", sub));
				} else {
					String sub = "SELECT r2.location_ref FROM resident r2 WHERE r2.resident_def <> r.resident_def AND date_of_death IS NULL";
					return (String.format("r.location_ref  IN (%s)", sub));
				}
			case AttrCategoryRef:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setLong(index, getCriteria(Criteria.AttrCategoryRef, Long.class));
					}
				});
				return ("d.attr_category_ref = ?");
			case BuisenessFinder:
				break;
			case Category: {
				if (isList(Criteria.Category)) {
					String p = buildIn(getCriteriaList(Criteria.Category, String.class));
					return (String.format("r.authority_ref IN (%s)", p));
				} else {
					push(new IParameter() {
						private static final long serialVersionUID = 1L;

						public void set(PreparedStatement p, int index) throws SQLException {
							p.setString(index, getCriteria(Criteria.Category, String.class));
						}
					});
					return ("r.authority_ref = ?");
				}
			}
			case Dead: {
				if (criterias.containsKey(Criteria.Dead) && getCriteria(Criteria.Dead, Boolean.class)) {
					return ("r.date_of_death IS NOT NULL");
				}
			}
			case DepartementEqual:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						String code = getCriteria(Criteria.DepartementEqual, String.class);
						p.setString(index, String.format("%s%s", code.trim(), "%"));
					}
				});
				return ("TRIM(e.address_postcode) LIKE ?");
			case MustBirth:
				if (getCriteria(Criteria.MustBirth, Boolean.class)) {
					return ("r.date_of_birth IS NOT NULL");
				} else {
					return ("r.date_of_birth IS NULL");
				}
			case NoAge: {
				if (getCriteria(Criteria.NoAge, Boolean.class)) {
					return ("r.date_of_birth IS NULL");
				} else {
					return ("r.date_of_birth IS NOT NULL");
				}
			}
			case NotAlone:
				if (getCriteria(Criteria.NotAlone, Boolean.class)) {
					String sub = "SELECT r2.location_ref FROM resident r2 WHERE r2.resident_def <> r.resident_def AND date_of_death IS NULL";
					return (String.format("r.location_ref IN (%s)", sub));
				} else {
					String sub = "SELECT r2.location_ref FROM resident r2 WHERE r2.resident_def <> r.resident_def AND date_of_death IS NULL";
					return (String.format("r.location_ref NOT IN (%s)", sub));
				}
			case NotDead: {
				if (getCriteria(Criteria.NotDead, Boolean.class)) {
					return ("r.date_of_death IS NULL");
				} else {
					return ("r.date_of_death IS NOT NULL");
				}
			}
			case NotHavingSexe: {
				if (isList(Criteria.NotHavingSexe)) {
					String p = buildIn(getCriteriaList(Criteria.NotHavingSexe, String.class));
					return (String.format("r.title NOT IN (%s)", p));
				} else {
					push(new IParameter() {
						private static final long serialVersionUID = 1L;

						public void set(PreparedStatement p, int index) throws SQLException {
							p.setString(index, getCriteria(Criteria.NotHavingSexe, String.class));
						}
					});
					return ("r.title <> ?");
				}
			}
			case Sexe: {
				if (isList(Criteria.Sexe)) {
					String p = buildIn(getCriteriaList(Criteria.Sexe, String.class));
					return (String.format("r.title IN (%s)", p));
				} else {

					push(new IParameter() {
						private static final long serialVersionUID = 1L;

						public void set(PreparedStatement p, int index) throws SQLException {
							p.setString(index, getCriteria(Criteria.Sexe, String.class));
						}
					});
					return ("r.title = ?");
				}
			}
			case TerminatedMonth:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						int m = new DateTime(getCriteria(Criteria.TerminatedMonth, Date.class)).monthOfYear().get();
						p.setLong(index, m);
					}
				});
				return ("month(e.termination_date) = ?");
			case TerminatedYear:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						int m = new DateTime(getCriteria(Criteria.TerminatedYear, Date.class)).year().get();
						p.setLong(index, m);
					}
				});
				return ("year(e.termination_date) = ?");
			case TerminationGt: {
				push(new IParameter() {

					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setTimestamp(index, getCriteriaSqlTimestampFromDate(Criteria.TerminationGt));
					}
				});
				return ("(e.termination_date is null OR e.termination_date > ?)");
			}
			case TownEqual:
				push(new IParameter() {
					private static final long serialVersionUID = 1L;

					public void set(PreparedStatement p, int index) throws SQLException {
						p.setString(index, StringUtils.trim(getCriteria(Criteria.TownEqual, String.class)));
					}
				});
				return ("TRIM(e.address_town) = ?");

			}
		}
		return null;
	}

	public QueryBuilder withGroupByAge() {
		this.selects.add(Select.Age);
		this.groups.add(Select.Age);
		this.orders.add(Select.Age);
		return this;
	}
}
