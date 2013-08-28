package com.primitive.library.helper.serialize;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.primitive.library.helper.serialize.exception.SerializableException;

public class SerializerImpl implements Serializer{
	public SerializerImpl(){

	}
	@Override
	public void serialize(final OutputStream outputStream,final Serializable serializable) throws SerializableException{
		StaticSerializer.serialize(outputStream,serializable);
	}
	@Override
	public Serializable deserialize(final InputStream inputStream) throws SerializableException{
		return StaticSerializer.deserialize(inputStream);
	}
}
