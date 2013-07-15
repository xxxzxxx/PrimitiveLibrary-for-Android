/**
 * FileVaridator
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.varidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.primitive.library.exception.Exception;
import com.primitive.library.exception.ObjectSettingException;
import com.primitive.library.helper.Logger;
import com.primitive.library.helper.cipher.MessageDigestHelper;
import com.primitive.library.helper.cipher.MessageDigestHelper.Algorithm;

public class FileVaridator {
	/**  */
	private Algorithm algorithm;
	/**  */
	private String digest;
	/**  */
	private InputStream inputStream;

	/**
	 *
	 */
	public FileVaridator() {
	}

	/**
	 *
	 * @param filePath
	 * @param digest
	 * @param algorithm
	 * @throws FileNotFoundException
	 */
	public FileVaridator(final String filePath, final String digest,
			final Algorithm algorithm) throws FileNotFoundException {
		File file = new File(filePath);
		this.inputStream = new FileInputStream(file);
		this.digest = digest;
		this.algorithm = algorithm;
	}

	/**
	 *
	 * @param target
	 * @param digest
	 * @param algorithm
	 */
	public FileVaridator(final InputStream inputStream, final String digest,
			final Algorithm algorithm) {
		this.inputStream = inputStream;
		this.digest = digest;
		this.algorithm = algorithm;
	}

	/**
	 * check parameters
	 *
	 * @throws ObjectSettingException
	 */
	private void check() throws ObjectSettingException {
		if (digest == null || inputStream == null) {
			throw new ObjectSettingException("Parameter setup has a defect. ");
		}
	}

	/**
	 *
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean compare() throws Exception {
		check();
		boolean result = false;
		try {
			int size = inputStream.available();
			byte[] data = new byte[size];
			if (0 != inputStream.read(data, 0, size)) {
				String digestData = MessageDigestHelper.getDigestBase64(
						algorithm, data);
				result = digestData.equals(digest);
			}
		} catch (NoSuchAlgorithmException ex) {
			Logger.err(ex);
			throw new Exception(ex);
		} catch (IOException ex) {
			Logger.err(ex);
			throw new Exception(ex);
		}
		return result;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void close() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
