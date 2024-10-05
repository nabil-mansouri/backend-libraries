package com.nm.auths.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Assert;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.nm.auths.oauth.manager.OAuthContext;
import com.nm.utils.ApplicationUtils;
import com.nm.utils.dates.URLUtilsExt;

/**
 * 
 * @author Nabil MANSOURI
 *
 */
public class GoogleWebTestUtils {
	public static abstract class CallGoogle {
		private String EMAIL = "nabil.mansouri@servius.com";
		private String PASS = "jadl170606";

		public abstract void operation() throws Exception;

		public void call() throws Exception {
			OAuthContext context = ApplicationUtils.getBean(OAuthContext.class);
			try {
				context.setCurrentURL("http://localhost");
				operation();
			} catch (UserRedirectRequiredException e) {
				e.printStackTrace();
				String urlRedirect = simulate(e, EMAIL, PASS);
				context.setParameters(URLUtilsExt.getQueryParamsMap(urlRedirect));
				operation();
			} catch (Exception e) {
				Throwable root = ExceptionUtils.getRootCause(e);
				if (root instanceof UserRedirectRequiredException) {
					String urlRedirect = GoogleWebTestUtils.simulate((UserRedirectRequiredException) root, EMAIL, PASS);
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
		return simulate(ee, "nabil.mansouri@servius.com", "jadl170606");
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
			// SOMETIMES 2 PAGE OF IDENTIFICATION
			page = login(page, email, pass);
			if (finished(page)) {
				return page.getUrl().toString();
			}
			page = confirm(page);
			if (finished(page)) {
				return page.getUrl().toString();
			} else {
				throw new IllegalArgumentException("Could not found access token");
			}
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
		if (URLUtilsExt.getQueryParamsMap(page.getUrl().toString()).containsKey("code")) {
			String code = URLUtilsExt.getQueryParamsFirst(page.getUrl(), "code");
			String state = URLUtilsExt.getQueryParamsFirst(page.getUrl(), "session_state");
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
		if (page.getElementsByName("signIn").size() > 0) {
			HtmlElement signInButton = (HtmlElement) page.getElementByName("signIn");
			HtmlTextInput userNameField = (HtmlTextInput) page.getElementByName("Email");
			userNameField.setValueAttribute(email);
			HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementByName("Passwd");
			passwordField.setValueAttribute(pass);
			return writePage(signInButton.click());
		} else {
			return page;
		}
	}

	private static HtmlPage confirm(HtmlPage page) throws Exception {
		if (page.getElementById("submit_approve_access") == null) {
			Thread.sleep(5000);
		}
		if (page.getElementById("submit_approve_access") != null) {
			HtmlButton allowAccessButton = (HtmlButton) page.getElementById("submit_approve_access");
			allowAccessButton.removeAttribute("disabled");
			Assert.assertFalse(allowAccessButton.isDisabled());
			return writePage(allowAccessButton.click());
		} else {
			return page;
		}
	}

	private static HtmlPage writePage(Page html) throws IOException {
		File page = new File(String.format("output/google%s.html", cpt++));
		page.delete();
		FileUtils.write(page, html.getWebResponse().getContentAsString());
		System.out.println(page.getAbsolutePath());
		return (HtmlPage) html;
	}
}
