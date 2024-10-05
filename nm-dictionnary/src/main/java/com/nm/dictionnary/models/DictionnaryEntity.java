package com.nm.dictionnary.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.collect.Sets;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.node.ModelNode;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
@Entity
@Table(name = "nm_dictionnary_entity", schema = "mod_dictionnary")
@Inheritance(strategy = InheritanceType.JOINED)
public class DictionnaryEntity extends ModelTimeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "nm_app_dictionnary_entity_seq", schema = "mod_dictionnary", sequenceName = "nm_app_dictionnary_entity_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nm_app_dictionnary_entity_seq")
	private Long id;
	@ManyToOne(optional = true)
	private ModelNode about;
	@ManyToMany()
	@JoinTable(name = "nm_app_dictionnary_entity_values_values", schema = "mod_dictionnary")
	private Collection<DictionnaryValue> values = Sets.newHashSet();

	public DictionnaryEntity() {
		super();
	}

	public ModelNode getAbout() {
		return about;
	}

	public void setAbout(ModelNode about) {
		this.about = about;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<DictionnaryValue> getValues() {
		return values;
	}

	public void setValues(Collection<DictionnaryValue> values) {
		this.values = values;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}