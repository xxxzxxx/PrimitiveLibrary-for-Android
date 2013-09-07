package com.primitive.library.authenticate.twitter;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.primitive.library.common.log.Logger;
import com.primitive.library.helper.http.HttpClientHelper;

public class RequestToken {
	final Configuration configure;
	static final String Encode = HTTP.UTF_8;
	public RequestToken(final Configuration configure){
		this.configure = configure;
	}

	public RequestTokenResponse getRequestToken()
			throws ClientProtocolException, IOException {

		Long start = Logger.start();
		RequestTokenResponse requestTokenResponse =  HttpClientHelper.doPostRequest(
				"xxxx",
				Configuration.RequestTokenURL,
				null,
				new RequestTokenHandler(),
				configure.buildRequestokenHeader()
		);
		Logger.end(start);
		return requestTokenResponse;
	}

	private static class RequestTokenHandler
			implements ResponseHandler<RequestTokenResponse> {
		@Override
		public RequestTokenResponse handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			Long start = Logger.start();
			try {
				if (response == null)
					throw new NullPointerException("response is null");
				int statusCode = response.getStatusLine().getStatusCode();
				switch (statusCode) {
					case 401:   // Authorization Required
						Logger.debug("401");
					case 200:   // OK
						Logger.debug("200");
						return parse(EntityUtils.toString(response.getEntity(),Encode));
				}

				HttpResponseException ex = new HttpResponseException(statusCode, "unexpected response: "
						+ response.getStatusLine().toString() + ": "
						+ EntityUtils.toString(response.getEntity()));
				Logger.err(ex);
				throw ex;
			} finally {
				Logger.end(start);
			}
		}

		private RequestTokenResponse parse(String string) {
			Logger.debug(string);
			RequestTokenResponse res = new RequestTokenResponse();
			return res;
		}
	}
}
