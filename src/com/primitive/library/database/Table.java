/**
 * Table
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import android.database.sqlite.SQLiteDatabase;

import com.primitive.library.helper.Logger;

public abstract class Table{
	/** */
	private final String name;
	/** */
	private Column[] columns;

	/**
	 * 
	 * @param name
	 * @param columns
	 */
	protected Table(final String name, final Column[] columns){
		Logger.start();
		this.name = name;
		this.columns = columns;
		Logger.end();
	}

	/**
	 * 
	 * @return
	 */
	public String getCreateTableSQL(){
		Logger.start();
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(this.name);
		sql.append("(");
		int i = 0;
		for(Column column : this.columns){
			if(i > 0){
				sql.append(",");
			}
			sql.append(column.getDefinitionSQL());
		}
		sql.append(");");
		Logger.debug(sql);
		Logger.end();
		return sql.toString();
	}
	/**
	 * 
	 * @return
	 */
	public Column[] getColumns() {
		return columns;
	}

	public abstract void upgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
