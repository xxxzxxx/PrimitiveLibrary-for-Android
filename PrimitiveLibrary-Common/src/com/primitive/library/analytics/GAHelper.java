/**
 * GAHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.analytics;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 *
 * @author xxxzxxx
 *
 */
class GAHelper implements Analytics {
	@Override
	public void sndEvnt(final String category, final String action,final String label, final Long value) {
		Tracker t = EasyTracker.getTracker();
		t.sendEvent(category, action, label, value);
	}

	@Override
	public void sndEvntDebug(final String action, final String label,final Long value) {
		sndEvnt(Label.Category.Debug, action, label, value);
	}

	@Override
	public void sndEvntTrace(final String action, final String label,final Long value) {
		sndEvnt(Label.Category.Trace, action, label, value);
	}

	@Override
	public void sndEvntWarning(final String action, final String label,final Long value) {
		sndEvnt(Label.Category.Warm, action, label, value);
	}

	@Override
	public void sndEvntError(final String action, final String label,final Long value) {
		sndEvnt(Label.Category.Err, action, label, value);
	}

	@Override
	public void sndEvntFatal(final String action, final String label,final Long value) {
		sndEvnt(Label.Category.Fatal, action, label, value);
	}
	@Override
	public void sndEvntPerformance(final String action, final String label,final Long value) {
		sndEvnt(Label.Category.Performance, action, label, value);
	}

	@Override
	public void sndException(final Throwable ex, final boolean fatal) {
		Tracker t = EasyTracker.getTracker();
		t.sendException(Thread.currentThread().getName(), ex, fatal);
	}

	@Override
	public void sndTiming(String category, long intervalInMilliseconds,String name, String label) {
		Tracker t = EasyTracker.getTracker();
		t.sendTiming(category, intervalInMilliseconds, name, label);
	}

	@Override
	public void snd(final String hitType, final HashMap<String, String> params) {
		Tracker t = EasyTracker.getTracker();
		t.send(hitType, params);
	}

	@Override
	public BroadcastReceiver createInstallReceiver(Context ctx, Intent inten) {
		return null;
	}
}
