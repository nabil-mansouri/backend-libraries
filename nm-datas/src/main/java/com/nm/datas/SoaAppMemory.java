package com.nm.datas;

import java.util.Date;
import java.util.List;

import com.nm.datas.dtos.MemoryKeyDto;
import com.nm.datas.models.AppMemory;
import com.nm.utils.hibernate.NotFoundException;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface SoaAppMemory {
	public byte[] getByte(MemoryKeyDto key) throws NotFoundException;

	public Date getDate(MemoryKeyDto key, Date defaut);

	public Date getDate(MemoryKeyDto key) throws NotFoundException;

	public double getDouble(MemoryKeyDto key) throws NotFoundException;

	public int getInt(MemoryKeyDto key) throws NotFoundException;

	public List<String> getList(MemoryKeyDto key) throws NotFoundException;

	public long getLong(MemoryKeyDto key) throws NotFoundException;

	public String getText(MemoryKeyDto key) throws NotFoundException;

	public AppMemory setByte(MemoryKeyDto key, byte[] data);

	public AppMemory setDate(MemoryKeyDto key, Date data);

	public AppMemory setDouble(MemoryKeyDto key, Double text);

	public AppMemory setInt(MemoryKeyDto key, Integer text);

	public AppMemory setList(MemoryKeyDto key, List<String> text);

	public AppMemory setLong(MemoryKeyDto key, Long text);

	public AppMemory setText(MemoryKeyDto key, String text);
}
