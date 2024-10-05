package com.nm.auths.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.nm.auths.oauth.manager.OAuthContext;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dates.URLUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class TwitterWebTestUtils {
	public static abstract class CallTwitter {
		public abstract void operation() throws Exception;

		public void call() throws Exception {
			OAuthContext context = ApplicationUtils.getBean(OAuthContext.class);
			try {
				context.setCurrentURL("http://localhost");
				operation();
			} catch (UserRedirectRequiredException e) {
				e.printStackTrace();
				String urlRedirect = simulate(e);
				context.setParameters(URLUtilsExt.getQueryParamsMap(urlRedirect));
				operation();
			} catch (Exception e) {
				Throwable root = ExceptionUtils.getRootCause(e);
				if (root instanceof UserRedirectRequiredException) {
					String urlRedirect = TwitterWebTestUtils.simulate((UserRedirectRequiredException) root);
					context.setParameters(URLUtilsExt.getQueryParamsMap(urlRedirect));
					operation();
				} else {
					throw e;
				}
			}
		}
	}

	static final WebClient webClient = new WebClient(BrowserVersion.CHROME);
	static java.util.Random randForName = new java.util.Random();

	static {
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.setAjaxController(new AjaxController() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
				return true;
			}
		});
	}

	public static String simulate(UserRedirectRequiredException ee) throws Exception {
		return simulate(ee, "nm_dev_test", "mansouri");
	}

	public static String simulate(UserRedirectRequiredException ee, String email, String pass) throws Exception {
		try {
			String url = ee.getRedirectUri();
			Map<String, String> map = ee.getRequestParams();
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage page = writePage(webClient.getPage(URLUtilsExt.build(url, map)));
			if (finished(page)) {
				return page.getUrl().toString();
			}
			page = login(page, email, pass);
			if (finished(page)) {
				return page.getUrl().toString();
			}
			throw new IllegalArgumentException("Could not found access token");
		} catch (FailingHttpStatusCodeException e) {
			System.out.println(("********************"));
			System.out.println(e.getResponse().getContentAsString());
			System.out.println(("********************"));
			throw e;
		} finally {
			webClient.close();
		}
	}

	static int cpt = 0;

	private static boolean finished(HtmlPage page) throws Exception {
		if (URLUtilsExt.getQueryParamsMap(page.getUrl().toString()).containsKey("oauth_verifier")) {
			String code = URLUtilsExt.getQueryParamsFirst(page.getUrl(), "oauth_token");
			String state = URLUtilsExt.getQueryParamsFirst(page.getUrl(), "oauth_verifier");
			System.out.println(("********************"));
			System.out.println(code);
			System.out.println(state);
			System.out.println(page.getUrl().toString());
			System.out.println(("********************"));
			return true;
		}
		return false;
	}

	private static HtmlPage login(HtmlPage page, String email, String pass) throws Exception {
		if (page.getElementById("allow") != null) {
			if (page.getElementsByName("session[username_or_email]").isEmpty()) {
				HtmlSubmitInput signInButton = (HtmlSubmitInput) page.getElementById("allow");
				return writePage(signInButton.click());
			} else {
				HtmlSubmitInput signInButton = (HtmlSubmitInput) page.getElementById("allow");
				HtmlTextInput userNameField = (HtmlTextInput) page.getElementByName("session[username_or_email]");
				userNameField.setValueAttribute(email);
				HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementByName("session[password]");
				passwordField.setValueAttribute(pass);
				return writePage(signInButton.click());
			}
		} else {
			return page;
		}
	}

	private static HtmlPage writePage(Page html) throws IOException {
		File page = new File(String.format("output/twitter%s.html", cpt++));
		page.delete();
		FileUtils.write(page, html.getWebResponse().getContentAsString());
		System.out.println(page.getAbsolutePath());
		return (HtmlPage) html;
	}
}
