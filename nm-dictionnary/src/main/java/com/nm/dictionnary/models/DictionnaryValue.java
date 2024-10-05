package com.nm.dictionnary.models;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import com.google.common.collect.Sets;
import com.nm.app.utils.StringMoreUtils;
import com.nm.dictionnary.constants.EnumDictionnaryState;
import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_dictionnary_value", schema = "mod_dictionnary")
public class DictionnaryValue extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_app_dictionnary_value", schema = "mod_dictionnary", sequenceName = "seq_nm_app_dictionnary_value", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_app_dictionnary_value")
	private Long id;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumDictionnaryState state;
	@Column(columnDefinition = "TEXT", nullable = true)
	private String value;
	@Column(nullable = true)
	private Integer valueInt;
	@Column(nullable = true)
	private Double valueDouble;
	@Column(nullable = true)
	private Date valueDate;
	@ManyToOne(optional = false)
	private DictionnaryEntry entry;
	@ManyToMany(mappedBy = "values")
	private Collection<DictionnaryEntity> entities = Sets.newHashSet();

	@AssertTrue
	public boolean isSetted() {
		return this.value != null || this.valueDate != null || this.valueDouble != null || this.valueInt != null;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public Double getValueDouble() {
		return valueDouble;
	}

	public Integer getValueInt() {
		return valueInt;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public void setValueDouble(Double valueDouble) {
		this.valueDouble = valueDouble;
	}

	public void setValueInt(Integer valueInt) {
		this.valueInt = valueInt;
	}

	public void setNormalizeValue(String s) {
	}

	public Collection<DictionnaryEntity> getEntities() {
		return entities;
	}

	public void setEntities(Collection<DictionnaryEntity> entityValues) {
		this.entities = entityValues;
	}

	@Access(AccessType.PROPERTY)
	public String getNormalizeValue() {
		if (value == null) {
			return null;
		}
		return StringMoreUtils.normalize(value);
	}

	public DictionnaryEntry getEntry() {
		return entry;
	}

	public void setEntry(DictionnaryEntry entry) {
		this.entry = entry;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EnumDictionnaryState getState() {
		return state;
	}

	public void setState(EnumDictionnaryState state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Identify one value by and and data
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((getNormalizeValue() == null) ? 0 : getNormalizeValue().hashCode());
		result = prime * result + ((valueDate == null) ? 0 : valueDate.hashCode());
		result = prime * result + ((valueDouble == null) ? 0 : valueDouble.hashCode());
		result = prime * result + ((valueInt == null) ? 0 : valueInt.hashCode());
		return result;
	}

	/**
	 * Use in Entry
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictionnaryValue other = (DictionnaryValue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (getNormalizeValue() == null) {
			if (other.getNormalizeValue() != null)
				return false;
		} else if (!getNormalizeValue().equals(other.getNormalizeValue()))
			return false;
		if (valueDate == null) {
			if (other.valueDate != null)
				return false;
		} else if (!valueDate.equals(other.valueDate))
			return false;
		if (valueDouble == null) {
			if (other.valueDouble != null)
				return false;
		} else if (!valueDouble.equals(other.valueDouble))
			return false;
		if (valueInt == null) {
			if (other.valueInt != null)
				return false;
		} else if (!valueInt.equals(other.valueInt))
			return false;
		return true;
	}

}
