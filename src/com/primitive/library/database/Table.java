package com.primitive.library.database;

import com.primitive.library.helper.Logger;

public class Table {
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
}
