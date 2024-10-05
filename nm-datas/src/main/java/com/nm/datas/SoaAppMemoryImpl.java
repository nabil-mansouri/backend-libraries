package com.nm.datas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.nm.datas.daos.DaoAppMemory;
import com.nm.datas.dtos.MemoryKeyDto;
import com.nm.datas.models.AppMemory;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class SoaAppMemoryImpl implements SoaAppMemory {
	//
	protected Log log = LogFactory.getLog(getClass());
	// DAO
	private DaoAppMemory appMemoryDao;

	public final static String SEP = "/";

	public void setAppMemoryDao(DaoAppMemory appMemoryDao) {
		this.appMemoryDao = appMemoryDao;
	}

	protected AppMemory get(MemoryKeyDto key) {
		Collection<AppMemory> configs = appMemoryDao.findByKey(key);
		if (configs.isEmpty()) {
			AppMemory config = new AppMemory();
			config.setKey(key.getKey());
			config.setCategory(key.getCategory());
			config.setScope(key.getScope());
			return config;
		} else {
			return configs.iterator().next();
		}
	}

	public byte[] getByte(MemoryKeyDto key) throws NotFoundException {
		String val = getOrThrow(key);
		return Base64.getDecoder().decode(val);
	}

	public Date getDate(MemoryKeyDto key, Date defaut) {
		try {
			return getDate(key);
		} catch (NotFoundException e) {
		}
		return defaut;
	}

	public Date getDate(MemoryKeyDto key) throws NotFoundException {
		String val = getOrThrow(key);
		Long date = Long.valueOf(val);
		return new Date(date);
	}

	public double getDouble(MemoryKeyDto key) throws NotFoundException {
		double val = 0d;
		try {
			val = Double.valueOf(getOrThrow(key));
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
		return val;
	}

	@Transactional(readOnly = true)
	public int getInt(MemoryKeyDto key) throws NotFoundException {
		int val = 0;
		try {
			val = Integer.valueOf(getOrThrow(key));
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
		return val;
	}

	@Transactional(readOnly = true)
	public List<String> getList(MemoryKeyDto key) throws NotFoundException {
		String config = getOrThrow(key);
		if (Strings.isNullOrEmpty(config)) {
			return new ArrayList<String>();
		} else {
			return new ArrayList<String>(Arrays.asList(config.split(SEP)));
		}
	}

	public long getLong(MemoryKeyDto key) throws NotFoundException {
		long val = 0;
		try {
			val = Long.valueOf(getOrThrow(key));
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
		return val;
	}

	protected String getOrThrow(MemoryKeyDto key) throws NotFoundException {
		AppMemory memory = get(key);
		if (Strings.isNullOrEmpty(memory.getValue())) {
			throw new NotFoundException("Could not found key: " + key);
		}
		return memory.getValue();
	}

	@Transactional(readOnly = true)
	public String getText(MemoryKeyDto key) throws NotFoundException {
		return getOrThrow(key);
	}

	protected AppMemory set(MemoryKeyDto key, String value) {
		AppMemory config = get(key);
		if (config.getId() == null) {
			config.setValue(value);
			appMemoryDao.saveOrUpdate(config);
		} else {
			config.setValue(value);
			appMemoryDao.saveOrUpdate(config);
		}
		return config;
	}

	public AppMemory setByte(MemoryKeyDto key, byte[] data) {
		return set(key, Base64.getEncoder().encodeToString(data));
	}

	public AppMemory setDate(MemoryKeyDto key, Date data) {
		return set(key, Long.valueOf(data.getTime()).toString());
	}

	public AppMemory setDouble(MemoryKeyDto key, Double text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(key, val);
	}

	@Transactional
	public AppMemory setInt(MemoryKeyDto key, Integer text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(key, val);
	}

	@Transactional
	public AppMemory setList(MemoryKeyDto key, List<String> text) {
		String value = StringUtils.join(text, SEP);
		return set(key, value);
	}

	public AppMemory setLong(MemoryKeyDto key, Long text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(key, val);
	}

	@Transactional
	public AppMemory setText(MemoryKeyDto key, String text) {
		return set(key, text);
	}

}
