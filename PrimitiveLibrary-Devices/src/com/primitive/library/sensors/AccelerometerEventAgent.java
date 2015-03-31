package com.primitive.library.sensors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.primitive.library.common.log.Logger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerEventAgent
		extends
		SensorEventListenerAgent<AccelerometerEventAgent.AccelerometerEventNotification> {
	public static final int Position_Stand = 1, Position_Flat = 2,
			Position_Flex = 3, Orientation_Landscape = 4, Inverse = 8, Unknown = 0;

	public enum Orientation {
		Landscape, Portrait,
	}

	public enum Position {
		Flex, Flat, Stand,
	}

	private AccelerometerSenserData current = new AccelerometerSenserData();
	private AccelerometerSenserData before = new AccelerometerSenserData();

	private static AccelerometerEventAgent instance;

	public static AccelerometerEventAgent createDefaultInstance(
			final Context context) throws SensorIsNotFoundException,
			PurgingException {
		return instance = (instance == null) ? new AccelerometerEventAgent(
				SensorEventListenerAgent.getSensorManager(context)) : instance;
	}

	public static AccelerometerEventAgent getDefaultInstance() {
		return instance;
	}

	protected static boolean is(double t, double comp, double threshold) {
		double nt = t < 0 ? 0 - t : t;
		return (nt >= (comp - threshold) && nt <= (comp + threshold));
	}

	public int getStandingPosition() {
		return before.getStandingPosition();
	}

	public static int getStandingPosition(final double[] degreeies) {
		int standingPosition = 0;
		if (degreeies == null)
		{
			return Unknown;
		}
		if (degreeies.length != 3)
		{
			return Unknown;
		}
		final double x = degreeies[0], y = degreeies[1], z = degreeies[2];

		Logger.debug("degreeies :%f,%f,%f", x, y, z);

		if (is(y, 0, 4) && (is(z, 0, 4) || is(z, 180, 4))) {
			Logger.debug("Position_Flat");
			standingPosition |= Position_Flat;
			standingPosition |= z < 0 ? Inverse : 0;
		} else if (is(y, 0, 15) && is(z, 90, 30)) {
			Logger.debug("Position_Stand portraio");
			standingPosition |= Position_Stand;
			standingPosition |= z < 0 ? Inverse : 0;
		} else if (is(y, 90, 30)
		// && is(z,0,10)
		) {
			Logger.debug("Position_Stand landscape");
			standingPosition |= Position_Stand;
			standingPosition |= Orientation_Landscape;
			standingPosition |= y < 0 ? Inverse : 0;
		} else {
			Logger.debug("Position_Flex");
			standingPosition |= Position_Flex;
		}
		return standingPosition;
	}

	public Position getPosition() {
		return before.getPosition();
	}

	public static Position getPosition(final int standingPosition) {
		final boolean isFlex = (Position_Flex & standingPosition) == Position_Flex, isStand = (Position_Stand & standingPosition) == Position_Stand;
		return (isFlex) ? Position.Flex : ((isStand) ? Position.Stand
				: Position.Flat);
	}

	public interface AccelerometerEventNotification {
		public void changeAccelerometer(SensorEvent event,
				AccelerometerSenserData data);
	};

	public class AccelerometerSenserData implements Serializable, Cloneable {
		private static final long serialVersionUID = 4908439139590079072L;
		/**  */
		public final float[] rotationMatrix = new float[9];
		/**  */
		public final float[] attitude = new float[3];
		/**  */
		public float[] gravity = new float[3];
		/**  */
		public float[] geomagnetic = new float[3];
		/**  */
		public float[] orientation = new float[3];
		/**  */
		public double[] degreeies;

		/**
		 *
		 * @return
		 */
		boolean getRotationMatrix() {
			final boolean result = SensorManager.getRotationMatrix(
					rotationMatrix, null, gravity, geomagnetic);
			return result;
		}

		/**
		 *
		 * @return
		 */
		public double[] getOrientationDegreess() {
			degreeies = null;
			orientation = new float[3];
			if (getRotationMatrix()) {
				SensorManager.getOrientation(rotationMatrix, orientation);
				degreeies = new double[orientation.length];
				for (int i = 0; i < orientation.length; i++) {
					degreeies[i] = Math.floor(Math.toDegrees(orientation[i]));
				}
			}
			return degreeies;
		}

		public int getStandingPosition() {
			return AccelerometerEventAgent.getStandingPosition(degreeies);
		}

		public Position getPosition() {
			final int standingPosition = getStandingPosition();
			return AccelerometerEventAgent.getPosition(standingPosition);
		}

		@Override
		public AccelerometerSenserData clone() {
			try {
				return (AccelerometerSenserData) super.clone();
			} catch (final CloneNotSupportedException ex) {
				throw new InternalError(ex.getMessage());
			}
		}
	};

	/**
	 *
	 * @param manager
	 * @throws SensorIsNotFoundException
	 */
	public AccelerometerEventAgent(final SensorManager manager)
			throws SensorIsNotFoundException, PurgingException {
		super(manager);
	}

	/**
	 *
	 * @param manager
	 * @throws SensorIsNotFoundException
	 */
	public AccelerometerEventAgent(final SensorManager manager,
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
	public AccelerometerEventAgent(final SensorManager manager,
			final AccelerometerEventNotification notification)
			throws SensorIsNotFoundException, PurgingException {
		this(manager);
		final long started = Logger.start();
		addNotification(notification);
		Logger.end(started);
	}

	@Override
	protected SensorEventListener makeBaseEventListner() {
		return new SensorEventListener() {
			@Override
			public final void onAccuracyChanged(Sensor sensor, int accuracy) {
			}

			@Override
			public final void onSensorChanged(final SensorEvent event) {
				synchronized (this) {
					final int sensorType = event.sensor.getType();
					try {
						switch (sensorType) {
						case Sensor.TYPE_MAGNETIC_FIELD:
							current.geomagnetic = event.values.clone();
							break;
						case Sensor.TYPE_ACCELEROMETER:
							current.gravity = event.values.clone();
							break;
						default:
							Logger.debug("SensorType:Unknown [%d]", sensorType);
							break;
						}
						if (Sensor.TYPE_MAGNETIC_FIELD == sensorType) {
							double[] b_result = before.getOrientationDegreess();
							double[] c_result = current
									.getOrientationDegreess();
							b_result = b_result != null ? b_result
									: new double[3];
							c_result = c_result != null ? c_result
									: new double[3];

							if (!Arrays.equals(b_result, c_result)) {
								if (notifications != null) {
									for (final AccelerometerEventNotification notification : notifications) {
										if (notification != null) {
											notification.changeAccelerometer(
													event, current);
										}
									}
								}
							}
							before = current;
							current = new AccelerometerSenserData();
						}
					} catch (final Throwable ex) {
						Logger.err(ex);
					} finally {
					}
				}
			}
		};
	}

	@Override
	protected void connectImpl() throws SensorIsNotFoundException {
		registerSenserListener(Sensor.TYPE_ACCELEROMETER,
				SensorManager.SENSOR_ACCELEROMETER);
		registerSenserListener(Sensor.TYPE_MAGNETIC_FIELD,
				SensorManager.SENSOR_MAGNETIC_FIELD);
	}

	@Override
	protected void purgeImpl() throws PurgingException {
	}
}
