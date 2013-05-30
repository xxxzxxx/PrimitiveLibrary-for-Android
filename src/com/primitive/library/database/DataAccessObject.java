/**
 * DataAccessObject
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import java.util.ArrayList;

import com.primitive.library.helper.Logger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author xxx
 *
 * @param <DMODEL>
 */
public class DataAccessObject<DMODEL extends DataModel<?>,TBL extends Table> {
	/**
	 * 
	 * @param context
	 * @param uri
	 * @param values
	 * @return
	 */
	public static Uri insert(final Context context,final Uri uri,final ContentValues values){
		return context.getContentResolver().insert(uri,values);
	}

	/**
	 * 
	 * @param context
	 * @param uri
	 * @param values
	 * @param id
	 * @return
	 */
	public static int update(final Context context,final Uri uri,final ContentValues values,final String id){
		return context.getContentResolver().update(uri, values, BaseColumns._ID + "=?", new String[] {id});
	}

	/**
	 * 
	 * @param context
	 * @param uri
	 * @param id
	 * @return
	 */
	public static int delete(final Context context,final Uri uri,final String id){
		return context.getContentResolver().delete(uri, BaseColumns._ID + "=?", new String[] {id});
	}

	/**
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static int deleteAll(final Context context,final Uri uri){
		return context.getContentResolver().delete(uri, null, null);
	}

	/**
	 * 
	 * @param context
	 * @param uri
	 * @param id
	 * @param projectiuon
	 * @return
	 */
	public static Cursor find(
			final Context context,
			final Uri uri,
			final String[] projectiuon,
			final String whereQuery,
			final String[] whereValue
			){
		return context.getContentResolver().query(uri, projectiuon, whereQuery, whereValue, null);
	}

	private final Context context;
	protected final Uri uri;

	/**
	 * 
	 * @param context
	 * @param uri
	 */
	public DataAccessObject(Context context,final Uri uri){
		Logger.start();
		this.context = context;
		this.uri = uri;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public Uri insert(final ContentValues values){
		Logger.start();
		return DataAccessObject.insert(context,uri,values);
	}

	/**
	 * 
	 * @param dataobject
	 * @return
	 */
	public Uri insert(final DMODEL dataobject){
		Logger.start();
		Logger.info(dataobject.getClass().getSimpleName());
		return DataAccessObject.insert(context,uri,dataobject.changeContentValues());
	}

	/**
	 * 
	 * @param values
	 * @param id
	 * @return
	 */
	public int update(final ContentValues values,final String id){
		Logger.start();
		return DataAccessObject.update(context,uri, values, id);
	}

	/**
	 * update target id recode
	 * @param dataobject
	 * @param id
	 * @return
	 */
	public int update(final DMODEL dataobject,final String id){
		Logger.start();
		Logger.info(dataobject.getClass().getSimpleName());
		return DataAccessObject.update(context,uri, dataobject.changeContentValues(), id);
	}

	/**
	 * delete target id recode
	 * @param id
	 * @return
	 */
	public int delete(final String id){
		Logger.start();
		return DataAccessObject.delete(context,uri,id);
	}

	/**
	 * delete all recode
	 * @return
	 */
	public int deleteAll(){
		Logger.start();
		return DataAccessObject.deleteAll(context,uri);
	}

	/**
	 * return all recode
	 * @param dataobject
	 * @return
	 */
	public DataModel<?>[] findAll(final DMODEL model){
		Logger.start();
		Logger.info(model.getClass().getSimpleName());
		Cursor cursor = DataAccessObject.find(context,uri, model.getProjectiuon(),null,null);
		ArrayList<DataModel<?>> results = new ArrayList<DataModel<?>>();
		try{
			while(cursor.moveToNext()){
				results.add(model.changeModel(cursor));
			}
		}finally{
			if(!cursor.isClosed()){
				cursor.close();
			}
		}
		return results.toArray(model.genericObjectArray());
	}

	/**
	 * findByPrimaryKey
	 * @param primaryKey
	 * @return
	 */
	public DataModel<?> findByPrimaryKey(final DMODEL primaryKey){
		Logger.start();
		Logger.info(primaryKey.getClass().getSimpleName());

		Column[] primaryKeys = primaryKey.getPrimaryKeys();
		String[] values = primaryKey.getPrimaryKeyValues();
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < primaryKeys.length; index ++){
			Column col = primaryKeys[index];
			if(index > 0){
				builder.append(" AND ");
			}
			builder.append(col.getName()+"=?");
		}

		Cursor cursor = DataAccessObject.find(context,uri,primaryKey.getProjectiuon(),builder.toString(),values);
		DataModel<?> model = null;
		try{
			while(cursor.moveToNext()){
				model = primaryKey.changeModel(cursor);
			}
		}finally{
			if(!cursor.isClosed()){
				cursor.close();
			}
		}
		return model;
	}
}