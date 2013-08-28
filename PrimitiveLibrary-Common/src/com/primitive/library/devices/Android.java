/**
 * Android
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.devices;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;

import com.primitive.library.common.Comparison;

/**
 * @author xxx
 *
 */
public class Android {
	private static Context context = null;
	private static SensorManager sensorManager = null;

	public static void init(Context context){
		Android.context = context;
		Android.sensorManager = (SensorManager)context.getSystemService(Activity.SENSOR_SERVICE);
	}

	/**
	 *
	 * @param comparisonVersion
	 * @param comparison
	 * @return
	 */
	public static boolean isComparisonVersion(final int comparisonVersion,final Comparison comparison){
		int thisVersion = Build.VERSION.SDK_INT;
		boolean result = false;
		switch (comparison) {
		case Greater:
			result = thisVersion > comparisonVersion;
			break;
		case Less:
			result = thisVersion < comparisonVersion;
			break;
		case EqualGreater:
			result = thisVersion >= comparisonVersion;
			break;
		case EqualLess:
			result = thisVersion <= comparisonVersion;
			break;
		case Equal:
			result = thisVersion == comparisonVersion;
			break;
		}
		return result;
	}

	public static SensorManager getSensors(){
		return sensorManager;
	}

	public static boolean hasSensor(){
		return false;
	}
	public static boolean hasCamera(){
		return false;
	}

	public static boolean hasNFC(){
		return false;
	}

	public static boolean hasAccelerometer(){
		return false;
	}
}
