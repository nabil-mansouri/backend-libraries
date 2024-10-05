package com.nm.templates.models;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nm.datas.models.AppData;
import com.nm.templates.constants.TemplateNameEnum;
import com.nm.templates.constants.TemplateType;
import com.nm.templates.engines.TemplateModel;
import com.nm.utils.hibernate.impl.ModelTimeable;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil
 * 
 */
@Entity
@Table(name = "nm_template", schema = "mod_template")
public class Template extends ModelTimeable implements TemplateModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@SequenceGenerator(name = "seq_nm_template", schema = "mod_template", sequenceName = "seq_nm_template", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_nm_template")
	private Long id;
	//
	@Column(nullable = true)
	private String criteria;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private TemplateNameEnum templateName;
	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private TemplateType processor;
	@ManyToOne(optional = false)
	private AppData data;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "nm_template_arguments_list", schema = "mod_template")
	private Collection<TemplateArgument> arguments = new HashSet<TemplateArgument>(0);
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "nm_template_children", schema = "mod_template")
	private Collection<Template> children = new HashSet<Template>(0);

	public Collection<Template> getChildren() {
		return children;
	}

	public void setChildren(Collection<Template> children) {
		this.children = children;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public AppData getData() {
		return data;
	}

	public void setData(AppData data) {
		this.data = data;
	}

	public TemplateType getProcessor() {
		return processor;
	}

	public void setProcessor(TemplateType processor) {
		this.processor = processor;
	}

	public TemplateNameEnum getTemplateName() {
		return templateName;
	}

	public void setTemplateName(TemplateNameEnum templateName) {
		this.templateName = templateName;
	}

	public Collection<TemplateArgument> getArguments() {
		return arguments;
	}

	public void setArguments(Collection<TemplateArgument> arguments) {
		this.arguments = arguments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
