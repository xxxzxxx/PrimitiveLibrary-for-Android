/**
 * BoostrapReceiver
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class BoostrapReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		if( Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
			Thread thread = new Thread(new Runnable(){
				public void run() {
					onBoot(context);
				}
			});
			thread.start();
		}
	}
	protected abstract void onBoot(Context context);
}
