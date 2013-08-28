package com.primitive.library.helper.serialize;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.primitive.library.helper.serialize.exception.SerializableException;

public interface Serializer {
	public void serialize(final OutputStream outputStream,final Serializable serializable) throws SerializableException;
	public Serializable deserialize(final InputStream inputStream) throws SerializableException;
}
