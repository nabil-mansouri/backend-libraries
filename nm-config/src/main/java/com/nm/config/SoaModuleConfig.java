package com.nm.config;

import java.util.Collection;
import java.util.List;

import com.nm.config.constants.ModuleConfigKey;
import com.nm.config.dtos.ModuleConfigDto;
import com.nm.config.model.ModuleConfig;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public interface SoaModuleConfig {

	public ModuleConfigDto fetch(ModuleConfigKey key) throws NoDataFoundException;

	public Collection<ModuleConfigDto> fetch();

	public ModuleConfigDto saveOrUpdate(ModuleConfigDto config);

	public Collection<ModuleConfigDto> saveOrUpdate(Collection<ModuleConfigDto> config);

	public byte[] getBytes(ModuleConfigKey key);

	public double getDouble(ModuleConfigKey type);

	public int getInt(ModuleConfigKey type);

	public List<String> getList(ModuleConfigKey type);

	public long getLong(ModuleConfigKey type);

	public String getText(ModuleConfigKey type);

	public ModuleConfig setByte(ModuleConfigKey type, byte[] data);

	public ModuleConfig setDouble(ModuleConfigKey type, Double text);

	public ModuleConfig setInt(ModuleConfigKey type, Integer text);

	public ModuleConfig setList(ModuleConfigKey type, List<String> text);

	public ModuleConfig setLong(ModuleConfigKey type, Long text);

	public ModuleConfig setText(ModuleConfigKey type, String text);
}
