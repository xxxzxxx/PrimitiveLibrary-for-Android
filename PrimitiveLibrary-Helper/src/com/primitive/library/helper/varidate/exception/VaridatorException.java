package com.primitive.library.helper.varidate.exception;

public class VaridatorException extends com.primitive.library.common.exception.Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public VaridatorException() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public VaridatorException(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public VaridatorException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public VaridatorException(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
