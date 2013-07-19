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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.primitive.library.exception.Exception;
import com.primitive.library.exception.ObjectSettingException;
import com.primitive.library.helper.Logger;
import com.primitive.library.helper.cipher.Digest;
import com.primitive.library.helper.varidate.exception.VaridatorException;

public class StreamVaridator implements Closeable,Varidator{
	/**  Digest */
	private final Digest digest;
	/**  Digest value  */
	private String digestValue;
	/**  */
	private InputStream inputStream;
	/** finalize Guardian */
	@SuppressWarnings("unused")
	private final Object finalizeGuardian = new Object(){
		protected void finalize() throws Throwable {
			StreamVaridator.this.close();
		}
	};

	/**
	 *
	 * @param target
	 * @param digestValue
	 * @param algorithm
	 */
	public StreamVaridator(final InputStream inputStream, final String digestValue,final Digest digest) {
		this.inputStream = inputStream;
		this.digestValue = digestValue;
		this.digest = digest;
	}

	/**
	 * check parameters
	 *
	 * @throws ObjectSettingException
	 */
	private void check() throws ObjectSettingException {
		if (inputStream == null) {
			throw new ObjectSettingException("Parameter setup has a defect. ");
		}
		if (digestValue == null ) {
			throw new ObjectSettingException("Parameter setup has a defect. ");
		}
	}

	/**
	 *
	 *
	 * @return
	 * @throws ObjectSettingException
	 * @throws Exception
	 */
	public boolean compare() throws VaridatorException, ObjectSettingException {
		check();
		boolean result = false;
		try {
			int size = inputStream.available();
			byte[] data = new byte[size];
			if (0 != inputStream.read(data, 0, size)) {
				String digestData = digest.getDigestBase64(data);
				result = digestData.equals(digestValue);
			}
		} catch (NoSuchAlgorithmException ex) {
			Logger.err(ex);
			throw new VaridatorException(ex);
		} catch (IOException ex) {
			Logger.err(ex);
			throw new VaridatorException(ex);
		}
		return result;
	}

	public String getDigestValue() {
		return digestValue;
	}

	public void setDigestValue(String digest) {
		this.digestValue = digest;
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
