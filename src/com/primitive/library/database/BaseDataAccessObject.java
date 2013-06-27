package com.primitive.library.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class BaseDataAccessObject {
	/**
	 *
	 * @param context
	 * @param uri
	 * @param values
	 * @return
	 */
	public static Uri insert(final Context context,final Uri uri,final ContentValues values){
		return context.getContentResolver().insert(uri,values);
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @param values
	 * @param id
	 * @return
	 */
	public static int update(final Context context,final Uri uri,final ContentValues values,final String id){
		return context.getContentResolver().update(uri, values, BaseColumns._ID + "=?", new String[] {id});
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @param id
	 * @return
	 */
	public static int delete(final Context context,final Uri uri,final String id){
		return context.getContentResolver().delete(uri, BaseColumns._ID + "=?", new String[] {id});
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static int deleteAll(final Context context,final Uri uri){
		return context.getContentResolver().delete(uri, null, null);
	}

	/**
	 *
	 * @param context
	 * @param uri
	 * @param id
	 * @param projectiuon
	 * @return
	 */
	public static Cursor find(
			final Context context,
			final Uri uri,
			final String[] projectiuon,
			final String whereQuery,
			final String[] whereValue
			){
		return context.getContentResolver().query(uri, projectiuon, whereQuery, whereValue, null);
	}
}
