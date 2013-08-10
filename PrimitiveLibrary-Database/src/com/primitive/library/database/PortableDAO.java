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
 * @param <MDL>
 * @param <TBL>
 */
public class PortableDAO
	<
		TBL extends Table,
		PKMDL extends PrimaryKeyModel<?>,
		MDL extends DataModel<?,?>
	>
	implements DAO<PKMDL,MDL>
{
	final DataSource dataSource;
	final TBL table;
	public PortableDAO(
			DataSource dataSource,
			TBL table
			)
	{
		this.dataSource = dataSource;
		this.table = table;
	}

	@Override
	public Uri insert(DataModel<?,?> model) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		database.insert(table.getName(), null, model.changeContentValues());
		return null;
	}

	@Override
	public int update(final PrimaryKeyModel<?> primaryKey,DataModel<?,?> model) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		return database.update(table.getName(), model.changeContentValues(),primaryKey.getPrimaryKeyQuery(),primaryKey.getPrimaryKeyValues());
	}
	@Override
	public int delete(final PrimaryKeyModel<?> primaryKey) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		return database.delete(table.getName(), primaryKey.getPrimaryKeyQuery(),primaryKey.getPrimaryKeyValues());
	}

	@Override
	public int delete(String whereClause,String[] whereArgs) {
		final SQLiteDatabase database = dataSource.getOpenHelper().getWritableDatabase();
		return database.delete(table.getName(), whereClause,whereArgs);
	}

	@Override
	public DataModel<?,?>[] find(String selection,String[] selectionArgs,String orderBy,DataModel<?,?> model){
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

	@Override
	public DataModel<?,?> findByPrimaryKey(PrimaryKeyModel<?> primaryKey,DataModel<?,?> model) {
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
		DataModel<?,?> result = null;
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

	private DataModel<?,?>[] changeCursorToModeArray(Cursor cursor ,DataModel<?,?> model) {
		ArrayList<DataModel<?,?>> results = new ArrayList<DataModel<?,?>>();
		if(cursor != null){
			while (cursor.moveToNext()) {
				results.add(model.changeModel(cursor));
			}
		}
		return results.toArray(model.genericObjectArray());
	}
}
