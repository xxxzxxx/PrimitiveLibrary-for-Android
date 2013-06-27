package com.primitive.library.database;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataModel<T> {
	public String getId();
	public String[] getProjectiuon();
	public DataModel<T> changeModel(final Cursor cursor);
	public ContentValues changeContentValues();
	public DataModel<T>[] genericObjectArray();
}
