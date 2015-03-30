package com.primitive.library.sensors;

public class PurgingException extends Throwable
{
	private static final long serialVersionUID = 1053753052323779314L;

	public PurgingException(String detailMessage)
	{
		super(detailMessage);
	}
	public PurgingException(String detailMessage, Throwable throwable)
	{
		super(detailMessage,throwable);
	}
}
