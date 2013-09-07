package com.primitive.library.authenticate.oauth;

import java.io.Serializable;

public class OAuthToken implements Serializable{
	private static final long serialVersionUID = -2214068510518489541L;

	private String tag;
	private String accessToken;
	private String refreshToken;
    private long expiresOn;

    public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public long getExpiresOn() {
		return expiresOn;
	}
	public void setExpiresOn(long expiresOn) {
		this.expiresOn = expiresOn;
	}
}
