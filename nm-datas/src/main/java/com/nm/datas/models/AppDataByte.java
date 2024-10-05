package com.nm.datas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_app_data_byte", schema = "mod_data")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppDataByte extends AppData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Postgres v9 => bytea Postgres v9- => oid
	 */
	@Lob
	@Column(nullable = false)
	private byte[] file;

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] data) {
		this.file = data;
	}

	@AssertTrue
	protected boolean isInDBByDefault() {
		return this.getDestination().equals(AppDataDestinationDefault.Database);
	}

	@AssertTrue
	protected boolean isKind() {
		return this.getKind().equals(AppDataContentKind.Byte);
	}
}
