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

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 *
 * @author xxx
 *
 * @param <TBL>
 * @param <PKMDL>
 * @param <MDL>
 */
public class PortableDAO
	<
		TBL extends Table,
		PKMDL extends PrimaryKeyModel<PKMDL>,
		MDL extends DataModel<PKMDL,MDL>
	>
	implements DAO<PKMDL,MDL>
{
	final DataSource dataSource;
	final TBL table;

	/**
	 * @param dataSource
	 * @param table
	 */
	public PortableDAO(
			DataSource dataSource,
			TBL table
			)
	{
		this.dataSource = dataSource;
		this.table = table;
	}

	@Override
	public MDL insert(MDL model) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		database.insert(table.getName(), null, model.changeContentValues());
		@SuppressWarnings("unchecked")
		PKMDL key = (PKMDL)model;
		return findByPrimaryKey(key,model);
	}

	@Override
	public int update(final PKMDL primaryKey,MDL model) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		return database.update(table.getName(), model.changeContentValues(),primaryKey.getPrimaryKeyQuery(),primaryKey.getPrimaryKeyValues());
	}
	@Override
	public int delete(final PKMDL primaryKey) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		return database.delete(table.getName(), primaryKey.getPrimaryKeyQuery(),primaryKey.getPrimaryKeyValues());
	}

	@Override
	public int delete(String whereClause,String[] whereArgs) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		return database.delete(table.getName(), whereClause,whereArgs);
	}

	public Cursor find(String selection,String[] selectionArgs,String orderBy){
		final SQLiteDatabase database = dataSource.getOpenHelper().getReadableDatabase();
		final Cursor cursor = database.query(
				table.getName(), //table
				table.getProjectiuon(), //columns
				selection,//selection
				selectionArgs,//selectionArgs
				null,//groupBy
				null,//having
				orderBy //orderBy
				);
		return cursor;
	}

	@Override
	public MDL[] find(String selection,String[] selectionArgs,String orderBy,MDL model){
		final SQLiteDatabase database = dataSource.getOpenHelper().getReadableDatabase();
		final Cursor cursor = database.query(
				table.getName(), //table
				table.getProjectiuon(), //columns
				selection,//selection
				selectionArgs,//selectionArgs
				null,//groupBy
				null,//having
				orderBy //orderBy
				);
		try {
			return changeCursorToModeArray(cursor ,model);
		}finally {
			if (!cursor.isClosed() && cursor != null){
				cursor.close();
			}
		}
	}

	public Cursor findByPrimaryKey(PKMDL primaryKey) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getReadableDatabase();
		final Cursor cursor = database.query(
				table.getName(), //table
				table.getProjectiuon(), //columns
				primaryKey.getPrimaryKeyQuery(),
				primaryKey.getPrimaryKeyValues(),
				null,//groupBy
				null,//having
				null //orderBy
				);
		return cursor ;
	}

	@Override
	public MDL findByPrimaryKey(PKMDL primaryKey,MDL model) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getReadableDatabase();
		final Cursor cursor = database.query(
				table.getName(), //table
				table.getProjectiuon(), //columns
				primaryKey.getPrimaryKeyQuery(),
				primaryKey.getPrimaryKeyValues(),
				null,//groupBy
				null,//having
				null //orderBy
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
		return result;
	}

	private MDL[] changeCursorToModeArray(Cursor cursor ,MDL model) {
		ArrayList<MDL> results = new ArrayList<MDL>();
		if(cursor != null){
			while (cursor.moveToNext()) {
				results.add(model.changeModel(cursor));
			}
		}
		return results.toArray(model.genericObjectArray());
	}
}
