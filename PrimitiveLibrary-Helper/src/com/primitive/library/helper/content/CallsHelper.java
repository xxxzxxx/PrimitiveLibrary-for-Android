package com.primitive.library.helper.content;

import com.primitive.library.contents.ContentDAO;
import com.primitive.library.contents.Callback;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

/**
 * @author xxx
 */
public class CallsHelper {
	public static final String[] defaultProjection = new String[]{
			CallLog.Calls._ID,
			CallLog.Calls._COUNT,
			CallLog.Calls.CACHED_NAME,
			CallLog.Calls.CACHED_NUMBER_LABEL,
			CallLog.Calls.CACHED_NUMBER_TYPE,
			CallLog.Calls.CONTENT_ITEM_TYPE,
			CallLog.Calls.DATE,
			CallLog.Calls.DURATION,
			CallLog.Calls.IS_READ,
			CallLog.Calls.NEW,
			CallLog.Calls.NUMBER,
			CallLog.Calls.TYPE,
	};
	private static final String missedCallsSelection = CallLog.Calls.NEW + "=?" + " AND "+ CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE;
	private static final String[] missedCallsSelectionArgs = {
			"1",
	};

	/**
	 * @param context
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @return
	 */
	public static Cursor calls(
			final Context context,
			final String[] projection,
			final String selection,
			final String[] selectionArgs,
			final String sortOrder
		)
	{
		return ContentDAO.query(
				context,
				CallLog.Calls.CONTENT_URI,
				defaultProjection,
				selection,
				selectionArgs,
				sortOrder == null ? CallLog.Calls.DEFAULT_SORT_ORDER : sortOrder
		);
	}

	/**
	 * @param context
	 * @param projection
	 * @param sortOrder
	 * @return
	 */
	public static Cursor missedCalls(
			final Context context,
			final String sortOrder
		)
	{
		return ContentDAO.query(
				context,
				CallLog.Calls.CONTENT_URI,
				defaultProjection,
				missedCallsSelection,
				missedCallsSelectionArgs,
				sortOrder == null ? CallLog.Calls.DEFAULT_SORT_ORDER : sortOrder
		);
	}

	/**
	 * @param context
	 * @param contenUri
	 * @param projection
	 * @param sortOrder
	 * @param callback
	 * @return
	 */
	public static ContentProviderClient missedCallsQueryWithCallBack(
			final Context context,
			final String[] projection,
			final String sortOrder,
			final Callback callback
		)
	{
		final ContentResolver resolver = context.getContentResolver();
		return missedCallsQueryWithCallBack(
				resolver,
				projection ,
				sortOrder,
				callback
			);
	}

	/**
	 * @param resolver
	 * @param contenUri
	 * @param projection
	 * @param sortOrder
	 * @param callback
	 * @return
	 */
	public static ContentProviderClient missedCallsQueryWithCallBack(
			final ContentResolver resolver,
			final String[] projection,
			final String sortOrder,
			final Callback callback
		)
	{
		final ContentProviderClient client = resolver.acquireContentProviderClient(CallLog.Calls.CONTENT_URI);
		return missedCallsQueryWithCallBack(
				client,
				projection,
				sortOrder,
				callback
			);
	}

	/**
	 * @param client
	 * @param contenUri
	 * @param projection
	 * @param sortOrder
	 * @param callback
	 * @return
	 */
	public static ContentProviderClient missedCallsQueryWithCallBack(
			final ContentProviderClient client,
			final String[] projection,
			final String sortOrder,
			final Callback callback
		)
	{
		return missedCallsQueryWithCallBack(
				client,
				defaultProjection,
				sortOrder,
				callback
			);
	}
}
