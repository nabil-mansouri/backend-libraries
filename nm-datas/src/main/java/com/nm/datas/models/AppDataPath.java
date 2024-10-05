package com.nm.datas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;

import org.hibernate.annotations.Type;

import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.FolderType;
import com.nm.utils.json.EnumHibernateType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Entity
@Table(name = "nm_app_data_path", schema = "mod_data")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppDataPath extends AppData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "VARCHAR", length = 256)
	@Type(type = EnumHibernateType.EE)
	private FolderType folderType;

	public FolderType getFolderType() {
		return folderType;
	}

	public void setFolderType(FolderType folderType) {
		this.folderType = folderType;
	}

	@AssertFalse
	protected boolean isInFileSystem() {
		return this.getDestination().equals(AppDataDestinationDefault.Database);
	}
}
