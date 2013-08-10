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
	public Uri insert(DataModel<?,?> model);
	/**
	 * @param model
	 * @return
	 */
	public int update(final PrimaryKeyModel<?> primaryKey,final DataModel<?,?> model);
	/**
	 * @param model
	 * @return
	 */
	public int delete(final PrimaryKeyModel<?> primaryKey);
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
	public DataModel<?,?>[] find(String selection,String[] selectionArgs,String orderBy,DataModel<?,?> model);
	/**
	 * @param primaryKey
	 * @param model
	 * @return
	 */
	public DataModel<?,?> findByPrimaryKey(final PrimaryKeyModel<?> primaryKey,final DataModel<?,?> model);
}
