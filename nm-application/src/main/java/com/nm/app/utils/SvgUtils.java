package com.nm.app.utils;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.Main;
import org.apache.batik.apps.rasterizer.Main.AbstractOptionHandler;
import org.apache.batik.apps.rasterizer.Main.ColorOptionHandler;
import org.apache.batik.apps.rasterizer.Main.FloatOptionHandler;
import org.apache.batik.apps.rasterizer.Main.NoValueOptionHandler;
import org.apache.batik.apps.rasterizer.Main.OptionHandler;
import org.apache.batik.apps.rasterizer.Main.RectangleOptionHandler;
import org.apache.batik.apps.rasterizer.Main.SingleValueOptionHandler;
import org.apache.batik.apps.rasterizer.Main.TimeOptionHandler;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.commons.io.FileUtils;

import com.google.common.net.MediaType;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class SvgUtils {
	public enum Media {
		Png, Html, Pdf, Jpg;
		public static String toMediaString(Media m) {
			switch (m) {
			case Html:
				return MediaType.HTML_UTF_8.toString();
			case Jpg:
				return MediaType.JPEG.toString();
			case Pdf:
				return MediaType.PDF.toString();
			case Png:
				return MediaType.PNG.toString();

			}
			return null;
		}
	}

	protected static Map<String, AbstractOptionHandler> optionMap = new HashMap<String, AbstractOptionHandler>();

	/**
	 * Static map containing all the mime types understood by the rasterizer
	 */
	protected static Map<String, DestinationType> mimeTypeMap = new HashMap<String, DestinationType>();

	static {
		mimeTypeMap.put("image/jpg", DestinationType.JPEG);
		mimeTypeMap.put("image/jpeg", DestinationType.JPEG);
		mimeTypeMap.put("image/jpe", DestinationType.JPEG);
		mimeTypeMap.put("image/png", DestinationType.PNG);
		mimeTypeMap.put("application/pdf", DestinationType.PDF);
		mimeTypeMap.put("image/tiff", DestinationType.TIFF);

		optionMap.put(Main.CL_OPTION_OUTPUT, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setDst(new File(optionValue));
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_OUTPUT_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_MIME_TYPE, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				DestinationType dstType = (DestinationType) mimeTypeMap.get(optionValue);

				if (dstType == null) {
					throw new IllegalArgumentException();
				}

				c.setDestinationType(dstType);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_MIME_TYPE_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_WIDTH, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if (optionValue <= 0) {
					throw new IllegalArgumentException();
				}

				c.setWidth(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_WIDTH_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_HEIGHT, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if (optionValue <= 0) {
					throw new IllegalArgumentException();
				}

				c.setHeight(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_HEIGHT_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_MAX_WIDTH, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if (optionValue <= 0) {
					throw new IllegalArgumentException();
				}

				c.setMaxWidth(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_MAX_WIDTH_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_MAX_HEIGHT, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if (optionValue <= 0) {
					throw new IllegalArgumentException();
				}

				c.setMaxHeight(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_MAX_HEIGHT_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_AOI, new RectangleOptionHandler() {
			public void handleOption(Rectangle2D optionValue, SVGConverter c) {
				c.setArea(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_AOI_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_BACKGROUND_COLOR, new ColorOptionHandler() {
			public void handleOption(Color optionValue, SVGConverter c) {
				c.setBackgroundColor(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_BACKGROUND_COLOR_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_MEDIA_TYPE, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setMediaType(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_MEDIA_TYPE_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_DEFAULT_FONT_FAMILY, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setDefaultFontFamily(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_DEFAULT_FONT_FAMILY_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_ALTERNATE_STYLESHEET, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setAlternateStylesheet(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_ALTERNATE_STYLESHEET_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_USER_STYLESHEET, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setUserStylesheet(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_USER_STYLESHEET_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_LANGUAGE, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setLanguage(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_LANGUAGE_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_DPI, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if (optionValue <= 0) {
					throw new IllegalArgumentException();
				}

				c.setPixelUnitToMillimeter((2.54f / optionValue) * 10);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_DPI_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_QUALITY, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if (optionValue <= 0 || optionValue >= 1) {
					throw new IllegalArgumentException();
				}

				c.setQuality(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_QUALITY_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_INDEXED, new FloatOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				if ((optionValue != 1) && (optionValue != 2) && (optionValue != 4) && (optionValue != 8))
					throw new IllegalArgumentException();

				c.setIndexed((int) optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_INDEXED_DESCRIPTION;
			}
		});
		optionMap.put(Main.CL_OPTION_VALIDATE, new NoValueOptionHandler() {
			public void handleOption(SVGConverter c) {
				c.setValidate(true);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_VALIDATE_DESCRIPTION;
			}
		});
		optionMap.put(Main.CL_OPTION_ONLOAD, new NoValueOptionHandler() {
			public void handleOption(SVGConverter c) {
				c.setExecuteOnload(true);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_ONLOAD_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_SNAPSHOT_TIME, new TimeOptionHandler() {
			public void handleOption(float optionValue, SVGConverter c) {
				c.setExecuteOnload(true);
				c.setSnapshotTime(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_SNAPSHOT_TIME_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_ALLOWED_SCRIPTS, new SingleValueOptionHandler() {
			public void handleOption(String optionValue, SVGConverter c) {
				c.setAllowedScriptTypes(optionValue);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_ALLOWED_SCRIPTS_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN, new NoValueOptionHandler() {
			public void handleOption(SVGConverter c) {
				c.setConstrainScriptOrigin(false);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN_DESCRIPTION;
			}
		});

		optionMap.put(Main.CL_OPTION_SECURITY_OFF, new NoValueOptionHandler() {
			public void handleOption(SVGConverter c) {
				c.setSecurityOff(true);
			}

			public String getOptionDescription() {
				return Main.CL_OPTION_SECURITY_OFF_DESCRIPTION;
			}
		});
	}

	//
	public static byte[] toByte(String svg, Media type, Double width) throws Exception {
		byte[] all = FileUtils.readFileToByteArray(toFile(svg, type, width));
		return all;

	}

	public static File toFile(String svg, Media type, Double width) throws Exception {
		File in = File.createTempFile(type + UUIDUtils.uuid(16), ".svg");
		FileUtils.writeStringToFile(in, svg, "UTF-8");
		File out = File.createTempFile(type + UUIDUtils.uuid(16), ".tmp");
		List<String> args = new ArrayList<String>();
		args.add("-d");
		args.add(out.getAbsolutePath());
		args.add("-m");
		args.add(Media.toMediaString(type));
		if (type.equals(Media.Jpg)) {
			args.add("-q");
			args.add("0.99");
		}
		if (width != null) {
			args.add("-w");
			args.add("" + width);
		}
		args.add(in.getAbsolutePath());
		Main m = new Main(args.toArray(new String[args.size()]));
		execute(args, m);
		return (out);
	}

	protected static void execute(List<String> args, Main m) {
		SVGConverter c = new SVGConverter(m);

		List<String> sources = new ArrayList<String>();
		int nArgs = args.size();
		for (int i = 0; i < nArgs; i++) {
			String v = (String) args.get(i);
			OptionHandler optionHandler = (OptionHandler) optionMap.get(v);
			if (optionHandler == null) {
				// Assume v is a source.
				sources.add(v);
			} else {
				// v is an option. Extract the optionValues required
				// by the handler.
				int nOptionArgs = optionHandler.getOptionValuesLength();
				if (i + nOptionArgs >= nArgs) {
					// Main.error(Main.ERROR_NOT_ENOUGH_OPTION_VALUES, new
					// Object[] { v, optionHandler.getOptionDescription() });
					return;
				}

				String[] optionValues = new String[nOptionArgs];
				for (int j = 0; j < nOptionArgs; j++) {
					optionValues[j] = (String) args.get(1 + i + j);
				}
				i += nOptionArgs;

				try {
					optionHandler.handleOption(optionValues, c);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					// error(ERROR_ILLEGAL_ARGUMENT, new Object[] { v,
					// optionHandler.getOptionDescription(),
					// toString(optionValues) });
					return;
				}
			}
		}

		// Apply script security option

		String[] expandedSources = expandSources(sources);

		c.setSources(expandedSources);

		// validateConverterConfig(c);

		if (expandedSources == null || expandedSources.length < 1) {
			// System.out.println(USAGE);
			System.out.flush();
			return;
		}

		try {
			c.execute();
		} catch (SVGConverterException e) {
			e.printStackTrace();
			// error(ERROR_WHILE_CONVERTING_FILES, new Object[] { e.getMessage()
			// });
		} finally {
			System.out.flush();
		}
	}

	protected static String[] expandSources(List<String> sources) {
		List<String> expandedSources = new ArrayList<String>();
		Iterator<String> iter = sources.iterator();
		while (iter.hasNext()) {
			String v = (String) iter.next();
			File f = new File(v);
			if (f.exists() && f.isDirectory()) {
				File[] fl = f.listFiles(new SVGConverter.SVGFileFilter());
				for (int i = 0; i < fl.length; i++) {
					expandedSources.add(fl[i].getPath());
				}
			} else {
				expandedSources.add(v);
			}
		}

		String[] s = new String[expandedSources.size()];
		expandedSources.toArray(s);
		return s;
	}
}
