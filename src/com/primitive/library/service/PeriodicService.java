/**
 * PeriodicService
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.service;

import com.primitive.library.helper.Logger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Manifest.xml adding
 * <service android:name="com.primitive.library.service.PeriodicService"/>
 * @author xxx
 *
 */
public abstract class PeriodicService extends Service {
	protected abstract void execute();
	private long intervalMS = 0;

	public PeriodicService(final long intervalMS){
		super();
		Logger.start();
		this.intervalMS = intervalMS;
	}

	protected final Binder binder = new Binder() {
		@Override
		protected boolean onTransact( int code, Parcel data, Parcel reply, int flags ) throws RemoteException{
			Logger.start();
			return super.onTransact(code, data, reply, flags);
		}
	};
	private AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

	@Override
	public IBinder onBind(Intent intent) {
		Logger.start();
		return binder;
	}

	private Thread ansyncdTask = null;

	@Override
	public void onStart(Intent intent, int startId) {
		Logger.start();
		super.onStart(intent, startId);
		if(ansyncdTask != null){
			ansyncdTask = new Thread(
				new Runnable(){
					public void run() {
						execute();
						scheduleNextTime();
					}
				}
			);
		}
		if(!ansyncdTask.isAlive()){
			ansyncdTask.start();
		}else{
			//TODO:
		}
	}

	protected void scheduleNextTime() {
		Logger.start();
		long now = System.currentTimeMillis();
		PendingIntent alarmSender = PendingIntent.getService(this,0,new Intent(this, this.getClass()),0);
		alarmManager.set(AlarmManager.RTC,now + intervalMS,alarmSender);
	}

	public PeriodicService start(Context context){
		Logger.start();
		Intent intent = new Intent(context, this.getClass());
		intent.putExtra("type", "start");
		context.startService(intent);
		return this;
	}

	public void stop(Context context){
		Logger.start();
		Intent intent = new Intent(context, this.getClass());
		PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
		stopSelf();
	}
}