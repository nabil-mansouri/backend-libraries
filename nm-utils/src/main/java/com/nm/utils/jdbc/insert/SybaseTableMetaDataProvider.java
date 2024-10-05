package com.nm.utils.jdbc.insert;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.metadata.GenericTableMetaDataProvider;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class SybaseTableMetaDataProvider extends GenericTableMetaDataProvider {

	protected SybaseTableMetaDataProvider(DatabaseMetaData databaseMetaData) throws SQLException {
		super(databaseMetaData);
	}

	@Override
	public boolean isGetGeneratedKeysSimulated() {
		return true;
	}

	@Override
	public String getSimpleQueryForGetGeneratedKey(String tableName, String keyColumnName) {
		return String.format("SELECT @@identity");
	}
}
