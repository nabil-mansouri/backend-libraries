package com.nm.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.joda.time.DateTime;
import org.joda.time.contrib.hibernate.PersistentDateTime;

/**
 * 
 * @author Nabil
 * 
 */
public class CustomPersistentDateTime extends PersistentDateTime {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CLAZZ = "com.restomanager.framework.utils.CustomPersistentDateTime";

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(rs, names, session, owner);
		if (timestamp == null) {
			return null;
		}

		return new DateTime(timestamp);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			StandardBasicTypes.TIMESTAMP.nullSafeSet(st, null, index, session);
		} else {
			if (value instanceof DateTime) {
				StandardBasicTypes.TIMESTAMP.nullSafeSet(st, ((DateTime) value).toDate(), index, session);
			} else if (value instanceof Date) {
				StandardBasicTypes.TIMESTAMP.nullSafeSet(st, ((Date) value), index, session);
			}else{
				StandardBasicTypes.TIMESTAMP.nullSafeSet(st, (value), index, session);
			}
		}
	}

}
