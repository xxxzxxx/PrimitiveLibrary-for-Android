package com.primitive.library.helper.varidate.exception;

public class VaridatorWarning extends VaridatorException{

	/**
	 *
	 */
	private static final long serialVersionUID = 6274294963756535165L;

	/**
	 *
	 */
	public VaridatorWarning() {
		super();
	}

	/**
	 *
	 * @param ex
	 */
	public VaridatorWarning(Throwable ex) {
		super(ex);
	}

	/**
	 *
	 * @param detailMessage
	 */
	public VaridatorWarning(String detailMessage) {
		super(detailMessage);
	}

	/**
	 *
	 * @param detailMessage
	 * @param ex
	 */
	public VaridatorWarning(String detailMessage, Throwable ex) {
		super(detailMessage, ex);
	}
}
