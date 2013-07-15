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
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

import com.primitive.library.helper.Logger;

/**
 * HashHelper
 * 
 * @author xxx
 */
public class HashHelper {
	/**
	 * @author xxx
	 */
	public enum Algorithm {
		HmacMD5, HmacSHA1, HmacSHA256,
	}

	/** AlgorithmMap */
	private static final HashMap<Algorithm, String> AlgorithmMap;
	static {
		AlgorithmMap = new HashMap<Algorithm, String>();
		AlgorithmMap.put(Algorithm.HmacMD5, "HmacMD5");
		AlgorithmMap.put(Algorithm.HmacSHA1, "HmacSHA1");
		AlgorithmMap.put(Algorithm.HmacSHA256, "HmacSHA256");
	}

	/**
	 * getHMACBase64
	 * 
	 * @param alg
	 * @param message
	 * @param passphrase
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getHMACBase64(final Algorithm alg,
			final String message, final String passphrase, final String encode)
			throws UnsupportedEncodingException {
		Logger.start();
		byte[] rawHmac = getHMAC(alg, message.getBytes(encode),
				passphrase.getBytes(encode));
		String signature = Base64.encodeToString(rawHmac, 0, rawHmac.length,
				Base64.DEFAULT);
		return signature;
	}

	/**
	 * getHMAC
	 * 
	 * @param alg
	 * @param message
	 * @param passphrase
	 * @return
	 */
	public static byte[] getHMAC(Algorithm alg, final byte[] message,
			final byte[] passphrase) {
		Logger.start();
		String algorithm = AlgorithmMap.get(alg);
		try {
			final byte[] secretyKeyBytes = passphrase;
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					secretyKeyBytes, algorithm);
			final Mac mac = Mac.getInstance(algorithm);
			mac.init(secretKeySpec);
			final byte[] data = message;
			final byte[] rawHmac = mac.doFinal(data);
			return rawHmac;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
