package com.nm.documents.sheets.poi;

import org.apache.poi.ss.usermodel.Cell;

import com.nm.app.utils.StringMoreUtils.ReplaceResult;
import com.nm.templates.parameters.TemplateParameterTranslation;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class VisitorTranslate extends VisitorDefault {
	private final TemplateParameterTranslation translate;

	public VisitorTranslate(TemplateParameterTranslation font) {
		this.translate = font;
	}

	@Override
	public void startVisitCell(Cell cell, int index) {
		super.startVisitCell(cell, index);
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			ReplaceResult res = translate.translate(cell.getStringCellValue());
			if (res.getCount() > 0) {
				cell.setCellValue(res.getResult());
			}
		}
	}

}
