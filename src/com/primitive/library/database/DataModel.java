/**
 * DataModel
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.database;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataModel<T> {
	public void setId(final String id);

	public String getId();

	public String[] getProjectiuon();

	public DataModel<T> changeModel(final Cursor cursor);

	public ContentValues changeContentValues();

	public DataModel<T>[] genericObjectArray();
}
