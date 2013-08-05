/**
 * VaridateException
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.common.exception;

public class Exception extends Throwable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1021196012988510891L;

	/**
	 *
	 */
	public Exception() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public Exception(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public Exception(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public Exception(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
