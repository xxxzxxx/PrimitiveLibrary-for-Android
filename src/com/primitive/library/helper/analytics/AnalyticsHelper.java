package com.primitive.library.helper.analytics;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public interface AnalyticsHelper {

	public void sndEvnt(
			final String category,
			final String action,
			final String label,
			final Long value
	);
	public void sndEvntD(
			final String action,
			final String label,
			final Long value
	);
	public void sndEvntT(
			final String action,
			final String label,
			final Long value
	);
	public void sndEvntW(
			final String action,
			final String label,
			final Long value
	);
	public void sndEvntE(
			final String action,
			final String label,
			final Long value
	);
	public void sndEvntF(
			final String action,
			final String label,
			final Long value
	);
	public void sndException(
			final Throwable ex,
			final boolean fatal
	);
	public void sndTiming(
			String category,
			long intervalInMilliseconds,
			String name,
			String label
	);

	public BroadcastReceiver createInstallReceiver(
			Context ctx,
			Intent inten);

	public void snd(
			final String hitType,
			final HashMap<String,String> params
	);
}
