package com.primitive.library.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataAccessObject {
	public static Uri insert(final Context context,final Uri uri,final ContentValues values){
		return context.getContentResolver().insert(uri,values);
	}
	public static int update(final Context context,final Uri uri,final ContentValues values,final String id){
		return context.getContentResolver().update(uri, values, BaseColumns._ID + "=?", new String[] {id});
	}

	public static int delete(final Context context,final Uri uri,final String id){
		return context.getContentResolver().delete(uri, BaseColumns._ID + "=?", new String[] {id});
	}

	public static int deleteAll(final Context context,final Uri uri){
		return context.getContentResolver().delete(uri, null, null);
	}

	public static Cursor findAll(final Context context,final Uri uri,final String[] projectiuon){
		return context.getContentResolver().query(uri, projectiuon, null, null, null);
	}

	public static Cursor findById(final Context context,final Uri uri,final String id,final String[] projectiuon){
		return context.getContentResolver().query(uri, projectiuon, BaseColumns._ID + "=?", new String[] { id }, null);
	}

	private final Context context;
	protected final Uri uri;

	public DataAccessObject(Context context,final Uri uri){
		this.context = context;
		this.uri = uri;
	}

	public Uri insert(final ContentValues values){
		return DataAccessObject.insert(context,uri,values);
	}

	public int update(final ContentValues values,final String id){
		return DataAccessObject.update(context,uri, values, id);
	}

	public int delete(final String id){
		return DataAccessObject.delete(context,uri,id);
	}

	public int deleteAll(){
		return DataAccessObject.deleteAll(context,uri);
	}

	public Cursor findAll(final String[] projectiuon){
		return DataAccessObject.findAll(context,uri, projectiuon);
	}

	public Cursor findById(final String id,final String[] projectiuon){
		return DataAccessObject.findById(context,uri,id,projectiuon);
	}
}