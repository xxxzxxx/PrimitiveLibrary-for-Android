/**
 * AbstractDataSource
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.primitive.library.helper.Logger;

public abstract class AbstractDataSource {
	protected final Table[] tables;
	private final OpenHelper openHelper;
	private final Context context;

	public AbstractDataSource(final Context context, final Table[] tables) {
		long start = Logger.start();
		this.context = context;
		this.tables = tables;
		openHelper = new OpenHelper(this);
		Logger.end(start);
	}
	public abstract String getDataBaseName();
	public abstract int getDataBaseVersion();
	public abstract CursorFactory getCursorFactory();
	public OpenHelper getOpenHelper() {
		Logger.start();
		if(openHelper == null){
			Logger.debug("openHelper is null");
		}
		return openHelper;
	}

	public Context getContext() {
		Logger.start();
		if(context == null){
			Logger.debug("context is null");
		}
		return context;
	}
}
