package com.nm.dictionnary;

import java.util.Collection;

import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntity;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryEntry;
import com.nm.dictionnary.daos.QueryBuilderDictionnaryValue;
import com.nm.dictionnary.dtos.DtoDictionnaryEntity;
import com.nm.dictionnary.dtos.DtoDictionnaryEntry;
import com.nm.dictionnary.dtos.DtoDictionnaryValue;
import com.nm.dictionnary.models.DictionnaryEntity;
import com.nm.dictionnary.models.DictionnaryException;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface SoaDictionnary {

	public Collection<DtoDictionnaryEntry> fetch(QueryBuilderDictionnaryEntry query, OptionsList otpions) throws DictionnaryException;

	public Collection<DtoDictionnaryValue> fetch(QueryBuilderDictionnaryValue query, OptionsList otpions) throws DictionnaryException;

	public Collection<DtoDictionnaryEntity> fetch(QueryBuilderDictionnaryEntity query, OptionsList otpions) throws DictionnaryException;

	public void delete(QueryBuilderDictionnaryEntity entry, OptionsList otpions) throws DictionnaryException;

	public void delete(DtoDictionnaryEntry entry, OptionsList otpions) throws DictionnaryException;

	public void saveOrUpdate(DtoDictionnaryEntry entry, OptionsList otpions) throws DictionnaryException;

	public DictionnaryEntity saveOrUpdate(DtoDictionnaryEntity entry, OptionsList otpions) throws DictionnaryException;
}
