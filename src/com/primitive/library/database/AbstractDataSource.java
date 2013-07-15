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

public abstract class AbstractDataSource {
	protected final Table[] tables;
	protected final OpenHelper openHelper;
	protected final Context context;

	public AbstractDataSource(final Context context, final Table[] tables) {
		this.context = context;
		this.tables = tables;
		openHelper = new OpenHelper(this);
	}

	public abstract String getDataBaseName();

	public abstract int getDataBaseVersion();

	public abstract CursorFactory getCursorFactory();
}
