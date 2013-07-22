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

public class Serializer {
	public static void serialize(final OutputStream outputStream,final Serializable serializable) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objos = new ObjectOutputStream(baos);
		objos.writeObject(serializable);
		objos.close();
		byte[] serializeData = baos.toByteArray();
		BufferedOutputStream buffos = new BufferedOutputStream(outputStream);
		buffos.write(serializeData);
	}
	public static Serializable deserialize(final InputStream inputStream) throws StreamCorruptedException, IOException, ClassNotFoundException{
		ObjectInputStream objin = new ObjectInputStream(inputStream);
		return (Serializable)objin.readObject();
	}
}
