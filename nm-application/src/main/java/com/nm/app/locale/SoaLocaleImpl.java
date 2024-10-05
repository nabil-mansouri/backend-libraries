package com.nm.app.locale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.config.SoaModuleConfig;
import com.nm.config.constants.ModuleConfigKeyDefault;
import com.nm.config.dtos.ModuleConfigDto;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaLocaleImpl implements SoaLocale {

	private SoaModuleConfig soaModuleConfig;

	public void setSoaModuleConfig(SoaModuleConfig soaModuleConfig) {
		this.soaModuleConfig = soaModuleConfig;
	}

	public OptionsList setLocaleIfEmpty(OptionsList options) {
		if (!options.hasLocale()) {
			try {
				options.setLocale(getDefaultLocale().getCode());
			} catch (NoDataFoundException e) {
				e.printStackTrace();
			}
		}
		return options;
	}

	private ObjectMapper mapper = new ObjectMapper();

	public Collection<LocaleFormDto> getSelectedLocales() {
		try {
			ModuleConfigDto config = soaModuleConfig.fetch(ModuleConfigKeyDefault.Langs);
			Collection<LocaleFormDto> lists = mapper.readValue(config.getPayload(),
					new TypeReference<List<LocaleFormDto>>() {
					});
			for (LocaleFormDto l : lists) {
				l.setSelected(true);
			}
			return lists;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<LocaleFormDto>();
	}

	public LocaleFormDto getDefaultLocale() throws NoDataFoundException {
		try {
			ModuleConfigDto config = soaModuleConfig.fetch(ModuleConfigKeyDefault.DefaultLang);
			LocaleFormDto obj = mapper.readValue(config.getPayload(), LocaleFormDto.class);
			obj.setSelected(true);
			return obj;
		} catch (Exception e) {
			throw new NoDataFoundException("No default lang", e);
		}
	}

	public void setSelectedLocales(Collection<LocaleFormDto> form) {
		try {
			soaModuleConfig.setText(ModuleConfigKeyDefault.Langs, mapper.writeValueAsString(form));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void setDefaultLocale(LocaleFormDto form) {
		try {
			soaModuleConfig.setText(ModuleConfigKeyDefault.DefaultLang, mapper.writeValueAsString(form));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
