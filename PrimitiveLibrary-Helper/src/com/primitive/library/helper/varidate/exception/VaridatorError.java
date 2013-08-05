package com.primitive.library.helper.varidate.exception;

public class VaridatorError extends VaridatorException{
	/**
	 *
	 */
	private static final long serialVersionUID = 3472324371644713338L;

	/**
	 *
	 */
	public VaridatorError() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public VaridatorError(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public VaridatorError(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public VaridatorError(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
