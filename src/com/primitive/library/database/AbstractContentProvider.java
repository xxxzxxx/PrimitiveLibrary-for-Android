package com.primitive.library.database;

import com.primitive.library.helper.Logger;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public abstract class AbstractContentProvider extends ContentProvider{
	protected OpenHelper openHelper;
	protected abstract OpenHelper createOpenHelper();
	protected abstract String getAythority();
	protected abstract UriMatcher getUriMatcher();
	protected abstract String matchies(final int matchIndex);

	private String getTableName(final Uri uri) {
		final String[] paths = uri.getPath().split("/");
		if (paths == null || paths.length < 2) {
			return null;
		}
		return paths[1];
	}

	/**
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		Logger.start();
		final SQLiteDatabase db = openHelper.getWritableDatabase();
		final String tableName = getTableName(uri);
		Logger.debug(tableName);
		final int count = db.delete(tableName, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		Logger.end();
		return count;
	}

	/**
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		UriMatcher matcher= getUriMatcher();
		int matchIndex = matcher.match(uri);
		return matchies(matchIndex);
	}

	/**
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = openHelper.getWritableDatabase();
		final long id = db.insert(getTableName(uri), null, values);
		final Uri newUri = ContentUris.withAppendedId(uri, id);
		getContext().getContentResolver().notifyChange(newUri, null);
		return newUri;
	}

	/**
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		openHelper = createOpenHelper();
		return true;
	}

	/**
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		final SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
		sqliteQueryBuilder.setTables(getTableName(uri));
		String orderBy = null;
		if (!TextUtils.isEmpty(sortOrder)) {
			orderBy = sortOrder;
		}
		final SQLiteDatabase db = openHelper.getReadableDatabase();
		final Cursor c = sqliteQueryBuilder.query(db, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	/**
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where,String[] whereArgs) {
		final SQLiteDatabase db = openHelper.getWritableDatabase();
		final int count = db.update(getTableName(uri), values, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
