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

import com.primitive.library.helper.cipher.CipherHelper.Algorithm;
import com.primitive.library.helper.cipher.CipherHelper.Mode;
import com.primitive.library.helper.cipher.CipherHelper.Padding;
import com.primitive.library.helper.cipher.exception.CipherException;

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

	public  byte[] decrypt(
			final byte[] encryptedData
			) throws CipherException
	{
		return CipherHelper.decrypt(
				algorithm,
				mode,
				padding,
				encryptedData,
				iv,
				passphrase
		);
	}

	public  byte[] encrypt(
			final byte[] originalData
			) throws CipherException
	{
		return CipherHelper.encrypt(
				algorithm,
				mode,
				padding,
				originalData,
				iv,
				passphrase
		);
	}
}
