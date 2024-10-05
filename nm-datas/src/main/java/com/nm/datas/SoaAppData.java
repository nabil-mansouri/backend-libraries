package com.nm.datas;

import java.util.Collection;

import com.nm.datas.daos.QueryBuilderAppData;
import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.models.AppData;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface SoaAppData {

	public Collection<AppDataDto> fetch(QueryBuilderAppData query, OptionsList options) throws AppDataException;

	public AppDataDto fetch(Long id, OptionsList options) throws AppDataException;

	public boolean remove(AppDataDto dto) throws AppDataException;

	public AppData save(AppDataDto dto, OptionsList options) throws AppDataException;

	public AppData saveUnique(AppDataDto dto, OptionsList options) throws AppDataException;

}
