package com.primitive.library.helper.device;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;

import com.primitive.library.common.Comparison;

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
