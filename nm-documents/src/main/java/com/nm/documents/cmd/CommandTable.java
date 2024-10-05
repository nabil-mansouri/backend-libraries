package com.nm.documents.cmd;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.nm.documents.StyleDirection;
import com.nm.documents.TableModel;
import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandTable extends ICommand {

	public CommandTable(CommandParserResult parsed) {
		super(parsed);
	}

	public TableModel getModel(CommandChainArguments args) throws Exception {
		String src = null;
		if (parsed.containsKey("table")) {
			src = parsed.getFirst("table");
		} else if (parsed.containsKey("src")) {
			src = parsed.getFirst("src");
		}
		if(src.equals("tableWithoutAge")){
			System.out.println();
		}
		Assert.notNull(src);
		//
		TableModel m = args.getTableModel(src);
		Assert.notNull(m);
		if (parsed.containsKey("break")) {
			m.setBreakPage(BooleanUtils.toBoolean(parsed.getFirst("break")));
		}
		if (parsed.containsKey("full")) {
			m.setFullWidth(BooleanUtils.toBoolean(parsed.getFirst("full")));
		}
		if (parsed.containsKey("size")) {
			m.setFontSize(Integer.valueOf(parsed.getFirst("size")));
		}
		if (parsed.containsKey("rowsize")) {
			m.setRowSize(Integer.valueOf(parsed.getFirst("rowsize")));
		}
		if (parsed.containsKey("style")) {
			m.setStyle(StringUtils.trim(parsed.getFirst("style", "")));
		}
		if (parsed.containsKey("insert")) {
			m.setInsertMode(BooleanUtils.toBoolean(parsed.getFirst("insert")));
		} else {
			// AVOID reused beans with true
			m.setInsertMode(false);
		}
		if (parsed.containsKey("copystyle")) {
			String val = parsed.getFirst("copystyle");
			if (StringUtils.equalsIgnoreCase(val, "H")) {
				m.setCopyStyle(StyleDirection.Horizontal);
			} else if (StringUtils.equalsIgnoreCase(val, "V")) {
				m.setCopyStyle(StyleDirection.Vertical);
			} else {
				m.setCopyStyle(StyleDirection.All);
			}
		}
		return m;
	}

}
