package com.nm.cms.converters;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.nm.cms.constants.CmsOptions;
import com.nm.cms.constants.CmsPartType.CmsPartTypeDefault;
import com.nm.cms.constants.CmsTableHeader;
import com.nm.cms.dtos.CmsDtoContentsIndexedTable;
import com.nm.cms.dtos.CmsDtoContentsIndexedTableRow;
import com.nm.cms.dtos.CmsDtoContentsIndexedTableRow.RowOperation;
import com.nm.cms.dtos.CmsDtoContentsPrimitive;
import com.nm.cms.models.CmsContents;
import com.nm.cms.models.CmsContentsIndexedTable;
import com.nm.cms.models.CmsContentsIndexedTableCell;
import com.nm.cms.models.CmsContentsIndexedTableRow;
import com.nm.utils.ListUtils;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverter;
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
public class CmsContentsIndexedTableConverter
		extends DtoConverterDefault<CmsDtoContentsIndexedTable, CmsContentsIndexedTable> {

	@Override
	public Collection<Class<? extends CmsContentsIndexedTable>> managedEntity() {
		return ListUtils.all(CmsContents.class, CmsContentsIndexedTable.class);
	}

	public CmsDtoContentsIndexedTable toDto(final CmsDtoContentsIndexedTable dto, final CmsContentsIndexedTable entity,
			final OptionsList options) throws DtoConvertException {
		try {
			dto.setId(entity.getId());
			final DtoConverter<CmsDtoContentsPrimitive, CmsContents> converter = registry()
					.search(CmsDtoContentsPrimitive.class, CmsContents.class);
			dto.getHeaders().clear();
			for (CmsTableHeader h : entity.getHeaders()) {
				dto.getHeaders().add(h);
			}
			new ITableIterator().iterate(entity, new ITableListenerDefault<CmsContentsIndexedTableCell>() {
				CmsDtoContentsIndexedTableRow currentRow = null;

				@Override
				public void onRow(List<CmsContentsIndexedTableCell> cell, int numRow) {
					currentRow = dto.createRowDto();
					currentRow.setRowNum(numRow);
					currentRow.setId(entity.getByIndex(numRow).getId());
				}

				@Override
				public void onCell(CmsContentsIndexedTableCell cell, int numRow, int numCol) {
					try {
						CmsDtoContentsPrimitive cellDto = converter.toDto(cell.getContent(), options);
						cellDto.setColumn(numCol);
						cellDto.setRow(numRow);
						if (numCol < entity.getHeaders().size()) {
							cellDto.setHeader(entity.getHeaders().get(numCol));
						}
						currentRow.getCells().add(cellDto);
					} catch (DtoConvertException e) {
						throw new IllegalArgumentException(e);
					}
				}
			});
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
		if (options.contains(CmsOptions.TablePrependCreateRow)) {
			// DO NOT ADD ROW IF SINGLE MODE
			if (!options.contains(CmsOptions.TableSignleRow) || dto.getRows().isEmpty()) {
				CmsDtoContentsIndexedTableRow first = dto.createRowDto(0);
				for (CmsTableHeader h : entity.getHeaders()) {
					first.createCell().setHeader(h);
				}
			}
		}
		return dto;
	}

	public CmsContentsIndexedTable toEntity(CmsDtoContentsIndexedTable dto, OptionsList options)
			throws DtoConvertException {
		try {
			final DtoConverter<CmsDtoContentsPrimitive, CmsContents> converter = registry()
					.search(CmsDtoContentsPrimitive.class, CmsContents.class);
			CmsContentsIndexedTable contents = new CmsContentsIndexedTable();
			if (dto.getId() != null) {
				contents = AbstractGenericDao.get(CmsContentsIndexedTable.class).get(dto.getId());
			}
			contents.setType(CmsPartTypeDefault.Main);
			//
			if (options.contains(CmsOptions.SaveHeaders)) {
				contents.getHeaders().clear();
				for (CmsTableHeader h : dto.getHeaders()) {
					contents.getHeaders().add(h);
				}
			}
			//
			switch (dto.getOperation()) {
			case Deleted: {
				for (CmsDtoContentsIndexedTableRow row : dto.getRows()) {
					row.setOperation(RowOperation.Deleted);
				}
				break;
			}
			case Updated: {
				for (CmsDtoContentsIndexedTableRow row : dto.getRows()) {
					if (row.getId() == null) {
						row.setOperation(RowOperation.Created);
					} else {
						row.setOperation(RowOperation.Updated);
					}
				}
				break;
			}
			case Void:
				break;
			}
			//
			Collection<CmsContentsIndexedTableRow> toDelete = Lists.newArrayList();
			for (CmsDtoContentsIndexedTableRow r : dto.getRows()) {
				// Do not save empty row if options is present
				if (!options.contains(CmsOptions.TableRemoveEmptyRow) || r.getId() != null) {
					switch (r.getOperation()) {
					case Created: {
						CmsContentsIndexedTableRow row = contents.createRowModel();
						createRow(converter, row, r, options);
						break;
					}
					case Deleted: {
						// DEFER DELETE (index)
						CmsContentsIndexedTableRow row = contents.getByIndex(r.getRowNum());
						row.clear();
						toDelete.add(row);
						break;
					}
					case Updated: {
						CmsContentsIndexedTableRow row = contents.getByIndex(r.getRowNum());
						row.clear();
						createRow(converter, row, r, options);
						break;
					}
					case Void:
						break;
					}
				}
			}
			//
			contents.getRows().removeAll(toDelete);
			return contents;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	private void createRow(DtoConverter<CmsDtoContentsPrimitive, CmsContents> converter, CmsContentsIndexedTableRow row,
			CmsDtoContentsIndexedTableRow dtoRow, OptionsList options) throws DtoConvertException {
		for (CmsDtoContentsPrimitive p : dtoRow.getCells()) {
			if (p.getHeader() != null) {
				p.setOptionnal(p.getHeader().optionnal());
			}
			CmsContentsIndexedTableCell cell = new CmsContentsIndexedTableCell();
			// AVOID OneToOne duplicate constrainte for primitive in cell
			p.setId(null);
			cell.setContent(converter.toEntity(p, options));
			row.add(cell);
		}
	}
}
