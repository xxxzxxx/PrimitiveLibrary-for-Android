/**
 * HashHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.helper.cipher;

import java.io.UnsupportedEncodingException;

import com.primitive.library.helper.cipher.HashMacHelper.Algorithm;

/**
 * HashMac
 * @author xxx
 */
public class HashMac {
	final Algorithm algorithm;

	public HashMac(final Algorithm algorithm){
		this.algorithm = algorithm;
	}

	public String getHMACBase64(
			final String message,
			final String passphrase,
			final String encode
		) throws UnsupportedEncodingException
	{
		return HashMacHelper.getHMACBase64(algorithm, message, passphrase,encode);
	}

	/**
	 * getHMAC
	 *
	 * @param alg
	 * @param message
	 * @param passphrase
	 * @return
	 */
	public byte[] getHMAC(
			final byte[] message,
			final byte[] passphrase)
	{
		return HashMacHelper.getHMAC(algorithm, message, passphrase);
	}

}
