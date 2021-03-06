/**
 * PortableDAO
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import android.net.Uri;

/**
 *
 * @author xxx
 *
 * @param <PKMDL>
 * @param <MDL>
 */
public interface DAO <PKMDL extends PrimaryKeyModel<?>,MDL extends DataModel<?,?>> {
	/**
	 * @param model
	 * @return
	 */
	public Uri insert(MDL model);
	/**
	 * @param model
	 * @return
	 */
	public int update(final PKMDL primaryKey,final MDL model);
	/**
	 * @param model
	 * @return
	 */
	public int delete(final PKMDL primaryKey);
	/**
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int delete(String whereClause,String[] whereArgs);
	/**
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param orderBy
	 * @param model
	 * @return
	 */
	public MDL[] find(String selection,String[] selectionArgs,String orderBy,MDL model);
	/**
	 * @param primaryKey
	 * @param model
	 * @return
	 */
	public MDL findByPrimaryKey(final PKMDL primaryKey,final MDL model);
}
