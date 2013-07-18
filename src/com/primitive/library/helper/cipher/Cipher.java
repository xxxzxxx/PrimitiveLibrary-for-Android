/**
 * CipherHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.cipher;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.util.Base64;

import com.primitive.library.helper.cipher.CipherHelper.Algorithm;
import com.primitive.library.helper.cipher.CipherHelper.Mode;
import com.primitive.library.helper.cipher.CipherHelper.Padding;

/**
 * CipherHelper
 */
public class Cipher {
	final Algorithm algorithm;
	final Mode mode;
	final Padding padding;
	final byte[] iv;
	final byte[] passphrase;

	public Cipher(	final Algorithm algorithm,
				final Mode mode,
				final Padding padding,
				final byte[] iv,
				final byte[] passphrase
				)
	{
		this.algorithm = algorithm;
		this.mode = mode;
		this.padding = padding;
		this.iv = iv;
		this.passphrase = passphrase;
	}

	/**
	 *
	 * @param base64EncryptData
	 * @param iv
	 * @param passphrase
	 * @param keyLength
	 * @param encode
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public byte[] decrypt(
			final String base64EncryptData,
			final int keyLength,
			final String encode
		)
			throws NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		return CipherHelper.decrypt(
				algorithm,
				mode,
				padding,
				Base64.decode(base64EncryptData.getBytes(encode), Base64.DEFAULT),
				iv,
				passphrase,
				keyLength);
	}

	/**
	 *
	 * @param encryptedData
	 * @param iv
	 * @param passphrase
	 * @param keyLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public  byte[] decrypt(
			final byte[] encryptedData,
			final byte[] passphrase,
			final int keyLength)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		return CipherHelper.decrypt(algorithm, mode, padding, encryptedData, iv, passphrase, keyLength);
	}
}
