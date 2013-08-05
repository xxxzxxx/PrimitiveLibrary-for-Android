package com.primitive.library.content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

/**
 * @author xxx
 */
public class TelePhone {
	/**
	 *
	 * @param context
	 * @param projection
	 * @param sortOrder
	 * @return
	 */
	public static Cursor missedCalls(
			final Context context,
			final String[] projection,
			final String sortOrder
		)
	{
		final String selection = CallLog.Calls.NEW + "=?" +
						" AND "+ CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE;
		final String[] selectionArgs = {
				"1",
		};
		return query(context,
			projection,
			selection,
			selectionArgs,
			sortOrder
		);
	}

	/**
	 *
	 * @param context
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @return
	 */
	public static Cursor query(
			final Context context,
			final String[] projection,
			final String selection,
			final String[] selectionArgs,
			final String sortOrder
		)
	{
		final ContentResolver resolver = context.getContentResolver();
		final Cursor resultCursor = resolver.query(
				CallLog.Calls.CONTENT_URI,
				projection,
				selection,
				selectionArgs,
				sortOrder != null ? sortOrder : ""
		);
		return resultCursor;
	}
}
