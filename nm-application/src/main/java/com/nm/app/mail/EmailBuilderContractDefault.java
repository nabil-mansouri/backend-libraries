package com.nm.app.mail;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import com.google.common.base.Strings;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public abstract class EmailBuilderContractDefault implements EmailBuilderContract {
	public boolean hasBody() {
		try {
			return !Strings.isNullOrEmpty(body());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean history() {
		return false;
	}

	public boolean hasSubject() {
		try {
			return !Strings.isNullOrEmpty(subject());
		} catch (Exception e) {
			return false;
		}
	}

	public Collection<File> joined() throws Exception {
		return Collections.emptyList();
	}

	public Collection<String> cc() {
		return Collections.emptyList();
	}

	public final String from(String defaut) {
		if (Strings.isNullOrEmpty(innerFrom())) {
			return defaut;
		} else {
			return innerFrom();
		}
	}

	protected String innerFrom() {
		return null;
	}

	public String charset() {
		return "utf-8";
	}

	public String type() {
		return "html";
	}

	public Collection<String> cci() {
		return Collections.emptyList();
	}

}
