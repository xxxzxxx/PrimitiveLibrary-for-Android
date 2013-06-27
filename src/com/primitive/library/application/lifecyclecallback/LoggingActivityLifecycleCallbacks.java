package com.primitive.library.application.lifecyclecallback;

import android.app.Activity;
import android.os.Bundle;

import com.primitive.library.helper.Logger;
import com.uphyca.android.support.lifecyclecallbacks.LifecycleCallbacksSupportApplication.ActivityLifecycleCallbacksCompat;

public class LoggingActivityLifecycleCallbacks implements ActivityLifecycleCallbacksCompat{

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		Logger.start();
	}

	@Override
	public void onActivityStarted(Activity activity) {
		Logger.start();
	}

	@Override
	public void onActivityResumed(Activity activity) {
		Logger.start();
	}

	@Override
	public void onActivityPaused(Activity activity) {
		Logger.start();
	}

	@Override
	public void onActivityStopped(Activity activity) {
		Logger.start();
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		Logger.start();
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		Logger.start();
	}

}
