/**
 * CallbacksSupportApplication
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.application;

import com.uphyca.android.support.lifecyclecallbacks.LifecycleCallbacksSupportApplication;

/**
 * 
 * @author xxxzxxx
 * 
 */
public abstract class CallbacksSupportApplication extends
		LifecycleCallbacksSupportApplication {
	/**
	 *
	 */
	public CallbacksSupportApplication() {
		super();
		registerSupportActivityLifecycleCallbacks(createLifecycleCallbacks());
	}

	protected abstract ActivityLifecycleCallbacksCompat createLifecycleCallbacks();
}
