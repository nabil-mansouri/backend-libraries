package com.nm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.nm.config.constants.ModuleConfigKey;
import com.nm.config.dao.DaoModuleConfig;
import com.nm.config.dtos.ModuleConfigDto;
import com.nm.config.model.ModuleConfig;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaModuleConfigImpl implements SoaModuleConfig {

	public static final String SEP = "/";
	private DaoModuleConfig daoModuleConfig;

	public void setDaoModuleConfig(DaoModuleConfig daoModuleConfig) {
		this.daoModuleConfig = daoModuleConfig;
	}

	protected String getDefault(ModuleConfigKey key) {
		if (key.isFile()) {
			if (key.isFile()) {
				if (key.isPlainText()) {
					InputStream file = key.getClass().getResourceAsStream(key.getDefaut());
					try {
						return (IOUtils.toString(file));
					} catch (IOException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					InputStream file = key.getClass().getResourceAsStream(key.getDefaut());
					try {
						return (Base64.getEncoder().encodeToString(IOUtils.toByteArray(file)));
					} catch (IOException e) {
						throw new IllegalArgumentException(e);
					}
				}
			} else {
				return key.getDefaut();
			}
		} else {
			return key.getDefaut();
		}
	}

	protected String getModule(ModuleConfigKey key) {
		return key.getClass().getName();
	}

	protected ModuleConfig get(ModuleConfigKey key) {
		try {
			ModuleConfig configs = daoModuleConfig.findByKey(getModule(key), key);
			if (configs == null) {
				ModuleConfig config = new ModuleConfig();
				config.setKey(key);
				config.setPayload(getDefault(key));
				config.setModule(getModule(key));
				return config;
			} else {
				return configs;
			}
		} catch (NoDataFoundException e) {
			ModuleConfig config = new ModuleConfig();
			config.setKey(key);
			config.setModule(getModule(key));
			config.setPayload(getDefault(key));
			return config;
		}
	}

	protected String getOrDefault(ModuleConfigKey type) {
		ModuleConfig config = get(type);
		return config.getPayload();
	}

	protected ModuleConfig set(ModuleConfigKey type, String value) {
		ModuleConfig config = get(type);
		if (config.getId() == null) {
			config = new ModuleConfig();
			config.setKey(type);
			config.setModule(getModule(type));
			config.setPayload(value);
			daoModuleConfig.saveOrUpdate(config);
		} else {
			config.setPayload(value);
			daoModuleConfig.saveOrUpdate(config);
		}
		return config;
	}

	@Transactional(readOnly = true)
	public String getText(ModuleConfigKey type) {
		return getOrDefault(type);
	}

	@Transactional
	public ModuleConfig setText(ModuleConfigKey type, String text) {
		return set(type, text);
	}

	@Transactional(readOnly = true)
	public List<String> getList(ModuleConfigKey type) {
		String config = getOrDefault(type);
		if (Strings.isNullOrEmpty(config)) {
			return new ArrayList<String>();
		} else {
			return new ArrayList<String>(Arrays.asList(config.split(SEP)));
		}
	}

	@Transactional
	public ModuleConfig setList(ModuleConfigKey type, List<String> text) {
		String value = StringUtils.join(text, SEP);
		return set(type, value);
	}

	@Transactional
	public ModuleConfig setInt(ModuleConfigKey type, Integer text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(type, val);
	}

	@Transactional(readOnly = true)
	public int getInt(ModuleConfigKey type) {
		int val = 0;
		try {
			val = Integer.valueOf(getOrDefault(type));
		} catch (Exception e) {

		}
		return val;
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public byte[] getBytes(ModuleConfigKey type) {
		String val = getOrDefault(type);
		return Base64.getDecoder().decode(val);
	}

	public double getDouble(ModuleConfigKey type) {
		double val = 0d;
		try {
			val = Double.valueOf(getOrDefault(type));
		} catch (Exception e) {

		}
		return val;
	}

	public long getLong(ModuleConfigKey type) {
		long val = 0;
		try {
			val = Long.valueOf(getOrDefault(type));
		} catch (Exception e) {

		}
		return val;
	}

	public ModuleConfig setByte(ModuleConfigKey type, byte[] data) {
		return set(type, Base64.getEncoder().encodeToString(data));
	}

	public ModuleConfig setDouble(ModuleConfigKey type, Double text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(type, val);
	}

	public ModuleConfig setLong(ModuleConfigKey type, Long text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(type, val);
	}

	public Collection<ModuleConfigDto> fetch() {
		Collection<ModuleConfig> configs = daoModuleConfig.findAll();
		Collection<ModuleConfigDto> collConfig = new ArrayList<ModuleConfigDto>();
		for (ModuleConfig config : configs) {
			collConfig.add(build(config));
		}
		return collConfig;
	};

	public Collection<ModuleConfigDto> saveOrUpdate(Collection<ModuleConfigDto> configColl) {
		Collection<ModuleConfigDto> result = new ArrayList<ModuleConfigDto>();
		//
		for (ModuleConfigDto bean : configColl) {
			try {
				ModuleConfig config = daoModuleConfig.findByKey(getModule(bean.getKey()), bean.getKey());
				config.setPayload(bean.getPayload());
				daoModuleConfig.saveOrUpdate(config);
				result.add(build(config));
			} catch (NoDataFoundException e) {
				ModuleConfig config = new ModuleConfig();
				config.setKey(bean.getKey());
				config.setPayload(bean.getPayload());
				daoModuleConfig.saveOrUpdate(config);
				result.add(build(config));
			}
		}
		return result;
	}

	public ModuleConfigDto saveOrUpdate(ModuleConfigDto bean) {
		try {
			ModuleConfig config = daoModuleConfig.findByKey(getModule(bean.getKey()), bean.getKey());
			config.setPayload(bean.getPayload());
			daoModuleConfig.saveOrUpdate(config);
			return (build(config));
		} catch (NoDataFoundException e) {
			ModuleConfig config = new ModuleConfig();
			config.setKey(bean.getKey());
			config.setPayload(bean.getPayload());
			daoModuleConfig.saveOrUpdate(config);
			return (build(config));
		}
	}

	protected ModuleConfigDto build(ModuleConfig config) {
		ModuleConfigDto bean = new ModuleConfigDto();
		bean.setCreated(config.getCreatedAt());
		bean.setPayload(config.getPayload());
		bean.setUpdated(config.getUpdatedAt());
		bean.setKey(config.getKey());
		return bean;
	}

	public ModuleConfigDto fetch(ModuleConfigKey key) throws NoDataFoundException {
		return build(daoModuleConfig.findByKey(getModule(key), key));
	}

}
