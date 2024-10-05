package com.nm.auths.oauth.manager;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public interface OAuthContext {

	String getCurrentURL();

	void setCurrentURL(String currentURL);

	List<NameValuePair> getParameters();

	Map<String, String> getMapParameters();

	void setParameters(List<NameValuePair> parameters);

	void setParameters(Map<String, String> queryParamsMap);

	void removeParameters(String key);

}