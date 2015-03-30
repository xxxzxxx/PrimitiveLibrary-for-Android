package com.primitive.library.sensors;

import java.io.Serializable;
import java.util.List;

import com.primitive.library.common.log.Logger;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;

public class GyroscopeEventAgent extends SensorEventListenerAgent<GyroscopeEventAgent.GyroscopeEventNotification> {
	private static GyroscopeEventAgent instance;
	public static GyroscopeEventAgent createDefaultInstance(
			final Context context) throws SensorIsNotFoundException,
			PurgingException {
		return instance = (instance == null) ? new GyroscopeEventAgent(
				SensorEventListenerAgent.getSensorManager(context)) : instance;
	}

	public interface GyroscopeEventNotification {
		public void changeGyroscopeData(SensorEvent event,
				GyroscopeSenserData data);
	};

	public static GyroscopeEventAgent getDefaultInstance() {
		return instance;
	}

	/**
	 *
	 * @param manager
	 * @throws SensorIsNotFoundException
	 */
	public GyroscopeEventAgent(final SensorManager manager)
			throws SensorIsNotFoundException, PurgingException {
		super(manager);
	}

	/**
	 *
	 * @param manager
	 * @throws SensorIsNotFoundException
	 */
	public GyroscopeEventAgent(final SensorManager manager,
			List<SensorEventListener> listners)
			throws SensorIsNotFoundException, PurgingException {
		super(manager, listners);
	}

	/**
	 *
	 * @param manager
	 * @param notification
	 * @throws SensorIsNotFoundException
	 */
	public GyroscopeEventAgent(final SensorManager manager,
			final GyroscopeEventNotification notification)
			throws SensorIsNotFoundException, PurgingException {
		this(manager);
		addNotification(notification);
	}

	public class GyroscopeSenserData implements Serializable {
		private static final long serialVersionUID = 5401666620163818260L;
		public float x, y, z;

		GyroscopeSenserData(SensorEvent event) {
			x = event.values[SensorManager.DATA_X];
			y = event.values[SensorManager.DATA_Y];
			z = event.values[SensorManager.DATA_Z];
		}

		public void print() {
			Logger.debug("metrix:%5f,%5f,%5f", x, y, z);
		}
	};

	@Override
	protected SensorEventListener makeBaseEventListner() {
		return new SensorEventListener() {
			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1)
			{
			}

			@Override
			public void onSensorChanged(final SensorEvent event)
			{
				final GyroscopeSenserData data = new GyroscopeSenserData(event);
				for (final GyroscopeEventNotification notification : notifications)
				{
					AsyncTask<?,?,?> task = new AsyncTask<Object,Object,Object>()
					{
						@Override
						protected Object doInBackground(Object... arg0) {
							notification.changeGyroscopeData(event, data);
							return null;
						}
					};
					task.execute();
				}
			}
		};
	}

	@Override
	protected void purgeImpl() throws PurgingException {
	}

	@Override
	protected void connectImpl() throws SensorIsNotFoundException {
		registerSenserListener(Sensor.TYPE_GYROSCOPE,
				SensorManager.SENSOR_DELAY_UI);
	}

}
