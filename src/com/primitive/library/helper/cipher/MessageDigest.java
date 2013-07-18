package com.primitive.library.helper.cipher;

import java.security.NoSuchAlgorithmException;

public class MessageDigest {
	MessageDigestHelper.Algorithm algorithm  = null;
	/**
	 * @param algorithm
	 */
	public MessageDigest(final MessageDigestHelper.Algorithm algorithm ){
		this.algorithm = algorithm;
	}
	/**
	 *
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String getDigestBase64(byte[] message) throws NoSuchAlgorithmException{
		return MessageDigestHelper.getDigestBase64(algorithm, message);
	}
	/**
	 *
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] getDigest(byte[] message) throws NoSuchAlgorithmException{
		return MessageDigestHelper.getDigest(algorithm, message);
	}
}
