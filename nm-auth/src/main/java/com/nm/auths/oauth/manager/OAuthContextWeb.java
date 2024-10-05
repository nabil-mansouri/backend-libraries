package com.nm.auths.oauth.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuthContextWeb implements Serializable, OAuthContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currentURL;
	private List<NameValuePair> parameters = new ArrayList<NameValuePair>();

	public String getCurrentURL() {
		return currentURL;
	}

	public void setParameters(Map<String, String> queryParamsMap) {
		for (String key : queryParamsMap.keySet()) {
			String val = queryParamsMap.get(key);
			parameters.add(new BasicNameValuePair(key, val));
		}
	}

	public void removeParameters(String key) {
		Collection<NameValuePair> toDel = Lists.newArrayList();
		for (NameValuePair n : this.parameters) {
			if (n.getName().equals(key)) {
				toDel.add(n);
			}
		}
		this.parameters.removeAll(toDel);
	}

	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	public Map<String, String> getMapParameters() {
		Map<String, String> s = Maps.newHashMap();
		for (NameValuePair p : this.parameters) {
			s.put(p.getName(), p.getValue());
		}
		return s;
	}

	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

}
