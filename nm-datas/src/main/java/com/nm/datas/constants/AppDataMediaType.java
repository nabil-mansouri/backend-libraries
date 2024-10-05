package com.nm.datas.constants;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.Lists;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public enum AppDataMediaType {
	Text() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("text/plain");
		}

	},
	Csv() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("text/csv");
		}

	},
	Html() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("text/html");
		}

	},
	Pdf() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("application/pdf");
		}

	},
	Image() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("image/gif", "image/jpeg", "image/png", "image/tiff");
		}

	},
	Excel() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					"application/vnd.oasis.opendocument.spreadsheet");
		}

	},
	Word() {
		@Override
		public Collection<String> getMediaTypes() {
			return Arrays.asList("application/vnd.ms-word");
		}

	},
	Any() {
		@Override
		public Collection<String> getMediaTypes() {
			return Lists.newArrayList("*/*");
		}
	};
	public String getMain() {
		return getMediaTypes().iterator().next();
	}

	public abstract Collection<String> getMediaTypes();

}
