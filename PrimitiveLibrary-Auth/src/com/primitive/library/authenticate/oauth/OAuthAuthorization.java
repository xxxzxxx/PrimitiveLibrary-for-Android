package com.primitive.library.authenticate.oauth;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.primitive.library.helper.http.HttpClientHelper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OAuthAuthorization {
	private final OAuthConfiguration configure;
	private final Context context;
	public OAuthAuthorization(Context context, OAuthConfiguration configure){
		this.context = context;
		this.configure = configure;
	}

	public void openAuthorizationIntent(){
		openAuthorizationIntent(this.context,this.configure);
	}

	public static void openAuthorizationIntent(Context context,OAuthConfiguration configure){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(configure.getAuthorizeUri());
		context.startActivity(intent);
	}

	public OAuthToken getAuthorization(String code)
			throws ClientProtocolException, IOException {
		ArrayList<NameValuePair> request = new ArrayList<NameValuePair>();
		request.add(new BasicNameValuePair("grant_type", "authorization_code"));
		request.add(new BasicNameValuePair("client_id", configure.getClientId()));
		request.add(new BasicNameValuePair("client_secret", configure.getClientSecret()));
		request.add(new BasicNameValuePair("redirect_uri", configure.getRedirectUrl()));
		request.add(new BasicNameValuePair("code", code));
		return HttpClientHelper.doPostRequest(
				configure.getUserAgent(),
				configure.getEndPoint(),
				request,
				new TokenReponseHandler());
	}

	public OAuthToken refresh(String token)
			throws ClientProtocolException, IOException {
		ArrayList<NameValuePair> request = new ArrayList<NameValuePair>();
		request.add(new BasicNameValuePair("grant_type", "refresh_token"));
		request.add(new BasicNameValuePair("client_id", configure.getClientId()));
		request.add(new BasicNameValuePair("client_secret", configure.getClientSecret()));
		request.add(new BasicNameValuePair("refresh_token", token));
		return HttpClientHelper.doPostRequest(
				configure.getUserAgent(),
				configure.getEndPoint(),
				request,
				new TokenReponseHandler());
	}

	/**
	 * @author xxx
	 */
	public static class InvalidTokenException extends ClientProtocolException{
		private static final long serialVersionUID = -237264612885447238L;
		public InvalidTokenException(String msg){
			super(msg);
		}
	}

	/**
	 * @author xxx
	 */
	private static class TokenReponseHandler implements ResponseHandler<OAuthToken> {
		private static final String TAG = "TokenResponseHandler";
		@Override
		public OAuthToken handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			if (response == null)
				throw new NullPointerException("response is null");
			int statusCode = response.getStatusLine().getStatusCode();
			switch (statusCode) {
				case 401:
					Log.w(TAG, "authentation failed");
					throw new OAuthAuthorization.InvalidTokenException(getOAuthError(response));
				case 200:
					Log.v(TAG, "got response successfully");
					return parseTokenFromResponse(EntityUtils.toString(response.getEntity(),
							HTTP.UTF_8));
			}
			throw new HttpResponseException(statusCode, "unexpected response: "
					+ response.getStatusLine().toString() + ": "
					+ EntityUtils.toString(response.getEntity()));
		}

		/**
		 * @param string
		 * @return
		 */
		private OAuthToken parseTokenFromResponse(String string) {
			try {
				JSONObject json = new JSONObject(string);
				OAuthToken token = new OAuthToken();
				token.setAccessToken(json.getString("access_token"));
				token.setRefreshToken(json.getString("refresh_token"));
				token.setExpiresOn(System.currentTimeMillis() + json.getLong("expires_in") * 1000);
				return token;
			} catch (JSONException e) {
				Log.w(TAG, "something went wrong while parsing json", e);
				return null;
			}
		}
	}

	/**
	 * @param response
	 * @return
	 */
	public static boolean isTokenExpiredResponse(HttpResponse response) {
		String error = getOAuthError(response);
		return error != null && error.contains("expired_token");
	}

	/**
	 * @param response
	 * @return
	 */
	public static String getOAuthError(HttpResponse response) {
		Header result = response.getFirstHeader("WWW-Authenticate");
		if (result == null) return null;
		for (HeaderElement element : result.getElements()) {
			if (element.getName().equals("OAuth error")) {
				return element.getValue();
			}
		}
		return null;
	}
}