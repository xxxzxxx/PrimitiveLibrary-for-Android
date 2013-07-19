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
import com.primitive.library.helper.cipher.Digest;
import com.primitive.library.helper.cipher.MessageDigestHelper;
import com.primitive.library.helper.cipher.MessageDigestHelper.Algorithm;
import com.primitive.library.helper.varidate.exception.VaridatorException;

public class StringVaridator implements Varidator{
	/**  Digest */
	private final Digest digest;
	private final String original;
	private final String encode;
	private final String digestValue;

	/**
	 *
	 * @param original
	 * @param digest
	 * @param encode
	 * @param algorithm
	 */
	public StringVaridator(
			final String original,
			final String digestBase64,
			final String encode,
			final Digest digest)
	{
		this.original = original;
		this.digestValue = digestBase64;
		this.digest = digest;
		this.encode = encode;
	}

	/**
	 *
	 * @throws ObjectSettingException
	 */
	private void check()
		throws ObjectSettingException
	{
		Object[] compares = new Object[] { original, digest, digestValue, encode, };
		for (Object obj : compares) {
			if (obj == null) {
				throw new ObjectSettingException(
						"Parameter setup has a defect. ");
			}
		}
	}

	@Override
	public boolean compare() throws VaridatorException, ObjectSettingException
	{
		check();
		boolean result = false;
		try {
			byte[] data = original.getBytes(encode);
			String digestData = digest.getDigestBase64(data);
			result = digestData.equals(digestValue);
		} catch (NoSuchAlgorithmException ex) {
			Logger.err(ex);
			throw new VaridatorException(ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.err(ex);
			throw new VaridatorException(ex);
		}
		return result;
	}
}
