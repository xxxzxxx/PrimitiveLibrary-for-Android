/**
 * MessageDigestHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import android.util.Base64;

/**
 * MessageDigestHelper
 * 
 * @author xxx
 */
public class MessageDigestHelper {
	public enum Algorithm {
		MD5, SHA1, SHA256, SHA384, SHA512,
	};

	private static final String ALG_MD5 = "MD5";
	private static final String ALG_SHA1 = "SHA-1";
	private static final String ALG_SHA256 = "SHA-256";
	private static final String ALG_SHA384 = "SHA-384";
	private static final String ALG_SHA512 = "SHA-512";
	/** AlgorithmMap */
	private static HashMap<Algorithm, String> AlgorithmMap;
	static {
		AlgorithmMap = new HashMap<Algorithm, String>();
		AlgorithmMap.put(Algorithm.MD5, ALG_MD5);
		AlgorithmMap.put(Algorithm.SHA1, ALG_SHA1);
		AlgorithmMap.put(Algorithm.SHA256, ALG_SHA256);
		AlgorithmMap.put(Algorithm.SHA384, ALG_SHA384);
		AlgorithmMap.put(Algorithm.SHA512, ALG_SHA512);
	}

	/**
	 * getDigestBase64
	 * 
	 * @param algorithm
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getDigestBase64(Algorithm algorithm, byte[] message)
			throws NoSuchAlgorithmException {
		byte[] digest = getDigest(algorithm, message);
		String encode = Base64.encodeToString(digest, Base64.DEFAULT);
		return encode;
	}

	/**
	 * getDigest
	 * 
	 * @param algorithm
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getDigest(Algorithm algorithm, byte[] message)
			throws NoSuchAlgorithmException {
		String algorithm_id = AlgorithmMap.get(algorithm);
		MessageDigest md = MessageDigest.getInstance(algorithm_id);
		md.reset();
		md.update(message);
		byte[] digest = md.digest();
		return digest;
	}
}
