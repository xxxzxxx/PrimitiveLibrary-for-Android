/**
 * AnsyncBoostrapReceiver
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

/**
 * AnsyncBoostrapReceiver
 *
 * Manifest.xml adding
 * <receiver
 *   android:name="YourAnsyncBoostrapReceiver">
 *   <intent-filter>
 *     <action android:name="android.intent.action.BOOT_COMPLETED"/>
 *   </intent-filter>
 * </receiver>
 *
 * @author xxx
 *
 */
public abstract class AnsyncBoostrapReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					onBoot(context);
				}
			});
			thread.start();
		}
	}

	protected abstract void onBoot(Context context);
}
