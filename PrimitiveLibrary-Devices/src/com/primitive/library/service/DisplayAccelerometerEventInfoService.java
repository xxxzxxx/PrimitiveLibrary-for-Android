package com.primitive.library.service;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.view.View;

import com.primitive.library.common.log.Logger;
import com.primitive.library.sensors.AccelerometerEventAgent;
import com.primitive.library.sensors.AccelerometerEventAgent.AccelerometerSenserData;

public abstract class DisplayAccelerometerEventInfoService extends OverlayerService
		implements AccelerometerEventAgent.AccelerometerEventNotification
{
	public class DefaultDisplayAccelerometerEventInfoService extends DisplayAccelerometerEventInfoService
	{
		@Override
		public void changeAccelerometer(SensorEvent event,
				AccelerometerSenserData data) {
			final long started = Logger.start();
			Logger.end(started);
		}

		@Override
		protected View makeView()
		{
			return defaultView(this.getApplicationContext());
		}
	}

	private AccelerometerEventAgent agent;

	@Override
	public void onCreate() {
		super.onCreate();
		final long started = Logger.start();
		agent = AccelerometerEventAgent.getDefaultInstance();
		if (agent != null) {
			agent.addNotification(this);
		}
		Logger.end(started);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		final long started = Logger.start();
		if (agent != null) {
			agent.removeNotification(this);
		}
		Logger.end(started);
	}

	@Override
	public IBinder onBind(Intent intent) {
		final long started = Logger.start();
		Logger.end(started);
		return null;
	}

}
