/**
 * ZipArchiveHelper
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.archive;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.primitive.library.common.log.Logger;
import com.primitive.library.helper.archive.exception.ArchiverError;
import com.primitive.library.helper.archive.exception.ArchiverWarning;

/**
 *
 * @author xxxzxxx
 *
 */
public class ZipArchiveHelper {

	/**
	 *
	 * @param archiveFile
	 * @param outputDirectory
	 * @param ignore
	 * @return
	 * @throws ArchiverError
	 * @throws ArchiverWarning
	 */
	public static boolean extractAll(
		final String archiveFilePath,
		final String outputDirectoryPath,
		final boolean ignore,
		ExtractResults extractResults
		)
		throws ArchiverWarning , ArchiverError
	{
		File archiveFile = new File(archiveFilePath);
		File outputDirectory = new File(outputDirectoryPath);
		return extractAll(archiveFile,outputDirectory,ignore,extractResults);
	}

	/**
	 *
	 * @param archiveFile
	 * @param outputDirectory
	 * @param ignore
	 * @return
	 * @throws ArchiverError
	 * @throws ArchiverWarning
	 */
	public static boolean extractAll(
		final File archiveFile,
		final File outputDirectory,
		final boolean ignore,
		ExtractResults extractResults
		)
		throws ArchiverWarning , ArchiverError
	{
		ZipFile zipFile = null;
		final String outputDirectoryPath = outputDirectory.getPath();
		boolean result = false;
		try {
			if (
				outputDirectory.canWrite() == false ||
				outputDirectory.isDirectory() == false
			){
				throw new ArchiverError("can not have access to the specified directory");
			}

			zipFile = new ZipFile(archiveFile);
			for (
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
					entries.hasMoreElements();
				)
			{
				ZipEntry entry = entries.nextElement();
				final String outputFilePath = outputDirectoryPath+"/"+entry.getName();
				File extractFile = new File(outputFilePath);
				if(entry.isDirectory()){
					if(extractFile.isFile()){
						throw new ArchiverError("path that you specify is already exists as a file");
					}else if(extractFile.mkdirs() || extractFile.isDirectory()){
						continue;
					}else {
						throw new ArchiverError("path that you specify is already exists as a file");
					}
				}
				ZipExtractResult extractResult = new ZipExtractResult(entry);
				FileOutputStream extractFS = new FileOutputStream(extractFile);
				BufferedOutputStream bos = new BufferedOutputStream(extractFS);
				boolean varidate = false;
				try {
					varidate = varidateEntry(zipFile,entry,bos);
					if(varidate == false){
						bos.flush();
						bos.close();
						extractFS.close();
						extractFile.delete();
					} else {
						bos.close();
						extractFS.close();
					}
				} finally {
					if(varidate == false) {
						result = varidate;
						ArchiverWarning ex = new ArchiverWarning("");
						if(ignore != false){
							throw ex;
						} else {
							extractResult.warning = ex;
						}
					}
					if(extractResults != null){
						extractResult.varidateError = varidate;
						extractResult.name = entry.getName();
						extractResult.path = outputFilePath;
						extractResults.results.add(extractResult);
					}
				}
			}
		} catch (FileNotFoundException ex){
			Logger.err(ex);
			throw new ArchiverError(ex);
		} catch (ZipException ex) {
			Logger.err(ex);
			throw new ArchiverError(ex);
		} catch (IOException ex) {
			Logger.err(ex);
			throw new ArchiverError(ex);
		} finally {
			if(zipFile != null){
				try {
					zipFile.close();
				} catch (IOException ex) {
					Logger.warm(ex);
				}
			}
		}
		if(extractResults != null){
			extractResults.varidateError = result;
		}
		return result;
	}

	/**
	 *
	 * @param zipFile
	 * @param entry
	 * @param outputStream
	 * @return
	 * @throws IOException
	 */
	public static boolean varidateEntry(
			final ZipFile zipFile,
			final ZipEntry entry,
			final OutputStream outputStream
		)  throws IOException
	{
		InputStream inputStream = null;
		final long startTime = System.currentTimeMillis();
		try {
			inputStream = zipFile.getInputStream(entry);
			final long crcValue = entry.getCrc();
			if(crcValue == -1){
				//TODO : Throwする?
			}

			final CRC32 crc = new CRC32();
			byte[] buffer = new byte[256];//CRC32なので256
			for(int length = 0; 0 < (length = inputStream.read(buffer)); )
			{
				crc.update(buffer,0,length);
				if(outputStream != null){
					outputStream.write(buffer);
				}
			}
			Logger.times(startTime,System.currentTimeMillis());
			boolean result = crc.getValue() == crcValue;
			return result;
		} finally {
			if(inputStream != null){
				inputStream.close();
			}
		}
	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean varidate(
		final File file,
		ExtractResults extractResults
		)  throws IOException
	{
		ZipFile zipFile = new ZipFile(file);
		boolean result = false;
		try {
			for (
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
					entries.hasMoreElements();
				)
			{
				ZipEntry entry = entries.nextElement();
				if(entry.isDirectory()) continue;

				boolean varidate = varidateEntry(zipFile,entry,null);

				if(varidate == false){
					result = true;
				}

				if(extractResults != null){
					ExtractResult extractResult = new ExtractResult();
					extractResult.name = entry.getName();
					extractResult.varidateError = varidate;
					extractResults.results.add(extractResult);
				}

			}
		} finally {
			if(zipFile != null){
				zipFile.close();
			}
		}
		if(extractResults != null){
			extractResults.varidateError = result;
		}
		return result;
	}
}
