package com.primitive.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public abstract class BoostrapActivity extends BaseActivity{
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
		}else{
			_handler.postAtTime(
				new DefaultSplashHandler(),
				_waitDisplayTime);
	}
	}

	/**
	 * @author xxx
	 */
	class DefaultSplashHandler implements Runnable {
		@Override
		public void run() {
			Intent i = new Intent(
					BoostrapActivity.this.getApplication(),
					BoostrapActivity.this.getNextActivity()
					);
			startActivity(i);
			BoostrapActivity.this.finish();
		}
	}
}
