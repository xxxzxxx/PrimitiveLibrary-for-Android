package com.primitive.library.service;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.view.View;

import com.primitive.library.common.log.Logger;
import com.primitive.library.sensors.GyroscopeEventAgent;
import com.primitive.library.sensors.GyroscopeEventAgent.GyroscopeSenserData;

public abstract class DisplayGyroscopeEventInfoService
extends OverlayerService
implements GyroscopeEventAgent.GyroscopeEventNotification
{
	public class DefaultDisplayGyroscopeEventInfoService extends DisplayGyroscopeEventInfoService
	{

		@Override
		public void changeGyroscopeData(SensorEvent event,GyroscopeSenserData data)
		{

		}

		@Override
		protected View makeView()
		{
			return defaultView(this.getApplicationContext());
		}

	}
	private GyroscopeEventAgent agent;
	@Override
	public void onCreate()
	{
		super.onCreate();
		agent = GyroscopeEventAgent.getDefaultInstance();
		if (agent != null)
		{
			agent.addNotification(this);
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (agent != null)
		{
			agent.removeNotification(this);
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		final long started = Logger.start();
		Logger.end(started);
		return null;
	}
}
