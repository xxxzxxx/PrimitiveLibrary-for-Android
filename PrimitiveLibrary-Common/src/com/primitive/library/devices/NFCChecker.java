/**
 * NFCChecker
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
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
