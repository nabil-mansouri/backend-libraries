package com.nm.utils.jdbc;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface IParameter extends Serializable {
	public void set(PreparedStatement p, int index) throws SQLException;
}