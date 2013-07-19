/**
 * BaseDataAccessObject
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.primitive.library.helper.Logger;

public class BaseDataAccessObject {
	/**
	 *
	 * @param context
	 * @param uri
	 * @param values
	 * @return
	 */
	public static Uri insert(final Context context,
			final Uri uri,
			final ContentValues values) {
		long start = Logger.start();
		Logger.debug(uri);
		Logger.debug(values);

		Uri result =  context.getContentResolver().insert(uri, values);

		Logger.end(start);
		return result;
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @param values
	 * @param id
	 * @return
	 */
	public static int update(final Context context,
			final Uri uri,
			final ContentValues values,
			final String id) {
		long start = Logger.start();
		Logger.debug(uri);
		Logger.debug(values);
		Logger.debug(id);

		int i = context.getContentResolver().update(
				uri,
				values,
				BaseColumns._ID + "=?",
				new String[] { id }
			);

		Logger.end(start);
		return i;
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @param id
	 * @return
	 */
	public static int delete(final Context context, final Uri uri,
			final String id) {
		long start = Logger.start();
		Logger.debug(uri);

		int i = context.getContentResolver().delete(
				uri,
				BaseColumns._ID + "=?",
				new String[] { id }
			);

		Logger.end(start);
		return i;
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static int deleteAll(final Context context, final Uri uri) {
		long start = Logger.start();
		Logger.debug(uri);

		int i = context.getContentResolver().delete(
				uri,
				null,
				null
			);

		Logger.end(start);
		return i;
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @param id
	 * @param projectiuon
	 * @return
	 */
	public static Cursor find(final Context context, final Uri uri,
			final String[] projectiuon, final String whereQuery,
			final String[] whereValue) {
		long start = Logger.start();
		Logger.debug(uri);
		Logger.debug(projectiuon);
		Logger.debug(whereQuery);
		Cursor cursor = context.getContentResolver().query(
				uri,
				projectiuon,
				whereQuery,
				whereValue,
				null
			);
		Logger.end(start);
		return cursor;
	}
}
