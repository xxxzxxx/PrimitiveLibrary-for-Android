package com.primitive.library.devices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.NfcAdapter;

@SuppressLint("NewApi")
/**
 *  <uses-permission android:name="android.permission.CAMERA"/>
 *
 * @author xxx
 */
public class CameraChecker {
	enum CamraStatus{
		Notfound,
		Enable,
		Disable,
	}

	public static CamraStatus isDevice(final Context context){
		final NfcAdapter adapter = NfcAdapter.getDefaultAdapter(context);
		if(adapter == null){
			return CamraStatus.Notfound;
		}
		return adapter.isEnabled() ? CamraStatus.Enable: CamraStatus.Disable;
	}

	public static NfcAdapter getAdapter(final Context context){
		return NfcAdapter.getDefaultAdapter(context);
	}
}
