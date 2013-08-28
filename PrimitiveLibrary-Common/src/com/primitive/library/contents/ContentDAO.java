/**
 * ContentDAO
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.contents;

import com.primitive.library.common.log.Logger;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

/**
 * @author xxx
 */
public class ContentDAO {

	/**
	 * @param client
	 * @param contenUri
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @return
	 */
	public static Cursor query(
			final Context context,
			final Uri contenUri,
			final String[] projection,
			final String selection,
			final String[] selectionArgs,
			final String sortOrder
		)
	{
		final ContentResolver resolver = context.getContentResolver();
		Cursor cursor  = resolver.query(
				contenUri,
				projection,
				selection,
				selectionArgs,
				sortOrder
		);
		return cursor;
	}

	/**
	 * @param context
	 * @param contenUri
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @param callback
	 * @return
	 */
	public static ContentProviderClient queryWithCallback(
			final Context context,
			final Uri contenUri,
			final String[] projection,
			final String selection,
			final String[] selectionArgs,
			final String sortOrder,
			final Callback callback
		)
	{
		final ContentResolver resolver = context.getContentResolver();
		return queryWithCallback(
				resolver,
				contenUri,
				projection,
				selection,
				selectionArgs,
				sortOrder,
				callback
			);
	}

	/**
	 * @param resolver
	 * @param contenUri
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @param callback
	 * @return
	 */
	public static ContentProviderClient queryWithCallback(
			final ContentResolver resolver,
			final Uri contenUri,
			final String[] projection,
			final String selection,
			final String[] selectionArgs,
			final String sortOrder,
			final Callback callback
		)
	{
		final ContentProviderClient client = resolver.acquireContentProviderClient(contenUri);
		return queryWithCallback(
				client,
				contenUri,
				projection,
				selection,
				selectionArgs,
				sortOrder,
				callback
			);
	}

	/**
	 * @param client
	 * @param contenUri
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @param callback
	 * @return
	 */
	public static ContentProviderClient queryWithCallback(
			final ContentProviderClient client,
			final Uri contenUri,
			final String[] projection,
			final String selection,
			final String[] selectionArgs,
			final String sortOrder,
			final Callback callback
		)
	{
		try {
			Cursor cursor  = client.query(
					contenUri,
					projection,
					selection,
					selectionArgs,
					sortOrder
			);
			callback.didQuery(cursor);
		} catch (RemoteException ex) {
			Logger.err(ex);
			callback.didError(ex,client);
		}
		return client;
	}
}
