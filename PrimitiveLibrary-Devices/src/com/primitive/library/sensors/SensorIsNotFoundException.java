package com.primitive.library.sensors;

public class SensorIsNotFoundException extends Throwable {
	private static final long serialVersionUID = 1053753052323779314L;

	public SensorIsNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public SensorIsNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
