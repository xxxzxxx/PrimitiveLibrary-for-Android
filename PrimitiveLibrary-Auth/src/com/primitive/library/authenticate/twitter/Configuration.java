package com.primitive.library.authenticate.twitter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;

import android.net.Uri;
import android.util.Base64;

import com.primitive.library.authenticate.oauth.OAuthConfiguration;
import com.primitive.library.common.log.Logger;
import com.primitive.library.helper.cipher.HashMacHelper;
import com.primitive.library.helper.http.HttpHeader;


public class Configuration implements OAuthConfiguration{

	final static String UserAgent = "TwitterOAuthClient";
	final static String OAuthToken = "";
	final static String ConsumerKey = "4F0DnsHNuCznZhDCogEQ";
	final static String ConsumerSecret = "rCZhcJOuztRviIvxaEkXOMDQ8q2fLQWw2wqGT5vpx8M";
	final static String OAuthURL = "http://api.twitter.com/oauth/";

	final static String AuthorizeURL= OAuthURL + "authorize";
	final static String AccesstokenURL= OAuthURL + "access_token";
	final static String RequestTokenURL = OAuthURL + "request_token";

	private String oAuthToken = null;

	@Override
	public String getUserAgent() {
		return UserAgent;
	}

	@Override
	public String getResponseType() {
		return null;
	}

	@Override
	public String getAuthorizeUrl() {
		return AuthorizeURL;
	}

	@Override
	public String getEndPoint() {
		return OAuthURL;
	}

	@Override
	public String getRedirectUrl() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getClientId() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getClientSecret() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getResponse_type() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String[] getScope() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Uri getAuthorizeUri() {
		return Uri.parse(AuthorizeURL).buildUpon()
				.appendQueryParameter("oauth_token", oAuthToken)
				.appendQueryParameter("force_login", "true")
				.build();
	}

	private static String encode(String value){
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length()
                    && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
	}

	private static String createSignatureBase(String method,String url, String oauth_nonce,long time) throws UnsupportedEncodingException {
		StringBuilder builder1 = new StringBuilder();
		builder1.append(method);
		builder1.append("&"+encode(url));
		StringBuilder builder2 = new StringBuilder();
		builder2.append("oauth_consumer_key=" + encode(ConsumerKey));
		builder2.append("&oauth_nonce=" + encode(oauth_nonce));
		builder2.append("&oauth_signature_method=" + encode("HMAC-SHA1"));
		builder2.append("&oauth_timestamp=" + encode(String.valueOf(time)));
		builder2.append("&oauth_version=" + "1.0");
		builder1.append(encode(builder2.toString()));
		return builder1.toString();
	}

	private String sign(String body,String pass){
		Long start = Logger.start();
		String signature;
		signature = HashMacHelper.getHMACBase64(
				com.primitive.library.helper.cipher.HashMacHelper.Algorithm.HmacSHA1
				,body.getBytes()
				,pass.getBytes());
		Logger.end(start);
		return signature.trim();
	}

	/**
	 * @return
	 */
	public HttpHeader buildRequestokenHeader() {
		Long start = Logger.start();
/*
		String oauth_nonce = UUID.randomUUID().toString();
		oauth_nonce = oauth_nonce.replaceAll("-", "");

		Calendar cal = Calendar.getInstance();
		long time = cal.getTimeInMillis() / 1000L;
//		long time = System.currentTimeMillis() / 1000L;
		StringBuilder builder = new StringBuilder();
		try {
			String signatureBase = createSignatureBase("POST",RequestTokenURL,oauth_nonce,time);
			String signature = sign(signatureBase,ConsumerSecret+"&");
			builder.append("OAuth oauth_consumer_key=\n" + ConsumerKey + "\"");
			builder.append(",oauth_signature_method=\"HMAC-SHA1\"");
			builder.append(",oauth_timestamp=\""+ String.valueOf(time) +"\"");
			builder.append(",oauth_nonce=\""+ oauth_nonce +"\"");
			builder.append(",oauth_version=\"1.0\"");
			builder.append(",oauth_signature=\""+ encode(signature) +"\"");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		Logger.end(start);
*/
		// this particular request uses POST
		String get_or_post = "POST";

		// I think this is the signature method used for all Twitter API calls
		String oauth_signature_method = "HMAC-SHA1";

		// generate any fairly random alphanumeric string as the "nonce". Nonce = Number used ONCE.
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string; // any relatively random alphanumeric string will work here

		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		// assemble the proper parameter string, which must be in alphabetical order, using your consumer key
		String parameter_string = "oauth_consumer_key=" + ConsumerKey
				+ "&oauth_nonce=" + oauth_nonce
				+ "&oauth_signature_method=" + oauth_signature_method
				+ "&oauth_timestamp=" + oauth_timestamp
				+ "&oauth_version=1.0";

		// specify the proper twitter API endpoint at which to direct this request
		String twitter_endpoint = "https://api.twitter.com/oauth/request_token";

		// assemble the string to be signed. It is METHOD & percent-encoded endpoint & percent-encoded parameter string
		// Java's native URLEncoder.encode function will not work. It is the wrong RFC specification (which does "+" where "%20" should be)...
		// the encode() function included in this class compensates to conform to RFC 3986 (which twitter requires)
		String signature_base_string = get_or_post + "&"+ encode(twitter_endpoint) + "&" + encode(parameter_string);

		// now that we've got the string we want to sign (see directly above) HmacSHA1 hash it against the consumer secret
		String oauth_signature = "";
		try {
			oauth_signature = computeSignature(signature_base_string, ConsumerSecret + "&");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// each request to the twitter API 1.1 requires an "Authorization: BLAH" header. The following is what BLAH should look like
		String authorization_header_string = "OAuth oauth_consumer_key=\"" + ConsumerKey
				+ "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + oauth_timestamp
				+ "\",oauth_nonce=\"" + oauth_nonce
				+ "\",oauth_version=\"1.0\",oauth_signature=\"" + encode(oauth_signature) + "\"";

		return new HttpHeader("Authorization",authorization_header_string);
	}
	private static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException
	{
	    SecretKey secretKey = null;

	    byte[] keyBytes = keyString.getBytes();
	    secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(secretKey);

	    byte[] text = baseString.getBytes();

	    return Base64.encodeToString(mac.doFinal(text),Base64.DEFAULT).trim();
	}


}