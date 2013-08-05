
package com.primitive.library.helper.serialize;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.security.NoSuchAlgorithmException;

import com.primitive.library.helper.cipher.Cipher;
import com.primitive.library.helper.cipher.exception.CipherException;

public class CryptoSerializer {
	public static void serialize(final OutputStream outputStream,final Serializable serializable,final Cipher cipher) throws IOException, CipherException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objos = new ObjectOutputStream(baos);
		objos.writeObject(serializable);
		objos.close();
		byte[] serializeData = baos.toByteArray();
		byte[] encryptSerializeData = cipher.encrypt(serializeData);
		BufferedOutputStream buffos = new BufferedOutputStream(outputStream);
		buffos.write(encryptSerializeData);
		buffos.close();
	}
	public static Serializable deserialize(final InputStream inputStream,final Cipher cipher) throws StreamCorruptedException, IOException, ClassNotFoundException, CipherException{
		int readSize = inputStream.read();
		byte[] readBuffer = new byte[readSize];
		inputStream.read(readBuffer);
		byte[] decriptData = cipher.decrypt(readBuffer);
		ByteArrayInputStream bis = new ByteArrayInputStream(decriptData);
		ObjectInputStream objin = new ObjectInputStream(bis);
		return (Serializable)objin.readObject();
	}
}
