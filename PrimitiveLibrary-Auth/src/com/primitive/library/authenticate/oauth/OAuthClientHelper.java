package com.primitive.library.authenticate.oauth;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import com.primitive.library.helper.http.HttpClientHelper;
import com.primitive.library.helper.http.HttpHeader;

import android.net.Uri;

public class OAuthClientHelper extends HttpClientHelper{
	public final OAuthAuthorization client;
	public OAuthClientHelper(OAuthAuthorization client,String userAgent) {
		super(userAgent);
		this.client = client;
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
			OAuthToken accessToken,
			HttpHeader... headers
	) throws ClientProtocolException, IOException {
		return super.doPostRequest(
				endpointUrl,
				params,
				responseHandler,
				headers
				);
	}

	/**
	 *
	 * @param userAgent
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @param accessToken
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static <T> T doPostRequest(String userAgent,
		String endpointUrl,
		ArrayList<NameValuePair> params,
		ResponseHandler<T> responseHandler,
		OAuthToken accessToken,
		HttpHeader... headers
	) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(endpointUrl);
		UrlEncodedFormEntity entry = new UrlEncodedFormEntity(params);
		request.setEntity(entry);
		for (HttpHeader header : headers){
			request.setHeader(header.getName(),header.getValue());
		}
		return executeRequest(userAgent,request, responseHandler);
	}

	/**
	 *
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @param accessToken
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public <T> T doGetRequest(
		String endpointUrl,
		ArrayList<NameValuePair> params,
		ResponseHandler<T> responseHandler,
		OAuthToken accessToken,
		HttpHeader... headers
	) throws ClientProtocolException, IOException {
		return super.doGetRequest(
				this.userAgent,
				endpointUrl,
				params,
				responseHandler,
				headers
				);
	}

	/**
	 *
	 * @param userAgent
	 * @param endpointUrl
	 * @param params
	 * @param responseHandler
	 * @param accessToken
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static <T> T doGetRequest(String userAgent,
		String endpointUrl,
		ArrayList<NameValuePair> params,
		ResponseHandler<T> responseHandler,
		OAuthToken accessToken,
		Header... headers
	) throws ClientProtocolException, IOException {
		Uri.Builder uriBuilder = Uri.parse(endpointUrl).buildUpon();
		if (params != null) {
			for (NameValuePair nvp : params) {
				uriBuilder.appendQueryParameter(nvp.getName(), nvp.getValue());
			}
		}
		HttpGet request = new HttpGet(uriBuilder.build().toString());
		request.setHeaders(headers);
		return executeRequest(userAgent,request, responseHandler);
	}

	private OAuthToken getValidAccessToken(OAuthToken token) throws ClientProtocolException, IOException{
		return getValidAccessToken(client,token);
	}

	private static OAuthToken getValidAccessToken(OAuthAuthorization client,OAuthToken token)
			throws ClientProtocolException, IOException {
		if (token != null && token.getAccessToken() != null) {
			if (token.getExpiresOn() < System.currentTimeMillis()) {
				token = client.refresh(token.getRefreshToken());
//				OAuthTokenHolder.setToken(token);
			}
		}
		return token;
	}

}
