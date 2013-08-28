
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

import com.primitive.library.helper.cipher.Cipher;
import com.primitive.library.helper.cipher.exception.CipherException;
import com.primitive.library.helper.serialize.exception.SerializableException;

public class CryptoSerializerImpl implements Serializer{
	final Cipher cipher;

	/**
	 *
	 * @param cipher
	 */
	public CryptoSerializerImpl(Cipher cipher){
		this.cipher = cipher;
	}

	@Override
	public void serialize(final OutputStream outputStream,final Serializable serializable) throws SerializableException{
		serialize(outputStream,serializable,cipher);
	}

	@Override
	public Serializable deserialize(final InputStream inputStream) throws SerializableException{
		return deserialize(inputStream,cipher);
	}

	/**
	 *
	 * @param outputStream
	 * @param serializable
	 * @param cipher
	 * @throws SerializableException
	 */
	public static void serialize(final OutputStream outputStream,final Serializable serializable,final Cipher cipher) throws SerializableException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objos;
		byte[] serializeData;
		byte[] encryptSerializeData;
		BufferedOutputStream buffos = null;
		try {
			objos = new ObjectOutputStream(baos);
			objos.writeObject(serializable);
			objos.close();
			serializeData = baos.toByteArray();
			encryptSerializeData = cipher.encrypt(serializeData);
			buffos = new BufferedOutputStream(outputStream);
			buffos.write(encryptSerializeData);
			buffos.close();
		} catch (IOException ex) {
			throw new SerializableException(ex);
		} catch (CipherException ex) {
			throw new SerializableException(ex);
		}
		finally{
			if(buffos != null){
				try {
					buffos.close();
				} catch (IOException ex) {
					throw new SerializableException(ex);
				}
			}
		}
	}

	/**
	 *
	 * @param inputStream
	 * @param cipher
	 * @return
	 * @throws SerializableException
	 */
	public static Serializable deserialize(final InputStream inputStream,final Cipher cipher) throws SerializableException{
		int readSize;
		try {
			readSize = inputStream.read();
			byte[] readBuffer = new byte[readSize];
			inputStream.read(readBuffer);
			byte[] decriptData = cipher.decrypt(readBuffer);
			ByteArrayInputStream bis = new ByteArrayInputStream(decriptData);
			ObjectInputStream objin = new ObjectInputStream(bis);
			return (Serializable)objin.readObject();
		} catch (IOException ex) {
			throw new SerializableException(ex);
		} catch (ClassNotFoundException ex) {
			throw new SerializableException(ex);
		} catch (CipherException ex) {
			throw new SerializableException(ex);
		}
	}
}
