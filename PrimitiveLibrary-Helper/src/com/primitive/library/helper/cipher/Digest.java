package com.primitive.library.helper.cipher;

import java.security.NoSuchAlgorithmException;

public interface Digest {
	public String getDigestBase64(final byte[] message) throws NoSuchAlgorithmException;
	public byte[] getDigest(byte[] message) throws NoSuchAlgorithmException;
}
