package com.primitive.library.authenticate.oauth;

import android.net.Uri;

public interface OAuthConfiguration{

	public String getUserAgent();
	public String getResponseType();
	public String getAuthorizeUrl();
	public String getEndPoint();
	public String getRedirectUrl();
	public String getClientId();
	public String getClientSecret();
	public String getResponse_type();
	public String[] getScope();
	public Uri getAuthorizeUri();
}
