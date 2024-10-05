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
import com.nm.config.constants.AppConfigKey;
import com.nm.config.constants.AppConfigKeyDefault;
import com.nm.config.dao.DaoAppConfig;
import com.nm.config.dtos.ConfigDto;
import com.nm.config.model.AppConfig;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaAppConfigImpl implements SoaAppConfig {

	public static final String SEP = "/";
	private DaoAppConfig daoAppConfig;

	public void setDaoAppConfig(DaoAppConfig daoAppConfig) {
		this.daoAppConfig = daoAppConfig;
	}

	protected String getDefault(AppConfigKey key) {
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
	}

	protected AppConfig get(AppConfigKey key) {
		try {
			AppConfig configs = daoAppConfig.findByKey(key);
			if (configs == null) {
				AppConfig config = new AppConfig();
				config.setKey(key);
				config.setPayload(getDefault(key));
				return config;
			} else {
				return configs;
			}
		} catch (NoDataFoundException e) {
			AppConfig config = new AppConfig();
			config.setKey(key);
			config.setPayload(getDefault(key));
			return config;
		}
	}

	protected String getOrDefault(AppConfigKey type) {
		AppConfig config = get(type);
		return config.getPayload();
	}

	protected AppConfig set(AppConfigKey type, String value) {
		AppConfig config = get(type);
		if (config.getId() == null) {
			config = new AppConfig();
			config.setKey(type);
			config.setPayload(value);
			daoAppConfig.saveOrUpdate(config);
		} else {
			config.setPayload(value);
			daoAppConfig.saveOrUpdate(config);
		}
		return config;
	}

	@Transactional(readOnly = true)
	public String getText(AppConfigKey type) {
		return getOrDefault(type);
	}

	@Transactional
	public AppConfig setText(AppConfigKey type, String text) {
		return set(type, text);
	}

	@Transactional(readOnly = true)
	public List<String> getList(AppConfigKey type) {
		String config = getOrDefault(type);
		if (Strings.isNullOrEmpty(config)) {
			return new ArrayList<String>();
		} else {
			return new ArrayList<String>(Arrays.asList(config.split(SEP)));
		}
	}

	@Transactional
	public AppConfig setList(AppConfigKey type, List<String> text) {
		String value = StringUtils.join(text, SEP);
		return set(type, value);
	}

	@Transactional
	public AppConfig setInt(AppConfigKey type, Integer text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(type, val);
	}

	@Transactional(readOnly = true)
	public int getInt(AppConfigKey type) {
		int val = 0;
		try {
			val = Integer.valueOf(getOrDefault(type));
		} catch (Exception e) {

		}
		return val;
	}

	protected ConfigDto convert(AppConfig config) {
		ConfigDto bean = new ConfigDto();
		bean.setId(config.getId());
		bean.setType(config.getKey());
		bean.setValue(config.getPayload());
		return bean;
	}

	@Transactional(readOnly = true)
	public Collection<ConfigDto> fetch() {
		Collection<ConfigDto> beans = new ArrayList<ConfigDto>();
		for (AppConfigKey config : AppConfigKeyDefault.values()) {
			beans.add(convert(get(config)));
		}
		return beans;
	}

	@Transactional()
	public Collection<AppConfig> save(Collection<ConfigDto> bean) {
		List<AppConfig> saved = new ArrayList<AppConfig>();
		for (ConfigDto b : bean) {
			AppConfig config = convert(b);
			daoAppConfig.saveOrUpdate(config);
			saved.add(config);
		}
		return saved;
	}

	@Transactional(readOnly = true)
	public AppConfig convert(ConfigDto bean) {
		AppConfig config = get(bean.getType());
		config.setPayload(bean.getValue());
		return config;
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public byte[] getBytes(AppConfigKey type) {
		String val = getOrDefault(type);
		return Base64.getDecoder().decode(val);
	}

	public double getDouble(AppConfigKey type) {
		double val = 0d;
		try {
			val = Double.valueOf(getOrDefault(type));
		} catch (Exception e) {

		}
		return val;
	}

	public long getLong(AppConfigKey type) {
		long val = 0;
		try {
			val = Long.valueOf(getOrDefault(type));
		} catch (Exception e) {

		}
		return val;
	}

	public AppConfig setByte(AppConfigKey type, byte[] data) {
		return set(type, Base64.getEncoder().encodeToString(data));
	}

	public AppConfig setDouble(AppConfigKey type, Double text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(type, val);
	}

	public AppConfig setLong(AppConfigKey type, Long text) {
		String val = "0";
		try {
			val = text.toString();
		} catch (Exception e) {

		}
		return set(type, val);
	}
}
