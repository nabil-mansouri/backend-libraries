package com.nm.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class HttpUtilsExt {
	//
	protected static Log log = LogFactory.getLog(HttpUtilsExt.class);

	public static <T> T getHttpsIgnore(String url, Class<T> clazz) throws HttpResponseException {
		HttpResponseDto res = null;
		try {
			res = getHttpsIgnore(url);
			log.info(String.format("Body : \n %s", res));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(res.getBody(), clazz);
		} catch (Exception e) {
			HttpResponseException ee = new HttpResponseException(e);
			if (res != null) {
				ee.setStatus(res.getStatus());
			}
			throw ee;
		}
	}

	public static <T> T getHttp(String url, Class<T> clazz) throws HttpResponseException {
		HttpResponseDto res = null;
		try {
			res = getHttp(url);
			log.info(String.format("Body : \n %s", res));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(res.getBody(), clazz);
		} catch (Exception e) {
			HttpResponseException ee = new HttpResponseException(e);
			if (res != null) {
				ee.setStatus(res.getStatus());
			}
			throw ee;
		}
	}

	public static String get(String url) throws ClientProtocolException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		get(url, output);
		output.flush();
		output.close();
		return output.toString("UTF-8");
	}

	public static byte[] getBytes(String url) throws ClientProtocolException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		get(url, output);
		output.flush();
		output.close();
		return output.toByteArray();
	}

	public static void get(String url, OutputStream output) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		response.getEntity().writeTo(output);
	}

	public static void post(String url, String payload, OutputStream output)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(payload));
		HttpResponse response = httpclient.execute(httpPost);
		response.getEntity().writeTo(output);
	}

	public static String post(String url, String payload) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		post(url, payload, output);
		output.flush();
		output.close();
		return output.toString("UTF-8");
	}

	public static String get(URI url) throws ClientProtocolException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		get(url, output);
		output.flush();
		output.close();
		return output.toString("UTF-8");
	}

	public static void get(URI url, OutputStream output) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		response.getEntity().writeTo(output);
	}

	public static String post(URI url, String payload) throws ClientProtocolException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		post(url, payload, output);
		output.flush();
		output.close();
		return output.toString("UTF-8");
	}

	public static String deleteSafe(String url) {
		try {
			return delete(new URI(url));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String delete(String url) throws Exception {
		return delete(new URI(url));
	}

	public static String delete(URI url) throws ClientProtocolException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		delete(url, output);
		output.flush();
		output.close();
		return output.toString("UTF-8");
	}

	public static void delete(URI url, OutputStream output) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpDelete httpPost = new HttpDelete(url);
		HttpResponse response = httpclient.execute(httpPost);
		response.getEntity().writeTo(output);
	}

	public static void post(URI url, String payload, OutputStream output) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(payload));
		HttpResponse response = httpclient.execute(httpPost);
		response.getEntity().writeTo(output);
	}

	public static HttpResponseDto getHttpsIgnore(String url) throws ClientProtocolException, IOException,
			NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		HttpClient httpclient = HttpUtils.getNewHttpClientIgnoreHttps();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		//
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		response.getEntity().writeTo(output);
		output.flush();
		output.close();
		//
		HttpResponseDto httpBean = new HttpResponseDto();
		httpBean.setBody(output.toString("UTF-8"));
		httpBean.setStatus(response.getStatusLine().getStatusCode());
		return httpBean;
	}

	public static HttpResponseDto getHttp(String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		//
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		response.getEntity().writeTo(output);
		output.flush();
		output.close();
		//
		HttpResponseDto httpBean = new HttpResponseDto();
		httpBean.setBody(output.toString("UTF-8"));
		httpBean.setBodyBytes(output.toByteArray());
		httpBean.setStatus(response.getStatusLine().getStatusCode());
		return httpBean;
	}

	public static HttpResponseDto postHttp(String url, List<NameValuePair> params) throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost postMethod = new HttpPost(url);
		postMethod.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		HttpResponse response = httpclient.execute(postMethod);
		Header[] h = response.getHeaders("Content-Type");
		//
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		response.getEntity().writeTo(output);
		output.flush();
		//
		HttpResponseDto httpBean = new HttpResponseDto();
		httpBean.setType(h[0].getValue());
		httpBean.setBody(output.toString("UTF-8"));
		httpBean.setBodyBytes(output.toByteArray());
		httpBean.setStatus(response.getStatusLine().getStatusCode());
		return httpBean;
	}

	public static HttpResponseDto postHttp(String url, String payload) throws ClientProtocolException, IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(payload));
		HttpResponse response = httpclient.execute(httpPost);
		//
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		response.getEntity().writeTo(output);
		output.flush();
		output.close();
		//
		HttpResponseDto httpBean = new HttpResponseDto();
		httpBean.setBody(output.toString("UTF-8"));
		httpBean.setStatus(response.getStatusLine().getStatusCode());
		return httpBean;
	}

}
