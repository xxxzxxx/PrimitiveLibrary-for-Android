/**
 * Logger
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.common.log;

import android.os.Debug;
import android.util.Log;

import com.primitive.library.BuildConfig;
import com.primitive.library.analytics.Analytics;
import com.primitive.library.analytics.AnalyticsFactory;
import com.primitive.library.common.Comparison;

/**
 * Logger
 */
public class Logger {
	public static class Level {
		public static final Comparison ComparisonMode = Comparison.Less;
		public static final Level Nothing = new Level(0);
		public static final Level Error = new Level(20);
		public static final Level Warm = new Level(30);
		public static final Level Performance = new Level(40);
		public static final Level Info = new Level(50);
		public static final Level Trace = new Level(60);
		public static final Level Debug = new Level(90);
		public static final Level All = new Level(100);

		private int value;

		private Level(final int value) {
			this.value = value;
		}

		public static int comparison(Level compare, Level base) {
			int result = 0;
			if (ComparisonMode == Comparison.Greater){
				result = (compare.value < base.value) ? 0 : -1;
			}else if(ComparisonMode == Comparison.Less){
				result = (compare.value > base.value) ? 0 : -1;
			}else if(ComparisonMode == Comparison.Equal){
				result = (compare.value == base.value) ? 0 : -1;
			}
			return result;
		}
		public int comparison(Level base) {
			int result = 0;
			if (ComparisonMode == Comparison.Greater){
				result = (this.value < base.value) ? 0 : -1;
			}else if(ComparisonMode == Comparison.Less){
				result = (this.value > base.value) ? 0 : -1;
			}else if(ComparisonMode == Comparison.Equal){
				result = (this.value == base.value) ? 0 : -1;
			}
			return result;
		}
	}

	//パフォーマンスカウンタの秒単位定義
	enum TimeType{
		nano,
		mill,
		micro,
	}

	//ログレベルの設定
	private static final Level LogLevel;
	//Analyticsの設定
	private static final Analytics analytics;
	//rパフォーマンスカウンタ秒
	private static long LimitSecond;
	//パフォーマンスチェック用リミッター
	private static long TimeOverLimiter;
	//パフォーマンスカウンタの秒単位
	private static final TimeType timeType;

	static {
		timeType = TimeType.nano;
		LogLevel =
				BuildConfig.DEBUG
					? Level.All
					: Level.Error;
		analytics =
				BuildConfig.DEBUG
					? null
					: AnalyticsFactory.getHelper(AnalyticsFactory.AnalyticsType.GoogleAnalytics);
		LimitSecond = 10; //10sec
		switch (timeType){
		case nano:
			TimeOverLimiter = LimitSecond * 1000000000L;
			break;
		case mill:
			TimeOverLimiter = LimitSecond * 1000000L;
			break;
		case micro:
		default:
			TimeOverLimiter = LimitSecond * 1000L;
			break;
		}
	}
	//共通時間呼び出し用関数
	private static long getSec(){
		switch (timeType){
		case nano:
			return System.nanoTime();
		case mill:
			return System.currentTimeMillis();
		case micro:
		default:
			return System.currentTimeMillis() / 1000;
		}
	}

	public static long start() {
		long started =  getSec();
		StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
		if (LogLevel.comparison(Logger.Level.Trace) >= 0) {
			if (currentTack != null)
				Log.i(currentTack.getClassName(), currentTack.getMethodName()+ " start");
		}
		return started;
	}

	public static long end() {
		long end =  getSec();
		StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
		if (LogLevel.comparison( Logger.Level.Trace) >= 0) {
			if (currentTack != null){
				Log.i(currentTack.getClassName(), currentTack.getMethodName() + " end");
			}
		}
		return end;
	}

	public static long end(final long started) {
		final long end =  getSec();
		final long processTime = (end - started);
		final StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
		if (LogLevel.comparison( Logger.Level.Performance) >= 0) {
			if (currentTack != null){
				//リミットを越した場合は警告としてログを出力
				if(processTime > TimeOverLimiter){
					Log.w(currentTack.getClassName(), currentTack.getMethodName()+ " end["+ processTime +"ns]");
					//リミットを越した場合はanalyticsに報告
					if(analytics != null){
						analytics.sndEvntPerformance(currentTack.getClassName(), currentTack.getMethodName(), (end - started));
					}
				}else{
					Log.i(currentTack.getClassName(), currentTack.getMethodName()+ " end["+ processTime +"ns]");
				}
			}
		}
		else if (LogLevel.comparison( Logger.Level.Info) >= 0) {
			if (currentTack != null){
				Log.i(currentTack.getClassName(), currentTack.getMethodName()+ " end["+ processTime +"ns]");
			}
		}
		return end;
	}

	public static void info(Object obj) {
		StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
		if (LogLevel.comparison( Logger.Level.Info) >= 0) {
			if (currentTack != null){
				Log.i(currentTack.getClassName(), obj != null ? obj.toString() : "obj is null");
			}
		}
	}

	public static void info(String msg) {
		StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
		if (LogLevel.comparison( Logger.Level.Info) >= 0) {
			if (currentTack != null) {
				Log.i(currentTack.getClassName(), msg);
			}
		}
	}

	public static void err(Throwable ex) {
		StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
		if (LogLevel.comparison( Logger.Level.Error) >= 0) {
			if (currentTack != null){
				Log.e(currentTack.getClassName(), ex.getMessage(), ex);
				if(analytics != null){
					analytics.sndEvntError(currentTack.getClassName(), currentTack.getMethodName() + ":" + ex.getStackTrace().toString(), getSec());
				}
			}
		}
	}

	public static void warm(String msg) {
		if (LogLevel.comparison( Logger.Level.Warm) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null){
				Log.w(currentTack.getClassName(), msg);
				if(analytics != null){
					analytics.sndEvntWarning(currentTack.getClassName(), currentTack.getMethodName() + ":" + msg, getSec());
				}
			}
		}
	}

	public static void warm(Throwable ex) {
		if (LogLevel.comparison( Logger.Level.Warm) >= 0) {
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if (currentTack != null){
				Log.w(currentTack.getClassName(), ex.getStackTrace().toString());
				if(analytics != null){
					analytics.sndEvntWarning(currentTack.getClassName(), currentTack.getMethodName() + ":" + ex.getStackTrace().toString(), getSec());
				}
			}
		}
	}

	public static void debug(String msg) {
		if (LogLevel.comparison( Logger.Level.Debug) >= 0) {
			StackTraceElement currentTack = Thread.currentThread().getStackTrace()[3];
			if (currentTack != null) {
				Log.d(currentTack.getClassName(), msg != null ? msg : "empty strings");
			}
		}
	}

	public static void debug(long l) {
		if (LogLevel.comparison( Logger.Level.Debug) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null){
				Log.d(currentTack.getClassName(), "" + l);
			}
		}
	}

	public static void debug(Object obj) {
		if (LogLevel.comparison( Logger.Level.Debug) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null){
				Log.d(currentTack.getClassName(), obj != null ? obj.toString() : "obj is null");
			}
		}
	}
	public static void times(final long started) {
		final long endl = getSec();
		if (LogLevel.comparison( Logger.Level.Performance) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null) {
				String msg = "Process Time:" + (endl - started);
				Log.i(currentTack.getClassName(), msg);
			}
		}
	}
	public static void times(final long started,final long endl) {
		if (LogLevel.comparison( Logger.Level.Performance) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null) {
				String msg = "Process Time:" + (endl - started);
				Log.i(currentTack.getClassName(), msg);
			}
		}
	}

	public static void heap() {
		if (LogLevel.comparison( Logger.Level.Performance) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			String msg = "heap : Free="
					+ Long.toString(Debug.getNativeHeapFreeSize() / 1024)
					+ "kb" + "\n Allocated="
					+ Long.toString(Debug.getNativeHeapAllocatedSize() / 1024)
					+ "kb" + "\n Size="
					+ Long.toString(Debug.getNativeHeapSize() / 1024) + "kb";
			if (currentTack != null)
				Log.v(currentTack.getClassName(), msg);
		}
	}
}