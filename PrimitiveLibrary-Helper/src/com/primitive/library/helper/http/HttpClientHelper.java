package com.primitive.library.helper.http;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.primitive.library.common.log.Logger;

import android.net.Uri;
import android.net.http.AndroidHttpClient;

public class HttpClientHelper {
	protected final String userAgent;
	public HttpClientHelper(String userAgent){
		this.userAgent = userAgent;
	}

	/**
	 *
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public <T> T doPostRequest(
			String endpointUrl,
			ArrayList<NameValuePair> params,
			ResponseHandler<T> responseHandler,
			HttpHeader... headers
	) throws ClientProtocolException, IOException {
		Long start = Logger.start();
		T result =  HttpClientHelper.doPostRequest(
				userAgent,
				endpointUrl,
				params,
				responseHandler,
				headers
				);
		Logger.end(start);
		return result;
	}

	/**
	 *
	 * @param userAgent
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static <T> T doPostRequest(String userAgent,
		String endpointUrl,
		ArrayList<NameValuePair> params,
		ResponseHandler<T> responseHandler,
		HttpHeader... headers
	) throws ClientProtocolException, IOException {
		Long start = Logger.start();
		Logger.debug(userAgent);
		Logger.debug(endpointUrl);
		Logger.debug(params);
		Logger.debug(headers);

		HttpPost request = new HttpPost(endpointUrl);
		if(params != null){
			UrlEncodedFormEntity entry = new UrlEncodedFormEntity(params);
			request.setEntity(entry);
		}
		for (HttpHeader header : headers){
			request.setHeader(header.getName(),header.getValue());
		}
		T result = executeRequest(userAgent,request, responseHandler);
		Logger.end(start);
		return result;
	}

	/**
	 *
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public <T> T doGetRequest(
		String endpointUrl,
		ArrayList<NameValuePair> params,
		ResponseHandler<T> responseHandler,
		HttpHeader... headers
	) throws ClientProtocolException, IOException {
		Long start = Logger.start();
		T result = HttpClientHelper.doGetRequest(
				this.userAgent,
				endpointUrl,
				params,
				responseHandler,
				headers
				);
		Logger.end(start);
		return result;
	}

	/**
	 *
	 * @param userAgent
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static <T> T doGetRequest(String userAgent,
		String endpointUrl,
		ArrayList<NameValuePair> params,
		ResponseHandler<T> responseHandler,
		HttpHeader... headers
	) throws ClientProtocolException, IOException {
		Long start = Logger.start();
		Uri.Builder uriBuilder = Uri.parse(endpointUrl).buildUpon();
		if (params != null) {
			for (NameValuePair nvp : params) {
				uriBuilder.appendQueryParameter(nvp.getName(), nvp.getValue());
			}
		}
		HttpGet request = new HttpGet(uriBuilder.build().toString());
		for (HttpHeader header : headers){
			request.setHeader(header.getName(),header.getValue());
		}
		T result = executeRequest(userAgent,request, responseHandler);
		Logger.end(start);
		return result;
	}

	/**
	 *
	 * @param userAgent
	 * @param request
	 * @param responseHandler
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public <T> T executeRequest(
			HttpRequestBase request,
			ResponseHandler<T> responseHandler
	) throws ClientProtocolException, IOException {
		Long start = Logger.start();
		try {
			T result = executeRequest(
					this.userAgent,
					request,
					responseHandler);
			return result;
		} catch (ClientProtocolException ex){
			Logger.err(ex);
			throw ex;
		} catch (IOException ex){
			Logger.err(ex);
			throw ex;
		} finally {
			Logger.end(start);
		}
	}

	/**
	 *
	 * @param userAgent
	 * @param request
	 * @param responseHandler
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static <T> T executeRequest(
			String userAgent,
			HttpRequestBase request,
			ResponseHandler<T> responseHandler
	) throws ClientProtocolException, IOException {
		Long start = Logger.start();
		AndroidHttpClient client = AndroidHttpClient.newInstance(userAgent);
		try {
			return client.execute(request, responseHandler);
		} finally {
			client.close();
			Logger.end(start);
		}

	}
}
