package com.nm.app.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.config.SoaModuleConfig;
import com.nm.config.constants.ModuleConfigKeyDefault;
import com.nm.config.dtos.ModuleConfigDto;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaDeviseImpl implements SoaDevise {

	private SoaModuleConfig soaModuleConfig;
	private ObjectMapper mapper = new ObjectMapper();

	public void setSoaModuleConfig(SoaModuleConfig soaModuleConfig) {
		this.soaModuleConfig = soaModuleConfig;
	}

	public CurrencyDto getDefault() throws NoDataFoundException {
		try {
			ModuleConfigDto config = soaModuleConfig.fetch(ModuleConfigKeyDefault.DefaultDevise);
			CurrencyDto obj = mapper.readValue(config.getPayload(), CurrencyDto.class);
			obj.setSelected(true);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoDataFoundException("No default currency", e);
		}
	}

	public void setDefault(CurrencyDto form) {
		try {
			soaModuleConfig.setText(ModuleConfigKeyDefault.DefaultDevise, mapper.writeValueAsString(form));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
