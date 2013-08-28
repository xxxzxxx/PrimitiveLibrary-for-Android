/**
 * AnalyticsHelper
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

/**
 *
 * @author xxxzxxx
 *
 */
public interface Analytics {

	public void sndEvnt(final String category, final String action,final String label, final Long value);
	public void sndEvntDebug(final String action, final String label,final Long value);
	public void sndEvntTrace(final String action, final String label,final Long value);
	public void sndEvntWarning(final String action, final String label,final Long value);
	public void sndEvntError(final String action, final String label,final Long value);
	public void sndEvntFatal(final String action, final String label,final Long value);
	public void sndException(final Throwable ex, final boolean fatal);
	public void sndEvntPerformance(final String action, final String label,final Long value);
	public void sndTiming(String category, long intervalInMilliseconds,String name, String label);
	public BroadcastReceiver createInstallReceiver(Context ctx, Intent inten);
	public void snd(final String hitType, final HashMap<String, String> params);
}
