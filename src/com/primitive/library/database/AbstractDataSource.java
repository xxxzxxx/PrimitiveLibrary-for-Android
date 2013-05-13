package com.primitive.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public abstract class AbstractDataSource {
	private final Table[] tables;
	protected final Context context;
	public AbstractDataSource(final Context context,final Table[] tables){
		this.context = context;
		this.tables = tables;
	}
	public abstract String getDataBaseName();
	public abstract int getDataBaseVersion();
	public abstract CursorFactory getCursorFactory();
	public Table[] getTables(){
		return tables;
	}
}
