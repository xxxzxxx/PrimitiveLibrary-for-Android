/**
 * Logger
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper;

import android.os.Debug;
import android.util.Log;

import com.primitive.library.BuildConfig;

/**
 * Logger
 */
public class Logger {
	public static class Level {
		public static final Level Nothing = new Level(0);
		public static final Level Performance = new Level(10);
		public static final Level Error = new Level(20);
		public static final Level Warm = new Level(30);
		public static final Level Info = new Level(40);
		public static final Level Trace = new Level(50);
		public static final Level Debug = new Level(90);
		public static final Level All = new Level(100);
		private int value;

		Level(final int value) {
			this.value = value;
		}

		public static int comparison(Level compare, Level base) {
			return (compare.value - base.value);
		}
	}

	@SuppressWarnings("unused")
	private static Level LogLevel =
			BuildConfig.DEBUG == true
				? Level.Trace
				: Level.Error;

	public static void start() {
		if (Level.comparison(LogLevel, Logger.Level.Trace) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.i(currentTack.getClassName(), currentTack.getMethodName()
						+ " start");
		}
	}

	public static void end() {
		if (Level.comparison(LogLevel, Logger.Level.Trace) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.i(currentTack.getClassName(), currentTack.getMethodName()
						+ " end");
		}
	}

	public static void info(Object obj) {
		if (Level.comparison(LogLevel, Logger.Level.Info) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.i(currentTack.getClassName(), obj != null ? obj.toString()
						: "obj is null");
		}
	}

	public static void info(String msg) {
		if (Level.comparison(LogLevel, Logger.Level.Info) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null) {
				Log.i(currentTack.getClassName(), msg);
			}
		}
	}

	public static void err(Throwable ex) {
		if (Level.comparison(LogLevel, Logger.Level.Error) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.e(currentTack.getClassName(), ex.getMessage(), ex);
		}
	}

	public static void warm(String msg) {
		if (Level.comparison(LogLevel, Logger.Level.Warm) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.w(currentTack.getClassName(), msg);
		}
	}

	public static void warm(Throwable ex) {
		if (Level.comparison(LogLevel, Logger.Level.Warm) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.w(currentTack.getClassName(), ex.getStackTrace().toString());
		}
	}

	public static void debug(String msg) {
		if (Level.comparison(LogLevel, Logger.Level.Debug) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null) {
				Log.d(currentTack.getClassName(), msg);
			}
		}
	}

	public static void debug(long l) {
		if (Level.comparison(LogLevel, Logger.Level.Debug) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.d(currentTack.getClassName(), "" + l);
		}
	}

	public static void debug(Object obj) {
		if (Level.comparison(LogLevel, Logger.Level.Debug) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null)
				Log.d(currentTack.getClassName(), obj != null ? obj.toString()
						: "obj is null");
		}
	}
	public static void times(final long started) {
		final long endl = System.currentTimeMillis();
		if (Level.comparison(LogLevel, Logger.Level.Performance) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null) {
				String msg = "Process Time:" + (endl - started);
				Log.i(currentTack.getClassName(), msg);
			}
		}
	}
	public static void times(final long started,final long endl) {
		if (Level.comparison(LogLevel, Logger.Level.Performance) >= 0) {
			StackTraceElement currentTack = Thread.currentThread()
					.getStackTrace()[3];
			if (currentTack != null) {
				String msg = "Process Time:" + (endl - started);
				Log.i(currentTack.getClassName(), msg);
			}
		}
	}

	public static void heap() {
		if (Level.comparison(LogLevel, Logger.Level.Debug) >= 0) {
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