/**
 * OpenHelper
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

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
