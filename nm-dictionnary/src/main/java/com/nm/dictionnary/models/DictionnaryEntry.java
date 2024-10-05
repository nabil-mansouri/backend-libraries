package com.nm.dictionnary.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.AssertTrue;

import com.google.common.collect.Sets;
import com.nm.dictionnary.constants.EnumDictionnaryState;
import com.nm.dictionnary.constants.EnumDictionnaryType;
import com.nm.utils.hibernate.impl.ModelTimeable;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_dictionnary_entry", schema = "mod_dictionnary", uniqueConstraints = @UniqueConstraint(columnNames = { "key",
		"dictionnary_id" }) )
public class DictionnaryEntry extends ModelTimeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_app_dictionnary_entry", schema = "mod_dictionnary", sequenceName = "seq_nm_app_dictionnary_entry", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_app_dictionnary_entry")
	private Long id;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String key;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumDictionnaryState state;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumDictionnaryType type;
	@ManyToOne(optional = false)
	private Dictionnary dictionnary;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entry")
	private Collection<DictionnaryValue> values = Sets.newHashSet();

	public DictionnaryValue findByNormalized(DictionnaryValue toFind, DictionnaryValue defaut) {
		for (DictionnaryValue v : values) {
			// IGNORE ID WHILE SEARCHING
			toFind.setId(v.getId());
			if (toFind.equals(v)) {
				return v;
			}
		}
		return defaut;
	}

	public void add(DictionnaryValue v) {
		v.setEntry(this);
		this.values.add(v);
	}

	public void remove(DictionnaryValue v) {
		this.values.remove(v);
	}

	public Collection<DictionnaryValue> getValues() {
		return values;
	}

	public void setValues(Collection<DictionnaryValue> values) {
		this.values = values;
	}

	@AssertTrue(message = "dictionnary.entry.mode")
	protected boolean isValidSize() {
		switch (this.type) {
		case Multi:
			return 0 < this.values.size();
		case Single:
			return this.values.size() == 1;

		}
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public EnumDictionnaryState getState() {
		return state;
	}

	public void setState(EnumDictionnaryState state) {
		this.state = state;
	}

	public EnumDictionnaryType getType() {
		return type;
	}

	public void setType(EnumDictionnaryType type) {
		this.type = type;
	}

	public Dictionnary getDictionnary() {
		return dictionnary;
	}

	public void setDictionnary(Dictionnary dictionnary) {
		this.dictionnary = dictionnary;
	}

}
