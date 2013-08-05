package com.primitive.library.helper.archive.exception;

public class ArchiverException extends com.primitive.library.common.exception.Exception{
	private static final long serialVersionUID = 3355962816108443757L;
	/**
	 *
	 */
	public ArchiverException() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public ArchiverException(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public ArchiverException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public ArchiverException(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
