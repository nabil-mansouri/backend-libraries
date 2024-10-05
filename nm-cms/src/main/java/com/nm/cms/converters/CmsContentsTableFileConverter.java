package com.nm.cms.converters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nm.cms.constants.CmsTableHeader;
import com.nm.cms.dtos.CmsDtoContentsFile;
import com.nm.cms.dtos.CmsDtoContentsIndexedTable;
import com.nm.cms.dtos.CmsDtoContentsIndexedTableRow;
import com.nm.cms.dtos.CmsDtoContentsIndexedTableRow.RowOperation;
import com.nm.cms.dtos.CmsDtoContentsPrimitive;
import com.nm.cms.models.CmsContents;
import com.nm.cms.models.CmsContentsIndexedTable;
import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.constants.AppDataMediaType;
import com.nm.utils.ByteUtils;
import com.nm.utils.ListUtils;
import com.nm.utils.dates.DateUtilsExt;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.tables.ITableIterator;
import com.nm.utils.tables.ITableListenerDefault;

/***
 * 
 * @author nabilmansouri
 *
 */
public class CmsContentsTableFileConverter extends DtoConverterDefault<CmsDtoContentsFile, CmsContentsIndexedTable> {
	public static interface CmsContentsTableFileConverterConfig extends Serializable {
		public CmsDtoContentsPrimitive value(int rowNum, int colNum, String value, CmsTableHeader header);
	}

	public static class CmsContentsTableFileConverterCsvDefault implements CmsContentsTableFileConverterConfig {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CmsDtoContentsPrimitive value(int rowNum, int colNum, String value, CmsTableHeader header) {
			return new CmsDtoContentsPrimitive().setDataString(value);
		}
	}

	public Collection<Class<? extends CmsContentsIndexedTable>> managedEntity() {
		return ListUtils.all(CmsContentsIndexedTable.class, CmsContents.class);
	}

	public CmsDtoContentsFile toDto(final CmsDtoContentsFile dto, CmsContentsIndexedTable entity, OptionsList options)
			throws DtoConvertException {
		try {
			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			CmsDtoContentsIndexedTable dtoTable = registry().search(CmsDtoContentsIndexedTable.class, entity)
					.toDto(entity, options);
			new ITableIterator().iterate(dtoTable, new ITableListenerDefault<CmsDtoContentsPrimitive>() {
				List<String> row;

				@Override
				public void onRow(List<CmsDtoContentsPrimitive> cell, int numRow) {
					row = Lists.newArrayList();
				}

				@Override
				public void onCell(CmsDtoContentsPrimitive cell, int numRow, int numCol) {
					String value = "";
					if (cell.getDataDate() != null) {
						value = DateUtilsExt.formatDateTime(cell.getDataDate());
					} else if (cell.getDataDouble() != null) {
						value = cell.getDataDouble().toString();
					} else if (cell.getDataInt() != null) {
						value = cell.getDataInt().toString();
					} else if (cell.getDataString() != null) {
						value = cell.getDataInt().toString();
					}
					row.add(value);
				}

				@Override
				public void onEndRow(List<CmsDtoContentsPrimitive> cell, int numRow) {
					try {
						IOUtils.write(ByteUtils.toBytes(
								StringUtils.join(row, dto.getSeparator()) + dto.getSeparator() + "\n"), output);
					} catch (IOException e) {
						throw new IllegalArgumentException(e);
					}
				}
			});
			dto.getAppData().setFile(output.toByteArray());
			dto.getAppData().setKind(AppDataContentKind.Text);
			dto.getAppData().withGroup(AppDataMediaType.Csv);
			dto.getHeaders().clear();
			dto.getHeaders().addAll(entity.getHeaders());
			return dto;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	@Override
	public CmsContentsIndexedTable toEntity(final CmsDtoContentsFile dto, OptionsList options)
			throws DtoConvertException {
		try {
			final CmsContentsTableFileConverterConfig config = options.getOverrides(
					CmsContentsTableFileConverterConfig.class, new CmsContentsTableFileConverterCsvDefault());
			String text = null;
			if (dto.getAppData().getFile() != null) {
				text = ByteUtils.toStrings(dto.getAppData().getFile());
			} else if (dto.getAppData().getText() != null) {
				text = dto.getAppData().getText();
			}
			List<List<String>> all = Lists.newArrayList();
			int cpt = 0;
			for (String line : StringUtils.split(text, "\n")) {
				if (!dto.isHeader() || cpt > 0) {
					all.add(Arrays
							.asList(StringUtils.splitPreserveAllTokens(StringUtils.trim(line), dto.getSeparator())));
				}
				cpt++;
			}
			//
			final CmsDtoContentsIndexedTable table = new CmsDtoContentsIndexedTable();
			table.setContentId(dto.getContentId());
			new ITableIterator().iterate(all, new ITableListenerDefault<String>() {
				CmsDtoContentsIndexedTableRow row;

				@Override
				public void onRow(List<String> cell, int numRow) {
					row = table.createRowDto();
					row.setRowNum(numRow);
					row.setOperation(RowOperation.Created);
				}

				@Override
				public void onCell(String cell, int numRow, int numCol) {
					CmsTableHeader header = null;
					if (numCol < dto.getHeaders().size()) {
						header = dto.getHeaders().get(numCol);
					}
					//
					CmsDtoContentsPrimitive cellRow = new CmsDtoContentsPrimitive();
					if (Strings.isNullOrEmpty(cell)) {
						cellRow.setDataString("");
					} else {
						cellRow = config.value(numRow, numCol, cell, header);
						cellRow.setHeader(header);
					}
					cellRow.setColumn(numCol);
					cellRow.setRow(numRow);
					row.getCells().add(cellRow);
				}
			});
			CmsContentsIndexedTable indexTable = registry().search(table, CmsContentsIndexedTable.class).toEntity(table,
					options);
			indexTable.getHeaders().clear();
			indexTable.getHeaders().addAll(dto.getHeaders());
			return indexTable;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}
}
