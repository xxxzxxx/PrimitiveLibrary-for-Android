/**
 * PausingTask
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.task;

import com.primitive.library.common.log.Logger;

import android.os.AsyncTask;

/**
 * @author xxx
 *
 */
public abstract class PausingTask extends AsyncTask<Void,Void,Void>{

	protected final long maxPausingTimeMillis;
	public PausingTask(long maxPausingTimeMillis){
		this.maxPausingTimeMillis = maxPausingTimeMillis;
	}

	protected abstract void doProcessFinished();
	protected abstract Void doProcess(Void... params);
	@Override
	protected Void doInBackground(Void... params) {
		long started =  System.currentTimeMillis();
		Void result = doProcess(params);
		long endl =  System.currentTimeMillis();
		long rest = maxPausingTimeMillis - (endl - started);
		if(rest > 0){
			try {
				Thread.sleep(rest);
			} catch (InterruptedException ex) {
				Logger.err(ex);
			}
		}
		doProcessFinished();
		return result;
	}
}
