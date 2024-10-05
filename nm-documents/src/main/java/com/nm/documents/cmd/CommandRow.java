package com.nm.documents.cmd;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.nm.documents.StyleDirection;
import com.nm.documents.TableModel;
import com.nm.documents.TableModelRow;
import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandRow extends ICommand {

	public CommandRow(CommandParserResult parsed) {
		super(parsed);
	}

	public TableModelRow getModelRow(CommandChainArguments args) throws Exception {
		String src = null;
		if (parsed.containsKey("row")) {
			src = parsed.getFirst("row");
		} else if (parsed.containsKey("src")) {
			src = parsed.getFirst("src");
		}
		Assert.notNull(src);
		//
		TableModelRow m = args.getTableModelRow(src);
		Assert.notNull(m);
		return m;
	}

	public TableModel getModel(CommandChainArguments args) throws Exception {
		TableModelRow m = getModelRow(args);
		Assert.notNull(m);
		TableModel table = new TableModel();
		table.getRows().add(m);
		if (parsed.containsKey("copystyle")) {
			String val = parsed.getFirst("copystyle");
			if (StringUtils.equalsIgnoreCase(val, "H")) {
				table.setCopyStyle(StyleDirection.Horizontal);
			} else if (StringUtils.equalsIgnoreCase(val, "V")) {
				table.setCopyStyle(StyleDirection.Vertical);
			} else {
				table.setCopyStyle(StyleDirection.All);
			}
		}
		if (parsed.containsKey("insert")) {
			table.setInsertMode(BooleanUtils.toBoolean(parsed.getFirst("insert")));
		}
		return table;
	}

}
