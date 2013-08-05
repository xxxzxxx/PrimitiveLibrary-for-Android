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

import com.primitive.library.common.log.Logger;

/**
 * @author xxx
 * @param <MDL>
 */
public class DataAccessObject<MDL extends DataModel<?>> extends BaseDataAccessObject {
	private final Context context;
	protected final Uri uri;

	/**
	 * @param context
	 * @param uri
	 */
	public DataAccessObject(Context context, final Uri uri) {
		final long start = Logger.start();
		this.context = context;
		this.uri = uri;
		Logger.end(start);
	}

	/**
	 * @param values
	 * @return
	 */
	public Uri insert(final ContentValues values) {
		final long start = Logger.start();
		final Uri uri = DataAccessObject.insert(context, this.uri, values);
		Logger.end(start);
		return uri;
	}

	/**
	 *
	 * @param model
	 * @return
	 */
	public Uri insert(DataModel<?> model) {
		final long start = Logger.start();
		final Uri uri = DataAccessObject.insert(context, this.uri,model.changeContentValues());
		model.setUri(uri);
		Logger.end(start);
		return uri;
	}

	/**
	 *
	 * @param values
	 * @param model
	 * @return
	 */
	public int update(final ContentValues values, DataModel<?> model) {
		final long start = Logger.start();
		int result =  DataAccessObject.update(context, uri, values, model);
		Logger.end(start);
		return result;
	}

	/**
	 * update target primary key recode
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	public int update(final DataModel<?> model) {
		final long start = Logger.start();
		int result = DataAccessObject.update(context, uri,
				model.changeContentValues(), model);
		Logger.end(start);
		return result;
	}

	/**
	 * delete target primary key recode
	 *
	 * @param model
	 * @return
	 */
	public int delete(final DataModel<?> model) {
		final long start = Logger.start();
		int result =  DataAccessObject.delete(context, uri, model);
		Logger.end(start);
		return result;
	}

	/**
	 * delete all recode
	 *
	 * @return
	 */
	public int deleteAll() {
		final long start = Logger.start();
		int result = DataAccessObject.deleteAll(context, uri);
		Logger.end(start);
		return result;
	}

	/**
	 * return target recode
	 *
	 * @param context
	 * @param whereQuery
	 * @param whereValue
	 * @param model
	 * @return
	 */
	public DataModel<?>[] find(final String whereQuery,
			final String[] whereValue, final DataModel<?> model) {
		final long start = Logger.start();
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
		Logger.end(start);
		return results.toArray(model.genericObjectArray());
	}

	/**
	 * return all recode
	 *
	 * @param model
	 * @return
	 */
	public DataModel<?>[] findAll(final DataModel<?> model) {
		final long start = Logger.start();
		Logger.info(model.getClass().getSimpleName());
		Cursor cursor = DataAccessObject.find(context, uri,
				model.getProjectiuon(), null, null);
		ArrayList<DataModel<?>> results = new ArrayList<DataModel<?>>();
		try {
			while (cursor.moveToNext()) {
				results.add(model.changeModel(cursor));
			}
		} finally {
			if (!cursor.isClosed() || cursor != null) {
				cursor.close();
			}
		}
		Logger.end(start);
		return results.toArray(model.genericObjectArray());
	}

	/**
	 * findByPrimaryKey
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	public DataModel<?> findByPrimaryKey(final DataModel<?> model) {
		final long start = Logger.start();
		Logger.info(model.getClass().getSimpleName());
		Logger.debug(model.getPrimaryKeyQuery());
		Logger.debug(model.getPrimaryKeyValues());
		Cursor cursor = DataAccessObject.find(context, uri,
				model.getProjectiuon(),
				model.getPrimaryKeyQuery(),
				model.getPrimaryKeyValues()
				);
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
		Logger.end(start);
		return result;
	}
}