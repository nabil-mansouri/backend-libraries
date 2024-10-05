package com.nm.auths.oauth.manager;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.http.client.utils.URIBuilder;

import com.nm.utils.ApplicationUtils;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class OAuthContextWebFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		URIBuilder uri = new URIBuilder();
		uri.setScheme(request.getScheme());
		uri.setHost(request.getServerName());
		uri.setPort(request.getServerPort());
		for (String key : request.getParameterMap().keySet()) {
			uri.addParameter(key, request.getParameter(key));
		}
		OAuthContext context = ApplicationUtils.getBean(OAuthContextWeb.class);
		try {
			context.setCurrentURL(uri.build().toString());
			context.setParameters(uri.getQueryParams());
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void destroy() {

	}

}
