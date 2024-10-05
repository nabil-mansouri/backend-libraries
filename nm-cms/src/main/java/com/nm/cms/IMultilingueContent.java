package com.nm.cms;

import java.util.List;

/**
 * 
 * @author Nabil
 * 
 */
public interface IMultilingueContent {

	public String getDescription(String locale);

	public List<String> getKeyword(String locale);

	public String getName(String locale);

	public List<String> getNameLangage();
}
