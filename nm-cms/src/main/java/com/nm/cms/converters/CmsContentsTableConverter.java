package com.nm.cms.converters;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;

import com.nm.cms.constants.CmsPartType.CmsPartTypeDefault;
import com.nm.cms.dtos.CmsDtoContentsTable;
import com.nm.cms.models.CmsContents;
import com.nm.cms.models.CmsContentsSimple;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tables.ITableIterator;
import com.nm.utils.tables.ITableListenerDefault;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CmsContentsTableConverter extends DtoConverterDefault<CmsDtoContentsTable, CmsContentsSimple> {
	@Override
	public Collection<Class<? extends CmsContentsSimple>> managedEntity() {
		return ListUtils.all(CmsContents.class, CmsContentsSimple.class);
	}

	public CmsDtoContentsTable toDto(CmsDtoContentsTable dto, CmsContentsSimple entity, OptionsList options)
			throws DtoConvertException {
		JSONArray table = new JSONArray(entity.getData());
		for (int i = 0; i < table.length(); i++) {
			JSONArray row = table.getJSONArray(i);
			List<String> rowList = dto.createRow();
			for (int j = 0; j < row.length(); j++) {
				rowList.add(row.getString(j));
			}
		}
		return dto;
	}

	public CmsContentsSimple toEntity(CmsDtoContentsTable dto, OptionsList options) throws DtoConvertException {
		try {
			CmsContentsSimple contents = new CmsContentsSimple();
			if (dto.getId() != null) {
				contents = AbstractGenericDao.get(CmsContentsSimple.class).get(dto.getId());
			}
			contents.setType(CmsPartTypeDefault.Main);
			final JSONArray table = new JSONArray();
			new ITableIterator().iterate(dto, new ITableListenerDefault<String>() {
				JSONArray row;

				@Override
				public void onRow(List<String> cell, int numRow) {
					row = new JSONArray();
					table.put(row);
				}

				@Override
				public void onCell(String cell, int numRow, int numCol) {
					row.put(cell);
				}
			});
			contents.setData(table.toString());
			return contents;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
