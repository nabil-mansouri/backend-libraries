package com.nm.datas.dtos;

import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.constants.AppDataDestination;
import com.nm.datas.constants.FolderType;
import com.nm.utils.dtos.Dto;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface AppDataDto extends Dto {

	Long getId();

	AppDataDto setId(Long id);

	FolderType getFolder();

	AppDataContentKind getKind();

	String getText();

	String getUrl();

	AppDataDto setText(String text);

	AppDataDestination getDestination();

	byte[] getFile();

	AppDataDto setFile(byte[] file);
}