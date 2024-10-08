package com.nm.utils.jdbc.update;

import java.util.Arrays;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class SimpleJdbcUpdate extends AbstractJdbcUpdate implements SimpleJdbcUpdateOperations {

	/**
	 * Constructor that takes one parameter with the JDBC DataSource to use when
	 * creating the JdbcTemplate.
	 * 
	 * @param dataSource
	 *            the <code>DataSource</code> to use
	 * @see org.springframework.jdbc.core.JdbcTemplate#setDataSource
	 */
	public SimpleJdbcUpdate(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Alternative Constructor that takes one parameter with the JdbcTemplate to
	 * be used.
	 * 
	 * @param jdbcTemplate
	 *            the <code>JdbcTemplate</code> to use
	 * @see org.springframework.jdbc.core.JdbcTemplate#setDataSource
	 */
	public SimpleJdbcUpdate(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public SimpleJdbcUpdate withTableName(String tableName) {
		setTableName(tableName);
		return this;
	}

	public SimpleJdbcUpdate withSchemaName(String schemaName) {
		setSchemaName(schemaName);
		return this;
	}

	public SimpleJdbcUpdate withCatalogName(String catalogName) {
		setCatalogName(catalogName);
		return this;
	}

	public SimpleJdbcUpdate updatingColumns(String... columnNames) {
		setDeclaredUpdatingColumns(Arrays.asList(columnNames));
		return this;
	}

	public SimpleJdbcUpdate restrictingColumns(String... columnNames) {
		setRestrictingColumns(Arrays.asList(columnNames));
		return this;
	}

	public SimpleJdbcUpdate restrictingColumns(Map<String, Operator> columnsToOps) {
		setRestrictingColumns(columnsToOps);
		return this;
	}

	public SimpleJdbcUpdateOperations withoutTableColumnMetaDataAccess() {
		setAccessTableColumnMetaData(false);
		return this;
	}

	public SimpleJdbcUpdateOperations includeSynonymsForTableColumnMetaData() {
		setOverrideIncludeSynonymsDefault(true);
		return this;
	}

	public SimpleJdbcUpdateOperations useNativeJdbcExtractorForMetaData(NativeJdbcExtractor nativeJdbcExtractor) {
		setNativeJdbcExtractor(nativeJdbcExtractor);
		return this;
	}

	public int execute(Map<String, Object> updatingValues, Map<String, Object> restrictingValues) {
		return doExecute(updatingValues, restrictingValues);
	}

	public int execute(SqlParameterSource updatingValues, SqlParameterSource restrictingValues) {
		return doExecute(updatingValues, restrictingValues);
	}

}