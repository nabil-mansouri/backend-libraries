package com.nm.documents.aspose.cmd;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.aspose.words.Node;
import com.nm.app.utils.SvgUtils;
import com.nm.app.utils.SvgUtils.Media;
import com.nm.documents.PositionModel;
import com.nm.documents.args.CommandChainContext;
import com.nm.documents.aspose.parser.PatternState;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandChainImg extends CommandChain {

	@Override
	protected CommandChainContext doCmd(CommandChainContext context) throws Exception {
		//
		Collection<Node> n = new ArrayList<Node>();
		for (PatternState s : context.getTransaction().getStates()) {
			n.add(s.getNode());
		}
		//
		byte[] image = null;
		String src = context.getCmd().getFirst("src");
		if (context.getArgs().getImage(src) != null) {
			image = context.getArgs().getImage(src);
		} else {
			String img = context.getArgs().getSvg(src);
			String svg = FileUtils.readFileToString(new File(img));
			File all = SvgUtils.toFile(svg, Media.Png, null);
			image = FileUtils.readFileToByteArray(all);
		}
		context.getWord().pushPngImage(new PositionModel(n), new ByteArrayInputStream(image), src);
		//
		for (PatternState s : context.getTransaction().getStates()) {
			context.getWord().clear(s);
		}
		return context;
	}

	@Override
	protected boolean accept(CommandChainContext context) {
		if (StringUtils.equalsIgnoreCase(context.getCmd().getRoot(), "img")) {
			return true;
		}
		return false;
	}

}
