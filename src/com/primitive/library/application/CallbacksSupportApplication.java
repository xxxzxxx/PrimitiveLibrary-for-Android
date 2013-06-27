package com.primitive.library.application;

import com.uphyca.android.support.lifecyclecallbacks.LifecycleCallbacksSupportApplication;

public abstract class CallbacksSupportApplication extends LifecycleCallbacksSupportApplication {
	/**
	 *
	 */
    public CallbacksSupportApplication()
    {
        super();
        registerSupportActivityLifecycleCallbacks(createLifecycleCallbacks());
    }
    protected abstract ActivityLifecycleCallbacksCompat createLifecycleCallbacks();
}
