/**
 * OpenHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.primitive.library.helper.Logger;

public class OpenHelper extends SQLiteOpenHelper {
	private final AbstractDataSource datasource;

	public OpenHelper(AbstractDataSource datasource) {
		super(datasource.getContext(), datasource.getDataBaseName(), datasource
				.getCursorFactory(), datasource.getDataBaseVersion());
		long start = Logger.start();
		this.datasource = datasource;
		Logger.end(start);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		long start = Logger.start();
		Table[] tables = datasource.tables;
		if (tables != null) {
			for (final Table tbl : tables) {
				db.execSQL(tbl.getCreateTableSQL());
			}
		}
		Logger.end(start);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		long start = Logger.start();
		Table[] tables = datasource.tables;
		if (tables != null) {
			for (final Table tbl : tables) {
				tbl.upgrade(db, oldVersion, newVersion);
			}
		}
		Logger.end(start);
	}
}
