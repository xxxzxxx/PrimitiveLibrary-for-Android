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
 *
 * @param <PKMDL>
 * @param <MDL>
 */
public class ProvidableDAO<PKMDL extends PrimaryKeyModel<?>,MDL extends DataModel<PKMDL,MDL>> implements DAO<PKMDL,MDL>
{
	private final Context context;
	protected final Uri uri;

	/**
	 * insert contents
	 * @param context
	 * @param uri
	 * @param values
	 * @return
	 */
	public static Uri insert(final Context context,
			final Uri uri,
			final ContentValues values) {
		long start = Logger.start();
		Logger.debug(uri);
		Logger.debug(values);

		Uri result =  context.getContentResolver().insert(uri, values);

		Logger.end(start);
		return result;
	}

	/**
	 * update contents
	 * @param context
	 * @param uri
	 * @param values
	 * @param where
	 * @param selectionArgs
	 * @return
	 */
	public static int update(final Context context,
			final Uri uri,
			final ContentValues values,
			final String where,
			final String[] selectionArgs) {
		long start = Logger.start();
		int i = context.getContentResolver().update(
				uri,
				values,
				where,
				selectionArgs);
		Logger.end(start);
		return i;
	}


	/**
	 * delete contents
	 * @param context
	 * @param uri
	 * @param whereQuery
	 * @param whereValues
	 * @return
	 */
	public static int delete(final Context context, final Uri uri,final String whereQuery,final String[] whereValues) {
		long start = Logger.start();
		int i = context.getContentResolver().delete(
				uri,
				whereQuery,
				whereValues
			);
		Logger.end(start);
		return i;
	}

	/**
	 * find contents
	 * @param context
	 * @param uri
	 * @param projectiuon
	 * @param whereQuery
	 * @param whereValue
	 * @param orderBy
	 * @return
	 */
	public static Cursor find(
			final Context context,
			final Uri uri,
			final String[] projectiuon,
			final String whereQuery,
			final String[] whereValue,
			final String orderBy
	) {
		long start = Logger.start();
		Logger.debug(uri);
		Logger.debug(projectiuon);
		Logger.debug(whereQuery);
		Cursor cursor = context.getContentResolver().query(
				uri,
				projectiuon,
				whereQuery,
				whereValue,
				orderBy
			);
		Logger.end(start);
		return cursor;
	}

	public ProvidableDAO(Context context, final Uri uri) {
		final long start = Logger.start();
		this.context = context;
		this.uri = uri;
		Logger.end(start);
	}

	public Uri insert(final ContentValues values) {
		final long start = Logger.start();
		final Uri uri = ProvidableDAO.insert(context, this.uri, values);
		Logger.end(start);
		return uri;
	}

	public int update(final ContentValues values, DataModel<?,?>  model) {
		final long start = Logger.start();
		int result =  ProvidableDAO.update(
				context,
				uri,
				values,
				model.getPrimaryKeyQuery(),
				model.getPrimaryKeyValues()
				);
		Logger.end(start);
		return result;
	}

	@Override
	public MDL insert(MDL model) {
		final long start = Logger.start();
		final Uri uri = ProvidableDAO.insert(context, this.uri,model.changeContentValues());
		model.setUri(uri);
		@SuppressWarnings("unchecked")
		PKMDL model2 = (PKMDL)model;
		MDL model3 = findByPrimaryKey(model2, model);
		Logger.end(start);
		return model3;
	}

	@Override
	public int update(final PKMDL primaryKey,final MDL model) {
		final long start = Logger.start();
		int result = ProvidableDAO.update(
				context,
				uri,
				model.changeContentValues(),
				primaryKey.getPrimaryKeyQuery(),
				primaryKey.getPrimaryKeyValues()
			);
		Logger.end(start);
		return result;
	}

	@Override
	public int delete(final PKMDL  primaryKey) {
		final long start = Logger.start();
		int result =  ProvidableDAO.delete(
				context,
				uri,
				primaryKey.getPrimaryKeyQuery(),
				primaryKey.getPrimaryKeyValues()
				);
		Logger.end(start);
		return result;
	}

	@Override
	public int delete(String whereClause,String[] whereArgs){
		final long start = Logger.start();
		int result =  ProvidableDAO.delete(context, uri, whereClause,whereArgs);
		Logger.end(start);
		return result;
	}

	@Override
	public ArrayList<MDL> find(String whereQuery,String[] whereValue,String orderBy,MDL model){
		final long start = Logger.start();
		Cursor cursor = ProvidableDAO.find(context, uri,model.getProjectiuon(), whereQuery, whereValue, orderBy);
		ArrayList<MDL> results = null;
		try {
			results = changeCursorToModeArray(cursor ,model);
		} finally {
			if (!cursor.isClosed() && cursor != null){
				cursor.close();
			}
		}
		Logger.end(start);
		return results;
	}


	@Override
	public MDL findByPrimaryKey(PKMDL primaryKey,final MDL model) {
		final long start = Logger.start();
		Cursor cursor = ProvidableDAO.find(
				context,
				uri,
				model.getProjectiuon(),
				primaryKey.getPrimaryKeyQuery(),
				primaryKey.getPrimaryKeyValues(),
				null
				);
		MDL result = null;
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

	/**
	 * @param cursor
	 * @param model
	 * @return
	 */
	private ArrayList<MDL> changeCursorToModeArray(Cursor cursor ,MDL model) {
		ArrayList<MDL> results = new ArrayList<MDL>();
		if(cursor != null){
			while (cursor.moveToNext()) {
				results.add(model.changeModel(cursor));
			}
		}
		return results;
	}

}