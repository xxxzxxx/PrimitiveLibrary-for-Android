package com.primitive.library.service;

import com.primitive.library.common.log.Logger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public abstract class OverlayerService extends Service {
	protected View view;
	protected WindowManager windowManager;
	protected View rootView;

	protected abstract View makeView();
	protected static View defaultView(final Context context)
	{
		FrameLayout frame = new FrameLayout(context);
		return frame;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		final long started = Logger.start();
		// LayoutInflater layoutInflater = LayoutInflater.from(this);
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		rootView = makeView();

		if (rootView != null) {
			windowManager.addView(rootView, new WindowManager.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_FULLSCREEN
							| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
					PixelFormat.TRANSLUCENT));
		}
		else
		{
			Logger.debug("rootView not found.");
		}
		Logger.end(started);
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		final long started = Logger.start();
		Logger.end(started);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		final long started = Logger.start();
		windowManager.removeView(view);
		Logger.end(started);
	}

	@Override
	public IBinder onBind(Intent intent) {
		final long started = Logger.start();
		Logger.end(started);
		return null;
	}
}
