package com.nm.sheets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.nm.documents.TableModel;
import com.nm.documents.TableModelCell;
import com.nm.documents.TableModelRow;
import com.nm.documents.args.CommandChainArguments;
import com.nm.documents.sheets.poi.ExcelProcessor;
import com.nm.templates.parameters.TemplateParameterTranslation;

/**
 * 
 * @author Nabil
 * 
 */
// @RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
// @ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestSheet {

	@Before
	public void globalSetUp() {
	}

	@Test
	public void testShouldBuildTable() throws Exception {
		File input = new File(getClass().getResource("test.xlsx").getFile());
		File output = new File("output/test.xlsx");
		//
		CommandChainArguments args = CommandChainArguments.build();
		TableModel model = new TableModel();
		for (int i = 0; i < 5; i++) {
			model.addColumnName(new TableModelCell("COL" + i).setGridspan(4));
		}
		for (int i = 0; i < 5; i++) {
			List<TableModelCell> row = model.createRow();
			for (int j = 0; j < 5; j++) {
				row.add(new TableModelCell("CELL" + i + j));
			}
		}
		TableModelRow row = new TableModelRow();
		for (int i = 0; i < 5; i++) {
			row.add(new TableModelCell("ROW" + i));
		}
		args.putTable("table", model);
		args.putVars("var", "YEAH");
		args.putTableModelRow("row", row);
		//
		TemplateParameterTranslation trans = new TemplateParameterTranslation();
		trans.put("COL0", "BOUAH");
		args.setTranslation(trans);
		//
		ExcelProcessor processor = new ExcelProcessor();
		processor.process(args, new FileInputStream(input), new FileOutputStream(output));
	}

	@Test
	public void testShouldBuildTableInsert() throws Exception {
		File input = new File(getClass().getResource("test.xlsx").getFile());
		File output = new File("output/test-insert.xlsx");
		//
		CommandChainArguments args = CommandChainArguments.build();
		TableModel model = new TableModel(); 
		for (int i = 0; i < 5; i++) {
			model.addColumnName("COL" + i);
		}
		for (int i = 0; i < 5; i++) {
			List<TableModelCell> row = model.createRow();
			for (int j = 0; j < 5; j++) {
				row.add(new TableModelCell("CELL" + i + j));
			}
		}
		TableModelRow row = new TableModelRow();
		for (int i = 0; i < 5; i++) {
			row.add(new TableModelCell("ROW" + i));
		}
		args.putTable("table", model);
		args.putVars("var", "YEAH");
		args.putTableModelRow("row", row);
		//
		TemplateParameterTranslation trans = new TemplateParameterTranslation();
		trans.put("COL0", "BOUAH");
		args.setTranslation(trans);
		//
		ExcelProcessor processor = new ExcelProcessor();
		processor.process(args, new FileInputStream(input), new FileOutputStream(output));
	}
}
