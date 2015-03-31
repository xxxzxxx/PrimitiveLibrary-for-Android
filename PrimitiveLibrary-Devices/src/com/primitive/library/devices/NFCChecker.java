package com.primitive.library.devices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.NfcAdapter;

@SuppressLint("NewApi")
/**
 * <uses-permission android:name="android.permission.NFC"/>
 *
 * @author xxx
 */
public class NFCChecker {
	public enum NFCStatus{
		Notfound,
		Enable,
		Disable,
	}

	public static NFCStatus isDevice(final Context context){
		final NfcAdapter adapter = NfcAdapter.getDefaultAdapter(context);
		if(adapter == null){
			return NFCStatus.Notfound;
		}
		return adapter.isEnabled() ? NFCStatus.Enable: NFCStatus.Disable;
	}

	public static NfcAdapter getAdapter(final Context context){
		return NfcAdapter.getDefaultAdapter(context);
	}
}
