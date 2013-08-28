package com.primitive.library.helper.serialize.exception;

import com.primitive.library.common.exception.Exception;

public class SerializableException extends Exception {
	private static final long serialVersionUID = -7802760831605545339L;

	public SerializableException(Throwable ex) {
		super(ex);
	}

}
