/**
 * VaridateSettingException
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.exception;

public class ObjectSettingException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -243687252306587658L;

	/**
	 *
	 */
	public ObjectSettingException() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public ObjectSettingException(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public ObjectSettingException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public ObjectSettingException(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
