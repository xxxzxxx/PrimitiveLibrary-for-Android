/**
 * Archiver
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.archive;

import java.io.File;

import com.primitive.library.helper.archive.exception.ArchiverError;
import com.primitive.library.helper.archive.exception.ArchiverWarning;

/**
 *
 * @author xxxzxxx
 *
 */
public abstract class Archiver {
	protected final File archive;

	/**
	 *
	 * @param archive
	 */
	public Archiver(final String archive){
		this.archive = new File(archive);
	}

	/**
	 *
	 * @param archive
	 */
	public Archiver(final File archive){
		this.archive = archive;
	}

	/**
	 *
	 * @param outputDirectory
	 * @param ignore
	 * @param extractResults
	 * @return
	 * @throws ArchiverWarning
	 * @throws ArchiverError
	 */
	public abstract boolean extractAll(
			final File outputDirectory,
			final boolean ignore,
			ExtractResults extractResults) throws ArchiverWarning , ArchiverError;

	/**
	 *
	 * @param outputDirectory
	 * @param ignore
	 * @param extractResults
	 * @return
	 * @throws ArchiverWarning
	 * @throws ArchiverError
	 */
	public abstract boolean extractAll(
			final String outputDirectory,
			final boolean ignore,
			ExtractResults extractResults)
			throws ArchiverWarning, ArchiverError;

	/**
	 *
	 * @return
	 * @throws ArchiverWarning
	 * @throws ArchiverError
	 */
	public abstract boolean varidate()
			throws ArchiverWarning, ArchiverError;

	/**
	 *
	 * @param extractResults
	 * @return
	 * @throws ArchiverWarning
	 * @throws ArchiverError
	 */
	public abstract boolean varidate(ExtractResults extractResults)
			throws ArchiverWarning, ArchiverError;
}
