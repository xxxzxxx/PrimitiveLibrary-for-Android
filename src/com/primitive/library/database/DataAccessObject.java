/**
 * DataAccessObject
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.primitive.library.helper.Logger;

/**
 * @author xxx
 * @param <MDL>
 */
public class DataAccessObject<MDL extends DataModel<?>> extends
		BaseDataAccessObject {
	/**
	 *
	 */
	private final Context context;
	/**
	 *
	 */
	protected final Uri uri;

	/**
	 * 
	 * @param context
	 * @param uri
	 */
	public DataAccessObject(Context context, final Uri uri) {
		Logger.start();
		this.context = context;
		this.uri = uri;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public Uri insert(final ContentValues values) {
		Logger.start();
		return DataAccessObject.insert(context, uri, values);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public Uri insert(final DataModel<?> model) {
		Logger.start();
		Logger.info(model.getClass().getSimpleName());
		return DataAccessObject.insert(context, uri,
				model.changeContentValues());
	}

	/**
	 * 
	 * @param values
	 * @param id
	 * @return
	 */
	public int update(final ContentValues values, final String id) {
		Logger.start();
		return DataAccessObject.update(context, uri, values, id);
	}

	/**
	 * update target id recode
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	public int update(final DataModel<?> model, final String id) {
		Logger.start();
		Logger.info(model.getClass().getSimpleName());
		return DataAccessObject.update(context, uri,
				model.changeContentValues(), id);
	}

	/**
	 * delete target id recode
	 * 
	 * @param id
	 * @return
	 */
	public int delete(final String id) {
		Logger.start();
		return DataAccessObject.delete(context, uri, id);
	}

	/**
	 * delete all recode
	 * 
	 * @return
	 */
	public int deleteAll() {
		Logger.start();
		return DataAccessObject.deleteAll(context, uri);
	}

	/**
	 * 
	 * @param context
	 * @param whereQuery
	 * @param whereValue
	 * @param model
	 * @return
	 */
	public DataModel<?>[] find(final String whereQuery,
			final String[] whereValue, final DataModel<?> model) {
		ArrayList<DataModel<?>> results = new ArrayList<DataModel<?>>();
		Cursor cursor = DataAccessObject.find(context, uri,
				model.getProjectiuon(), whereQuery, whereValue);
		try {
			while (cursor.moveToNext()) {
				results.add(model.changeModel(cursor));
			}
		} finally {
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}
		return results.toArray(model.genericObjectArray());
	}

	/**
	 * return all recode
	 * 
	 * @param model
	 * @return
	 */
	public DataModel<?>[] findAll(final DataModel<?> model) {
		Logger.start();
		Logger.info(model.getClass().getSimpleName());
		Cursor cursor = DataAccessObject.find(context, uri,
				model.getProjectiuon(), null, null);
		ArrayList<DataModel<?>> results = new ArrayList<DataModel<?>>();
		try {
			while (cursor.moveToNext()) {
				results.add(model.changeModel(cursor));
			}
		} finally {
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}
		return results.toArray(model.genericObjectArray());
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	public DataModel<?> findById(final String id, final DataModel<?> model) {
		Logger.start();
		Logger.info(model.getClass().getSimpleName());

		Cursor cursor = DataAccessObject.find(context, uri,
				model.getProjectiuon(), BaseColumns._ID + "=?",
				new String[] { id });
		DataModel<?> result = null;

		try {
			while (cursor.moveToNext()) {
				result = model.changeModel(cursor);
			}
		} finally {
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}

		return result;
	}
}