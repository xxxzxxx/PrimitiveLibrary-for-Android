package com.primitive.library.helper.analytics;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class GAHelper implements AnalyticsHelper{
	@Override
	public void sndEvnt(
			final String category,
			final String action,
			final String label,
			final Long value
	){
		Tracker t = EasyTracker.getTracker();
		t.sendEvent(category, action, label, value);
	}
	@Override
	public void sndEvntD(
			final String action,
			final String label,
			final Long value
	){
		sndEvnt(Label.Category.Debug, action, label, value);
	}
	@Override
	public void sndEvntT(
			final String action,
			final String label,
			final Long value
	){
		sndEvnt(Label.Category.Trace, action, label, value);
	}
	@Override
	public void sndEvntW(
			final String action,
			final String label,
			final Long value
	){
		sndEvnt(Label.Category.Warm, action, label, value);
	}
	@Override
	public void sndEvntE(
			final String action,
			final String label,
			final Long value
	){
		sndEvnt(Label.Category.Err, action, label, value);
	}
	@Override
	public void sndEvntF(
			final String action,
			final String label,
			final Long value
	){
		sndEvnt(Label.Category.Fatal, action, label, value);
	}
	@Override
	public void sndException(
			final Throwable ex,
			final boolean fatal
	){
		Tracker t = EasyTracker.getTracker();
		t.sendException(
				Thread.currentThread().getName(),
				ex,
				fatal);
	}
	@Override
	public void sndTiming(
			String category,
			long intervalInMilliseconds,
			String name,
			String label
	){
		Tracker t = EasyTracker.getTracker();
		t.sendTiming(
				category,
				intervalInMilliseconds,
				name,
				label
				);
	}

	@Override
	public void snd(
			final String hitType,
			final HashMap<String,String> params
	){
		Tracker t = EasyTracker.getTracker();
		t.send(
				hitType,
				params
		);
	}
	@Override
	public BroadcastReceiver createInstallReceiver(Context ctx, Intent inten) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
