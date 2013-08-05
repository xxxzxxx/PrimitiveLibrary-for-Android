package com.primitive.library.helper.archive.exception;

public class ArchiverWarning extends ArchiverException{

	/**
	 *
	 */
	private static final long serialVersionUID = 8545535287694478321L;
	/**
	 *
	 */
	public ArchiverWarning() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public ArchiverWarning(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public ArchiverWarning(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public ArchiverWarning(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
