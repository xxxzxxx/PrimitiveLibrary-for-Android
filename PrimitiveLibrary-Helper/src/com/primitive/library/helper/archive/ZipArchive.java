/**
 * ZipArchive
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.archive;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import com.primitive.library.common.log.Logger;
import com.primitive.library.helper.archive.exception.ArchiverError;
import com.primitive.library.helper.archive.exception.ArchiverWarning;

/**
 *
 * @author xxxzxxx
 *
 */
public class ZipArchive extends Archiver
{
	/**
	 * @param archive
	 */
	public ZipArchive(
		final File archive
		)
	{
		super(archive);
	}

	@Override
	public boolean extractAll(
		final File outputDirectory,
		final boolean ignore,
		ExtractResults extractResults
		)
		throws ArchiverWarning, ArchiverError
	{
		return ZipArchiveHelper.extractAll(this.archive,outputDirectory,ignore,extractResults);
	}

	@Override
	public boolean extractAll(
		final String outputDirectory,
		final boolean ignore,
		ExtractResults extractResults
		)
		throws ArchiverWarning, ArchiverError
	{
		return ZipArchiveHelper.extractAll(this.archive,new File(outputDirectory),ignore,extractResults);
	}

	@Override
	public boolean varidate()
		throws ArchiverWarning, ArchiverError
	{
		try {
			return ZipArchiveHelper.varidate(this.archive,null);
		}catch (ZipException ex){
			Logger.err(ex);
			throw new ArchiverError(ex);
		}catch (IOException ex){
			Logger.err(ex);
			throw new ArchiverError(ex);
		}
	}

	@Override
	public boolean varidate(
		ExtractResults extractResults
		)
		throws ArchiverWarning, ArchiverError
	{
		try {
			return  ZipArchiveHelper.varidate(this.archive,extractResults);
		}catch (IOException ex){
			Logger.err(ex);
			throw new ArchiverError(ex);
		}
	}
}
