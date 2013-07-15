/**
 * StringVaridator
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.varidate;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.primitive.library.exception.Exception;
import com.primitive.library.exception.ObjectSettingException;
import com.primitive.library.helper.Logger;
import com.primitive.library.helper.cipher.MessageDigestHelper;
import com.primitive.library.helper.cipher.MessageDigestHelper.Algorithm;

public class StringVaridator {
	Algorithm algorithm;
	String original;
	String encode;
	String digest;

	/**
	 *
	 */
	public StringVaridator() {
	}

	/**
	 *
	 * @param data
	 * @param digest
	 * @param encode
	 * @param algorithm
	 */
	public StringVaridator(final String data, final String digest,
			final String encode, final Algorithm algorithm) {
		this.original = data;
		this.digest = digest;
		this.algorithm = algorithm;
		this.encode = encode;
	}

	/**
	 *
	 * @throws ObjectSettingException
	 */
	private void check() throws ObjectSettingException {
		Object[] compares = new Object[] { original, digest, algorithm, encode, };
		for (Object obj : compares) {
			if (obj == null) {
				throw new ObjectSettingException(
						"Parameter setup has a defect. ");
			}
		}
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean compare() throws Exception {
		check();
		boolean result = false;
		try {
			byte[] data = original.getBytes(encode);
			String digestData = MessageDigestHelper.getDigestBase64(algorithm,
					data);
			result = digestData.equals(digest);
		} catch (NoSuchAlgorithmException ex) {
			Logger.err(ex);
			throw new Exception(ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.err(ex);
			throw new Exception(ex);
		}
		return result;
	}
}
