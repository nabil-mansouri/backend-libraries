package com.nm.datas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_app_data_text", schema = "mod_data")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppDataText extends AppData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
