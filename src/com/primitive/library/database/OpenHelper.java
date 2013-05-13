package com.primitive.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper{
	private final AbstractDataSource database;
	public OpenHelper(Context context, AbstractDataSource database) {
		super(
			context,
			database.getDataBaseName(), 
			database.getCursorFactory(), 
			database.getDataBaseVersion()
		);
		this.database = database;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Table[] tables = database.getTables();
		if(tables != null){
			for(Table table : tables){
				db.execSQL(table.getCreateTableSQL());
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
