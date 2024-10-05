package com.nm.app.async;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.springframework.transaction.annotation.Transactional;

import com.nm.app.log.SoaGeneralLogService;
import com.nm.datas.SoaAppMemory;
import com.nm.datas.dtos.MemoryKeyDto;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class AsyncExecutorHelper {
	private SoaAppMemory memoryService;
	private SoaGeneralLogService logService;

	public void setLogService(SoaGeneralLogService logService) {
		this.logService = logService;
	}

	public void setMemoryService(SoaAppMemory memoryService) {
		this.memoryService = memoryService;
	}

	@Transactional
	public String getText(String key) throws NotFoundException {
		return memoryService.getText(new MemoryKeyDto(this, key));
	}

	@Transactional
	public int getInt(String key) throws NotFoundException {
		return memoryService.getInt(new MemoryKeyDto(this, key));
	}

	@Transactional
	public void saveMemory(String key, File output) {
		memoryService.setText(new MemoryKeyDto(this, key), output.getAbsolutePath());
	}

	@Transactional
	public void saveMemory(String key, byte[] output) {
		memoryService.setByte(new MemoryKeyDto(this, key), output);
	}

	@Transactional
	public void saveMemory(String key, ByteArrayOutputStream output) {
		memoryService.setByte(new MemoryKeyDto(this, key), output.toByteArray());
	}

	@Transactional
	public void logError(Exception e) {
		logService.pushMessage(e);
	}

	@Transactional
	public void saveMemory(String key, Exception e) {
		memoryService.setInt(new MemoryKeyDto(this, key), -1);
		logService.pushMessage(e);
	}

}
