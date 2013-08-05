/**
 * LoggingActivityLifecycleCallbacks
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.application.lifecyclecallback;

import android.app.Activity;
import android.os.Bundle;

import com.primitive.library.common.log.Logger;
import com.uphyca.android.support.lifecyclecallbacks.LifecycleCallbacksSupportApplication.ActivityLifecycleCallbacksCompat;

/**
 * 
 * @author xxxzxxx
 * 
 */
public class LoggingActivityLifecycleCallbacks implements
		ActivityLifecycleCallbacksCompat {

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
