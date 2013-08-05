/**
 * BoostrapActivity
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

/**
 * 
 * @author xxxzxxx
 * 
 */
public abstract class BoostrapActivity extends BaseActivity {
	private AsyncTask<Object, Integer, Long> _boostrapTask;
	private Handler _handler = new Handler();
	protected long _waitDisplayTime = 500;

	protected abstract AsyncTask<Object, Integer, Long> createBoostrapTask();

	protected abstract Class<Activity> getNextActivity();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_boostrapTask = createBoostrapTask();
		if (_boostrapTask != null) {
			_boostrapTask.execute();
		} else {
			_handler.postAtTime(new DefaultSplashHandler(), _waitDisplayTime);
		}
	}

	/**
	 * @author xxx
	 */
	class DefaultSplashHandler implements Runnable {
		@Override
		public void run() {
			Intent i = new Intent(BoostrapActivity.this.getApplication(),
					BoostrapActivity.this.getNextActivity());
			startActivity(i);
			BoostrapActivity.this.finish();
		}
	}
}
