package com.nm.utils.json;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

import com.google.common.base.Objects;

/**
 * 
 * @author nabilmansouri
 *
 */
@Converter(autoApply = true)
@SuppressWarnings("rawtypes")
public class EnumHibernateType implements AttributeConverter<Enum, String>, UserType {
	public static final String EE = "com.nm.utils.json.EnumHibernateType";

	public String convertToDatabaseColumn(Enum attribute) {
		return EnumJsonConverterRegistry.key(attribute);
	}

	public Enum convertToEntityAttribute(String dbData) {
		return EnumJsonConverterRegistry.getInstance().find(dbData);
	}

	public Class<Enum> returnedClass() {
		return Enum.class;
	}

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object value = StandardBasicTypes.STRING.nullSafeGet(rs, names[0], session, owner);
		return ((value != null) ? convertToEntityAttribute(value.toString()) : null);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		String v = convertToDatabaseColumn((Enum) value);
		StandardBasicTypes.STRING.nullSafeSet(st, v, index, session);
	}

	/* "default" implementations */

	public boolean isMutable() {
		return false;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equal(x, y);
	}

	public int hashCode(Object x) throws HibernateException {
		assert(x != null);
		return x.hashCode();
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

}
