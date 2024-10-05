package com.nm.social.models;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.nm.social.dtos.SocialEventDto;

/**
 * 
 * @author nabilmansouri
 *
 */
@Converter()
public class HibernateFieldConverterEvent implements AttributeConverter<SocialEventDto, String>, UserType {
	public static final String EE = "com.nm.social.models.HibernateFieldConverterEvent";
	private ObjectMapper mapper = new ObjectMapper();

	public String convertToDatabaseColumn(SocialEventDto attribute) {
		try {
			return mapper.writeValueAsString(attribute);
		} catch (Exception e) {
			return "";
		}
	}

	public SocialEventDto convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, SocialEventDto.class);
		} catch (Exception e) {
			return new SocialEventDto();
		}
	}

	public Class<SocialEventDto> returnedClass() {
		return SocialEventDto.class;
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
		String v = convertToDatabaseColumn((SocialEventDto) value);
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
