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

import java.security.NoSuchAlgorithmException;

import com.primitive.library.helper.cipher.HashMacHelper.Algorithm;

/**
 * HashMac
 * @author xxx
 */
public class HashMac implements Digest{
	final Algorithm algorithm;
	final byte[] passphrase;

	public HashMac(final Algorithm algorithm,final byte[] passphrase){
		this.algorithm = algorithm;
		this.passphrase = passphrase;
	}

	@Override
	public String getDigestBase64(final byte[] message)
	{
		return HashMacHelper.getHMACBase64(algorithm, message, passphrase);
	}

	@Override
	public byte[] getDigest(byte[] message) throws NoSuchAlgorithmException {
		return HashMacHelper.getHMAC(algorithm, message, passphrase);
	}
}
