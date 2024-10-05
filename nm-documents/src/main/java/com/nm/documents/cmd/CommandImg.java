package com.nm.documents.cmd;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.springframework.util.Assert;

import com.nm.app.utils.ImageUtil;
import com.nm.app.utils.SvgUtils;
import com.nm.app.utils.SvgUtils.Media;
import com.nm.documents.args.CommandChainArguments;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class CommandImg extends ICommand {

	public CommandImg(CommandParserResult parsed) {
		super(parsed);
	}

	public byte[] getData(CommandChainArguments args) throws Exception {
		byte[] image = null;
		String src = null;
		if (parsed.containsKey("img")) {
			src = parsed.getFirst("img");
		} else if (parsed.containsKey("src")) {
			src = parsed.getFirst("src");
		}
		Assert.notNull(src);
		//
		if (args.getImage(src) != null) {
			image = args.getImage(src);
		} else {
			String img = args.getSvg(src);
			String svg = FileUtils.readFileToString(new File(img));
			File all = SvgUtils.toFile(svg, Media.Png, null);
			image = FileUtils.readFileToByteArray(all);
		}
		return image;
	}

	public boolean hasHeight() {
		return (parsed.containsKey("height"));
	}

	public boolean hasHeightR() {
		return (parsed.containsKey("heightr"));
	}

	public int heightPoint(CommandChainArguments args) throws Exception {
		String height = parsed.getFirst("height").trim();
		if (StringUtils.equalsIgnoreCase("auto", height)) {
			int h = (int) ImageUtil.getDimension(getData(args)).getHeight();
			return (int) Units.pixelToPoints(h);
		}
		return (int) Units.pixelToPoints(Integer.valueOf(height));
	}

	public Double heightRatio(CommandChainArguments args) throws Exception {
		String height = parsed.getFirst("heightr").trim();
		return Double.valueOf(height);
	}
}
