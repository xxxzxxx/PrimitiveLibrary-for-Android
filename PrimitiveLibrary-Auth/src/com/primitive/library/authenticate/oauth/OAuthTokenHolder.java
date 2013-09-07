package com.primitive.library.authenticate.oauth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import com.primitive.library.common.log.Logger;
import com.primitive.library.helper.serialize.Serializer;
import com.primitive.library.helper.serialize.SerializerImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class OAuthTokenHolder implements Serializable {
	private static final long serialVersionUID = -1863197944692291736L;
	private SharedPreferences sharedPreferences;
	private static Serializer serializer = new SerializerImpl();
	private static OAuthTokenHolder instance = null;
	private HashMap<String,OAuthToken> store = new HashMap<String, OAuthToken>();

	/**
	 * @return
	 */
	public static OAuthTokenHolder initInstance(Context context){
		SharedPreferences sharedPreferences=
				context.getSharedPreferences(
						OAuthTokenHolder.class.getSimpleName(),
						Context.MODE_PRIVATE
				);
		return instance == null ? OAuthTokenHolder.load(sharedPreferences) : null;
	}

	/**
	 * @param context
	 * @return
	 */
	public static OAuthTokenHolder initInstance(SharedPreferences sharedPreferences){
		if(sharedPreferences == null){
			throw new IllegalArgumentException();
		}
		return instance == null ? OAuthTokenHolder.load(sharedPreferences) : null;
	}

	public void save(){
		OAuthTokenHolder.save(this,sharedPreferences);
	}

	/**
	 * @param sharedPreferences
	 * @return
	 */
	public static void save(OAuthTokenHolder holder,SharedPreferences sharedPreferences){
		if(sharedPreferences == null || holder == null){
			throw new IllegalArgumentException();
		}
		String encodedData = null;
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			serializer.serialize(output,holder);
			byte[] serializeData = output.toByteArray();
			encodedData = Base64.encodeToString(serializeData, 0);
		} catch (Throwable ex) {
			Logger.err(ex);
		} finally {
			Editor editor = sharedPreferences.edit();
			editor.putString(OAuthTokenHolder.class.getSimpleName(), encodedData);
			editor.commit();
		}
	}

	/**
	 * @param sharedPreferences
	 * @return
	 */
	public static OAuthTokenHolder load(SharedPreferences sharedPreferences){
		if(sharedPreferences == null){
			throw new IllegalArgumentException();
		}
		String OAuthTokenHolderString = sharedPreferences.getString(OAuthTokenHolder.class.getSimpleName(), null);
		OAuthTokenHolder holder = null;
		if(OAuthTokenHolderString != null){
			try {
				byte[] OAuthTokenHolderData = Base64.decode(OAuthTokenHolderString.getBytes("utf-8"), Base64.DEFAULT);
				ByteArrayInputStream input = new ByteArrayInputStream(OAuthTokenHolderData);
				holder = (OAuthTokenHolder)serializer.deserialize(input);
			} catch (Throwable ex) {
				Editor editor = sharedPreferences.edit();
				editor.putString(OAuthTokenHolder.class.getSimpleName(), null);
				editor.commit();
				Logger.err(ex);
			}
		}
		holder = holder != null ? holder : new OAuthTokenHolder();
		holder.sharedPreferences = sharedPreferences;
		return holder;
	}

	/**
	 *
	 * @return
	 */
	public static OAuthTokenHolder getInstance(){
		if(instance == null){
			throw new RuntimeException();
		}
		return instance;
	}
}
