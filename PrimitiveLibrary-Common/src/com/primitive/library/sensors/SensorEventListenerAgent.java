package com.primitive.library.sensors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class SensorEventListenerAgent<T> {
	public static SensorManager getSensorManager(final Context context)
	{
		return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	protected final SensorManager manager;
	private final SensorEventListener listnerImpl = makeBaseEventListner();
	private final List<SensorEventListener> listners = new ArrayList<SensorEventListener>();

	protected final List<T> notifications =
			Collections.synchronizedList(new ArrayList<T>());

	public void addNotification(final T notification)
	{
		notifications.add(notification);
	}

	public void removeNotification(final T notification)
	{
		notifications.remove(notification);
	}

	/**
	 *
	 * @param manager
	 * @throws SensorIsNotFoundException
	 */
	public SensorEventListenerAgent(final SensorManager manager)
			throws SensorIsNotFoundException, PurgingException {
		this.manager = manager;
		this.listners.add(listnerImpl);
		connect();
	}

	/**
	 *
	 * @param manager
	 * @throws SensorIsNotFoundException
	 */
	public SensorEventListenerAgent(final SensorManager manager,
			List<SensorEventListener> listners)
			throws SensorIsNotFoundException, PurgingException {
		this.manager = manager;
		this.listners.add(listnerImpl);
		this.listners.addAll(listners);
	}

	/**
	 * Finalizer Guardian
	 */
	private final Object finalizerGuardian = new Object() {
		@Override
		protected void finalize() throws Throwable {
			purge();
		}
	};

	/**
	 *
	 * @param type
	 * @param sensor
	 * @throws SensorIsNotFoundException
	 */
	protected final void registerSenserListener(final int type, final int sensor)
			throws SensorIsNotFoundException {
		final List<Sensor> sensors = manager.getSensorList(type);
		if (sensors.size() == 0) {
			throw new SensorIsNotFoundException(
					"Sensors are not equipped with necessary");
		}
		for (Sensor s : sensors) {
			for (SensorEventListener listner : listners) {
				this.manager.registerListener(listner, s, sensor);
			}
		}
	}

	/**
	 *
	 * @throws PurgingException
	 */
	public final void purge() throws PurgingException {
		for (SensorEventListener listner : listners) {
			this.manager.unregisterListener(listner);
		}
		purgeImpl();
	}

	/**
	 *
	 * @throws SensorIsNotFoundException
	 * @throws PurgingException
	 */
	public final void connect() throws SensorIsNotFoundException,
			PurgingException {
		try {
			purge();
			connectImpl();
		} catch (final SensorIsNotFoundException ex) {
			purge();
			throw ex;
		}
	};

	/**
	 *
	 * @return
	 */
	protected abstract SensorEventListener makeBaseEventListner();

	/**
	 *
	 */
	protected abstract void purgeImpl() throws PurgingException;

	/**
	 *
	 */
	protected abstract void connectImpl() throws SensorIsNotFoundException;

	/**
	 *
	 */
	@Override
	protected void finalize() throws Throwable {
		purge();
	}

}
