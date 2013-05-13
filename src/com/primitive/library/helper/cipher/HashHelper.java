/**
 * AbstractDataSource
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.helper.cipher;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

import com.primitive.library.helper.Logger;

public class HashHelper {
	public static String requestHMACSha256(
			final String key,
			final String content) {
		return requestHMAC(
				"HmacSHA256",
				key,
				content);
	}
	public static String requestHMAC(
			final String algorithm,
			final String key,
			final String content) {
		Logger.start();
		String signature = null;
		try {
			final byte[] secretyKeyBytes = key.getBytes();
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					secretyKeyBytes, algorithm);
			final Mac mac = Mac.getInstance(algorithm);
			mac.init(secretKeySpec);
			final byte[] data = content.getBytes("UTF-8");
			final byte[] rawHmac = mac.doFinal(data);
			signature = Base64.encodeToString(rawHmac, 0, rawHmac.length,Base64.DEFAULT);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return signature;
	}

}
