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
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

import com.primitive.library.helper.Logger;
import com.primitive.library.helper.cipher.exception.CipherException;

/**
 * CipherHelper
 */
public class CipherHelper {
	public enum CipherMode {
		Decrypt,
		Encrypt,
	}
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
		NoPadding,
		PKCS1Padding,
		PKCS5Padding,
		PKCS7Padding,
		OAEPWithSHA1AndMGF1Padding,
		OAEPWithSHA256AndMGF1Paddin,
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
	private static String makeIdententity(
			final Algorithm alg,
			final Mode mode,
			final Padding padding)
	{
		final String Alg = AlgorithmMap.get(alg);
		final String Mode = ModeMap.get(mode);
		final String Padding = PaddingMap.get(padding);
		return Alg + "/" + Mode + "/" + Padding;
	}

	public static byte[] encrypt(
			final Algorithm alg,
			final Mode mode,
			final Padding padding,
			final String base64EncryptData,
			final String iv,
			final String passphrase,
			final int keyLength,
			final String encode) throws CipherException, UnsupportedEncodingException
	{
		byte[] decriptData = crypt(CipherMode.Encrypt,
				alg,
				mode,
				padding,Base64.decode(base64EncryptData.getBytes(encode), Base64.DEFAULT),
				iv.getBytes(encode),
				passphrase.getBytes(encode));
		return decriptData;
	}

	public static byte[] encrypt(
			final Algorithm alg,
			final Mode mode,
			final Padding padding,
			final byte[] encryptedData,
			final byte[] iv,
			final byte[] passphrase) throws CipherException
	{
		return  crypt(CipherMode.Encrypt,alg, mode,padding, encryptedData, iv,passphrase);
	}

	public static byte[] decrypt(
			final Algorithm alg,
			final Mode mode,
			final Padding padding,
			final String base64EncryptData,
			final String iv,
			final String passphrase,
			final String encode) throws UnsupportedEncodingException, CipherException
	{
		byte[] decriptData = crypt(CipherMode.Decrypt,
				alg,
				mode,
				padding,
				Base64.decode(base64EncryptData.getBytes(encode), Base64.DEFAULT),
				iv != null ? iv.getBytes(encode) : null,
				passphrase.getBytes(encode));
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
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws CipherException
	 */
	public static byte[] decrypt(
			final Algorithm alg,
			final Mode mode,
			final Padding padding,
			final byte[] encryptedData,
			final byte[] iv,
			final byte[] passphrase)
			throws CipherException
	{
		return  crypt(CipherMode.Decrypt,alg, mode,padding, encryptedData, iv,passphrase);
	}

	public static byte[] crypt(
			final CipherMode cipherMode,
			final Algorithm alg,
			final Mode mode,
			final Padding padding,
			final byte[] data,
			final byte[] iv,
			final byte[] passphrase)
			throws CipherException {
		Logger.start();
		Cipher cipher;
		byte[] result = null;
		try {
			if (!isSupport(alg, mode, padding)) {
				throw new NoSuchAlgorithmException();
			}
			String Alg = makeIdententity(alg, mode, padding);
			Logger.debug(Alg);
			cipher = Cipher.getInstance(Alg);
			final IvParameterSpec ivSpec = mode != Mode.ECB ?
				new IvParameterSpec(iv, 0, getBlockSize(alg)) :
				null;
			final SecretKeySpec keySpec = new SecretKeySpec(
				passphrase,
				0,
				passphrase.length,
				Alg);
			int cipher_mode = CipherMode.Decrypt == cipherMode ?
				Cipher.DECRYPT_MODE :
				Cipher.ENCRYPT_MODE ;
			cipher.init(cipher_mode, keySpec, ivSpec);
			result = cipher.doFinal(data);
		} catch (NoSuchPaddingException ex) {
			throw new CipherException(ex);
		} catch (InvalidKeyException ex) {
			throw new CipherException(ex);
		} catch (InvalidAlgorithmParameterException ex) {
			throw new CipherException(ex);
		} catch (IllegalBlockSizeException ex) {
			throw new CipherException(ex);
		} catch (BadPaddingException ex) {
			throw new CipherException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new CipherException(ex);
		}
		return result;
	}
}
