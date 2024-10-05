package com.nm.utils.jdbc.orm;

import java.lang.reflect.Field;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class JdbcOrmAssociationContext {
	public enum TypeOfAssociation {
		OneToMany, ManyToMany, OneToOne
	}

	private TypeOfAssociation type;
	private Class<?> target;
	private Field field;
	private MapSqlParameterSource map = new MapSqlParameterSource();

	public JdbcOrmAssociationContext(TypeOfAssociation type, Class<?> target, Field field) {
		super();
		this.type = type;
		this.target = target;
		this.field = field;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public MapSqlParameterSource getMap() {
		return map;
	}

	public void setMap(MapSqlParameterSource map) {
		this.map = map;
	}

	public TypeOfAssociation getType() {
		return type;
	}

	public void setType(TypeOfAssociation type) {
		this.type = type;
	}

	public Class<?> getTarget() {
		return target;
	}

	public void setTarget(Class<?> target) {
		this.target = target;
	}

}
