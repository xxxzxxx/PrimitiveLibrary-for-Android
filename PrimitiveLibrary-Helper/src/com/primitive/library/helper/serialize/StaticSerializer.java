package com.primitive.library.helper.serialize;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import com.primitive.library.helper.serialize.exception.SerializableException;

public class StaticSerializer {
	/**
	 *
	 * @param outputStream
	 * @param serializable
	 * @throws SerializableException
	 */
	public static void serialize(final OutputStream outputStream,final Serializable serializable) throws SerializableException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objos;
		byte[] serializeData;
		BufferedOutputStream buffos;
		try {
			objos = new ObjectOutputStream(baos);
			objos.writeObject(serializable);
			objos.close();
			serializeData = baos.toByteArray();
			buffos = new BufferedOutputStream(outputStream);
			buffos.write(serializeData);
		} catch (IOException ex) {
			throw new SerializableException(ex);
		}
	}
	/**
	 *
	 * @param inputStream
	 * @return
	 * @throws SerializableException
	 */
	public static Serializable deserialize(final InputStream inputStream) throws SerializableException{
		ObjectInputStream objin;
		try {
			objin = new ObjectInputStream(inputStream);
			return (Serializable)objin.readObject();
		} catch (StreamCorruptedException ex) {
			throw new SerializableException(ex);
		} catch (IOException ex) {
			throw new SerializableException(ex);
		} catch (ClassNotFoundException ex) {
			throw new SerializableException(ex);
		}
	}
}
