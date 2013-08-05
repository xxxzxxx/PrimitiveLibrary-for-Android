package com.primitive.library.helper.archive.exception;

public class ArchiverError extends ArchiverException{
	private static final long serialVersionUID = 6915309943449334241L;
	/**
	 *
	 */
	public ArchiverError() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public ArchiverError(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public ArchiverError(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public ArchiverError(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
