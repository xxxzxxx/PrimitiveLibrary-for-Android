/**
 * Column
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import com.primitive.library.helper.Logger;

public class Column {
	public enum Type {
		INTEGER, TEXT, NONE, REAL, NUMERIC,
	};

	private final Type type;
	private final String name;
	private final boolean primaryKey;
	private final boolean notNull;

	public Column(final Type type, final String name, final boolean primaryKey,
			final boolean notNull) {
		Logger.start();
		this.type = type;
		this.name = name;
		this.primaryKey = primaryKey;
		this.notNull = notNull;
	}

	public String toString() {
		Logger.start();
		return "Column " + this.hashCode() + "\n name=" + name + "\n type="
				+ typeToString() + "\n primaryKey="
				+ (primaryKey == true ? "Yes" : "No") + "\n notNull="
				+ (notNull == true ? "Yes" : "No");
	}

	public Type getType() {
		Logger.start();
		return type;
	}

	public String getName() {
		Logger.start();
		return name;
	}

	public boolean isPrimaryKey() {
		Logger.start();
		return primaryKey;
	}

	public boolean isNotNull() {
		Logger.start();
		return notNull;
	}

	private String typeToString() {
		long start = Logger.start();
		String result = null;
		switch (type) {
		case INTEGER:
			result = "INTEGER";
			break;
		case TEXT:
			result = "TEXT";
			break;
		case NONE:
			result = "NONE";
			break;
		case REAL:
			result = "REAL";
			break;
		case NUMERIC:
			result = "NUMERIC";
			break;
		}
		Logger.end(start);
		return result;
	}

	public String getDefinitionSQL() {
		long start = Logger.start();
		StringBuilder sql = new StringBuilder();
		sql.append(name);
		sql.append(" " + typeToString());
		if (primaryKey) {
			sql.append(" PRIMARY KEY ");
		} else if (notNull) {
			sql.append(" NOT NULL ");
		} else {
			sql.append(" ");
		}
		Logger.end(start);
		return sql.toString();
	}
}
