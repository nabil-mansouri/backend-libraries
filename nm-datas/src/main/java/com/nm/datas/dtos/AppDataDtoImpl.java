package com.nm.datas.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.constants.AppDataDestination;
import com.nm.datas.constants.AppDataDestination.AppDataDestinationDefault;
import com.nm.datas.constants.FolderType.FolderTypeDefault;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.datas.constants.FolderType;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@JsonAutoDetect
public class AppDataDtoImpl implements AppDataDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private int size;
	private byte[] file;
	private String text;
	private String name;
	private String url;
	private boolean isAbsURL;
	private String type = AppDataMediaType.Any.getMain();
	private FolderType folder = FolderTypeDefault.TempPrivate;
	private AppDataContentKind kind = AppDataContentKind.Byte;
	private AppDataDestination destination = AppDataDestinationDefault.Database;

	public AppDataDtoImpl() {

	}

	public AppDataDtoImpl(String url) {
		withUrl(url);
	}

	public boolean isAbsURL() {
		return isAbsURL;
	}

	public void setAbsURL(boolean isAbsURL) {
		this.isAbsURL = isAbsURL;
	}

	public FolderType getFolder() {
		return folder;
	}

	public void setFolder(FolderType folder) {
		this.folder = folder;
	}

	public String getUrl() {
		return url;
	}

	public AppDataDtoImpl setUrl(String url) {
		this.url = url;
		return this;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public AppDataContentKind getKind() {
		return kind;
	}

	public AppDataDtoImpl setKind(AppDataContentKind kind) {
		this.kind = kind;
		return this;
	}

	public String getText() {
		return text;
	}

	public AppDataDtoImpl setText(String text) {
		this.text = text;
		return this;
	}

	public AppDataDtoImpl(Long id) {
		setId(id);
	}

	public AppDataDestination getDestination() {
		return destination;
	}

	public AppDataDtoImpl setDestination(AppDataDestination destination) {
		this.destination = destination;
		return this;
	}

	public AppDataDtoImpl withGroup(AppDataMediaType group) {
		this.type = group.getMain();
		return this;
	}

	public String getName() {
		return name;
	}

	public AppDataDtoImpl setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public AppDataDtoImpl setType(String type) {
		this.type = type;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public AppDataDto setId(Long id) {
		this.id = id;
		return this;
	}

	public byte[] getFile() {
		return file;
	}

	public AppDataDtoImpl setFile(byte[] file) {
		this.file = file;
		return this;
	}

	public AppDataDtoImpl withFile(byte[] file) {
		this.setFile(file);
		this.setKind(AppDataContentKind.Byte);
		return this;
	}

	public AppDataDtoImpl withUrl(String e) {
		setUrl(e);
		setKind(AppDataContentKind.Byte);
		setDestination(AppDataDestinationDefault.URL);
		return this;
	}

}
