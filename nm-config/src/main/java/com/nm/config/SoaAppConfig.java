package com.nm.config;

import java.util.Collection;
import java.util.List;

import com.nm.config.constants.AppConfigKey;
import com.nm.config.dtos.ConfigDto;
import com.nm.config.model.AppConfig;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaAppConfig {

	public AppConfig convert(ConfigDto bean);

	public Collection<ConfigDto> fetch();

	public byte[] getBytes(AppConfigKey key);

	public double getDouble(AppConfigKey type);

	public int getInt(AppConfigKey type);

	public List<String> getList(AppConfigKey type);

	public long getLong(AppConfigKey type);

	public String getText(AppConfigKey type);

	public Collection<AppConfig> save(Collection<ConfigDto> bean);

	public AppConfig setByte(AppConfigKey type, byte[] data);

	public AppConfig setDouble(AppConfigKey type, Double text);

	public AppConfig setInt(AppConfigKey type, Integer text);

	public AppConfig setList(AppConfigKey type, List<String> text);

	public AppConfig setLong(AppConfigKey type, Long text);

	public AppConfig setText(AppConfigKey type, String text);

}
