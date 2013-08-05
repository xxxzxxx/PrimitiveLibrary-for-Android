/**
 * InstallReceiver
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.primitive.library.common.log.Logger;

/**
 * InstallReceiver
 * 
 * Manifest.xml adding <receiver
 * android:name="com.primitive.library.receiver.InstallReceiver">
 * <intent-filter> <action android:name="android.intent.action.PACKAGE_ADDED" />
 * <data android:scheme="package" /> </intent-filter> </receiver>
 * 
 * @author xxx
 * 
 */
public abstract class InstallReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
			PackageManager pm = context.getPackageManager();
			String packageName = intent.getDataString().split(":")[1];
			PackageInfo packageInfo = null;
			try {
				packageInfo = pm.getPackageInfo(packageName,
						PackageManager.GET_PERMISSIONS);
			} catch (NameNotFoundException ex) {
				Logger.warm(ex);
				return;
			}
			notification(context, intent, packageInfo, packageName);
		}
	}

	/**
	 * 
	 * @param context
	 * @param intent
	 * @param packageInfo
	 * @param packageName
	 */
	protected abstract void notification(Context context, Intent intent,
			PackageInfo packageInfo, String packageName);
}
