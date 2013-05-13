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
