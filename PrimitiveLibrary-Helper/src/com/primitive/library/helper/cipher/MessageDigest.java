package com.primitive.library.helper.cipher;

import java.security.NoSuchAlgorithmException;

public class MessageDigest implements Digest{
	MessageDigestHelper.Algorithm algorithm  = null;
	/**
	 * @param algorithm
	 */
	public MessageDigest(final MessageDigestHelper.Algorithm algorithm){
		this.algorithm = algorithm;
	}

	@Override
	public String getDigestBase64(final byte[] message) throws NoSuchAlgorithmException{
		return MessageDigestHelper.getDigestBase64(algorithm, message);
	}

	@Override
	public byte[] getDigest(byte[] message) throws NoSuchAlgorithmException{
		return MessageDigestHelper.getDigest(algorithm, message);
	}
}
