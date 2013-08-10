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
import android.net.Uri;

public interface DataModel<PKMDL,MDL> extends PrimaryKeyModel<PKMDL>{
	/**
	 *
	 * @param uri
	 */
	public void setUri(final Uri uri);
	/**
	 *
	 * @return
	 */
	public Uri getUri();
	/**
	 *
	 * @return
	 */
	public String[] getProjectiuon();
	/**
	 *
	 * @return
	 */
	public String getPrimaryKeyQuery();
	/**
	 *
	 * @return
	 */
	public String[] getPrimaryKeyValues();
	/**
	 *
	 * @param cursor
	 * @return
	 */
	public MDL changeModel(final Cursor cursor);
	/**
	 *
	 * @return
	 */
	public ContentValues changeContentValues();
	/**
	 *
	 * @return
	 */
	public MDL[] genericObjectArray();
}
