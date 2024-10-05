package com.nm.datas.processors;

import java.util.Collection;

import com.nm.datas.dtos.AppDataDto;
import com.nm.datas.models.AppData;
import com.nm.utils.ApplicationUtils;

import javassist.NotFoundException;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public abstract class AppDataProcessor {
	public static Collection<AppDataProcessor> processors;

	public abstract boolean accept(AppDataDto dto);

	public abstract boolean accept(AppData entity);

	public abstract AppData create(AppDataDto dto);

	public abstract void pushToDestination(AppData data, AppDataDto context) throws Exception;

	public abstract void pullFromDestination(AppData data, AppDataDto context) throws Exception;

	public abstract void removeFromDestination(AppData data) throws Exception;

	public abstract String toURL(AppData data) throws Exception;

	protected final String realName(AppData data) {
		return String.format("%s-%s", data.getCreatedAt().getTime(), data.getFullName());
	}

	public static Collection<AppDataProcessor> getProcessors() {
		if (processors == null) {
			processors = ApplicationUtils.getBeansCollection(AppDataProcessor.class);
		}
		return processors;
	}

	public static AppDataProcessor get(AppDataDto dto) throws NotFoundException {
		for (AppDataProcessor p : getProcessors()) {
			if (p.accept(dto)) {
				return p;
			}
		}
		throw new NotFoundException("Could not found processor for dto :" + dto);
	}

	public static AppDataProcessor get(AppData entity) throws NotFoundException {
		for (AppDataProcessor p : getProcessors()) {
			if (p.accept(entity)) {
				return p;
			}
		}
		throw new NotFoundException("Could not found processor for entity :" + entity);
	}
}
