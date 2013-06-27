/**
 * CipherHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.cipher;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

import com.primitive.library.helper.Logger;

/**
 * CipherHelper
 */
public class CipherHelper {
	/**
	 * 
	 * @author xxx
	 * 
	 */
	public enum Algorithm {
		AES, DES, DESede, RSA,
	}

	/**
	 * 
	 * @author xxx
	 * 
	 */
	public enum Mode {
		CBC, ECB,
	}

	/**
	 * 
	 * @author xxx
	 * 
	 */
	public enum Padding {
		NoPadding, PKCS1Padding, PKCS5Padding, PKCS7Padding, OAEPWithSHA1AndMGF1Padding, OAEPWithSHA256AndMGF1Paddin,
	}

	/** AlgorithmMap */
	private final static HashMap<Algorithm, String> AlgorithmMap;
	/** ModeMap */
	private final static HashMap<Mode, String> ModeMap;
	/** PaddingMap */
	private final static HashMap<Padding, String> PaddingMap;
	static {
		AlgorithmMap = new HashMap<Algorithm, String>();
		ModeMap = new HashMap<Mode, String>();
		PaddingMap = new HashMap<Padding, String>();
		AlgorithmMap.put(Algorithm.AES, "AES");
		AlgorithmMap.put(Algorithm.DES, "DES");
		AlgorithmMap.put(Algorithm.DESede, "DESede");
		AlgorithmMap.put(Algorithm.RSA, "RSA");
		ModeMap.put(Mode.CBC, "CBC");
		ModeMap.put(Mode.ECB, "ECB");
		PaddingMap.put(Padding.NoPadding, "NoPadding");
		PaddingMap.put(Padding.PKCS1Padding, "PKCS1Padding");
		PaddingMap.put(Padding.PKCS5Padding, "PKCS5Padding");
		PaddingMap.put(Padding.PKCS7Padding, "PKCS7Padding");
		PaddingMap.put(Padding.OAEPWithSHA1AndMGF1Padding,
				"OAEPWithSHA-1AndMGF1Padding");
		PaddingMap.put(Padding.OAEPWithSHA256AndMGF1Paddin,
				"OAEPWithSHA-256AndMGF1Padding");
	}

	/**
	 * 
	 * @param alg
	 * @param mode
	 * @param padding
	 * @return
	 */
	public static boolean isSupport(final Algorithm alg, final Mode mode,
			final Padding padding) {
		switch (alg) {
		case AES:
			return isSupportAES(mode, padding);
		case DES:
			return isSupportDES(mode, padding);
		case DESede:
			return isSupportDESede(mode, padding);
		case RSA:
			return isSupportRSA(mode, padding);
		}
		return false;
	}

	/**
	 * 
	 * @param alg
	 * @return
	 */
	public static int getBlockSize(final Algorithm alg) {
		switch (alg) {
		case AES:
			return 128 / 8;
		case DES:
			return 64 / 8;
		case DESede:
			return 64 / 8;
		case RSA:
		default:
		}
		return -1;
	}

	/**
	 * 
	 * @param mode
	 * @param padding
	 * @return
	 */
	private static boolean isSupportAES(Mode mode, Padding padding) {
		switch (padding) {
		case OAEPWithSHA1AndMGF1Padding:
		case OAEPWithSHA256AndMGF1Paddin:
			return false;
		default:
		}
		return true;
	}

	/**
	 * 
	 * @param mode
	 * @param padding
	 * @return
	 */
	private static boolean isSupportDES(Mode mode, Padding padding) {
		switch (padding) {
		case OAEPWithSHA1AndMGF1Padding:
		case OAEPWithSHA256AndMGF1Paddin:
			return false;
		default:
		}
		return true;
	}

	/**
	 * 
	 * @param mode
	 * @param padding
	 * @return
	 */
	private static boolean isSupportDESede(Mode mode, Padding padding) {
		switch (padding) {
		case OAEPWithSHA1AndMGF1Padding:
		case OAEPWithSHA256AndMGF1Paddin:
			return false;
		default:
		}
		return true;
	}

	/**
	 * 
	 * @param mode
	 * @param padding
	 * @return
	 */
	private static boolean isSupportRSA(Mode mode, Padding padding) {
		switch (mode) {
		case CBC:
			return false;
		default:
		}
		switch (padding) {
		case NoPadding:
		case PKCS5Padding:
		case PKCS7Padding:
			return false;
		default:
		}
		return true;
	}

	/**
	 * 
	 * @param alg
	 * @param mode
	 * @param padding
	 * @return
	 */
	private static String makeIdententity(final Algorithm alg, final Mode mode,
			final Padding padding) {
		final String Alg = AlgorithmMap.get(alg);
		final String Mode = ModeMap.get(mode);
		final String Padding = PaddingMap.get(padding);
		return Alg + "/" + Mode + "/" + Padding;
	}

	/**
	 * 
	 * @param alg
	 * @param mode
	 * @param padding
	 * @param encryptdata
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
	public static byte[] decrypt(final Algorithm alg, final Mode mode,
			final Padding padding, final String encryptdata, final String iv,
			final String passphrase, final int keyLength, final String encode)
			throws NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		byte[] decriptData = decrypt(alg, mode, padding,
				Base64.decode(encryptdata.getBytes(encode), Base64.DEFAULT),
				iv.getBytes(encode), passphrase.getBytes(encode), keyLength);
		return decriptData;
	}

	/**
	 * 
	 * @param alg
	 * @param mode
	 * @param padding
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
	public static byte[] decrypt(final Algorithm alg, final Mode mode,
			final Padding padding, final byte[] encryptedData, final byte[] iv,
			final byte[] passphrase, final int keyLength)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		Logger.start();
		if (!isSupport(alg, mode, padding)) {
			throw new NoSuchAlgorithmException();
		}
		String Alg = makeIdententity(alg, mode, padding);
		Logger.debug(Alg);
		final Cipher cipher = Cipher.getInstance(Alg);
		final IvParameterSpec ivSpec = mode != Mode.ECB ? new IvParameterSpec(
				iv, 0, getBlockSize(alg)) : null;
		final SecretKeySpec keySpec = new SecretKeySpec(passphrase, 0,
				keyLength, Alg);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		byte[] decript = cipher.doFinal(encryptedData);
		return decript;
	}
}
