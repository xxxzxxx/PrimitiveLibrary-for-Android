package com.primitive.library.helper.device;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;

public class CameraHelper {
	private final Camera camera;

	public CameraHelper(final Camera camera){
		this.camera = camera;
	}

	/**
	 *
	 * @param camera
	 * @return
	 */
	public static boolean enableAutoFocus(final Camera camera){
		final Camera.Parameters params = camera.getParameters();
		return params.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_FIXED);
	}

	/**
	 *
	 * @param camera
	 * @return
	 */
	public static boolean enableZoom(final Camera camera){
		final Camera.Parameters params = camera.getParameters();
		return params.isZoomSupported();
	}

	/**
	 *
	 * @param camera
	 * @param callback
	 * @return
	 */
	public static boolean execAutoFocus(final Camera camera, final AutoFocusCallback callback){
		boolean result = enableAutoFocus(camera);
		if(result){
			camera.autoFocus(callback);
		}
		return result;
	}


	/**
	 *
	 * @param camera
	 * @param zoom
	 * @return
	 */
	public static boolean execZoom(final Camera camera, int zoom){
		final Camera.Parameters params = camera.getParameters();
		if (enableZoom(camera)) {
			if (params.getMaxZoom() >= zoom && zoom > -1) {
				params.setZoom(zoom);
				camera.setParameters(params);
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param zoom
	 * @return
	 */
	public boolean execZoom(final int zoom){
		return execZoom(zoom);
	}

	/**
	 *
	 * @return
	 */
	public boolean enableZoom(){
		return enableZoom(this.camera);
	}

	public boolean execAutoFocus(final AutoFocusCallback callback){
		return execAutoFocus(this.camera, callback);
	}

	public boolean enableAutoFocus(){
		return enableAutoFocus(this.camera);
	}

	public static int getNumberOfCameras(){
		return Camera.getNumberOfCameras();
	}
}
