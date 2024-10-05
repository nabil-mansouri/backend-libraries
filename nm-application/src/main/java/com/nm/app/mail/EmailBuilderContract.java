package com.nm.app.mail;

import java.io.File;
import java.util.Collection;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface EmailBuilderContract {
	public boolean hasBody();

	public boolean history();

	public boolean hasSubject();

	public String subject() throws Exception;

	public String body() throws Exception;

	public String charset();

	public String type();

	public Collection<File> joined() throws Exception;

	public String from(String defaut);

	public Collection<String> to();

	public Collection<String> cc();

	public Collection<String> cci();

}
