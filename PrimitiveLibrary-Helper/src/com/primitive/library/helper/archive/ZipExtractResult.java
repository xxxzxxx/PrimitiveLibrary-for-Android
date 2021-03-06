/**
 * ZipExtractResult
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.archive;

import java.util.zip.ZipEntry;

/**
*
* @author xxxzxxx
*
*/
public class ZipExtractResult extends ExtractResult{
	ZipEntry entry = null;
	public ZipExtractResult(final ZipEntry entry){
		this.entry = (ZipEntry)entry.clone();
	}

	public ZipEntry getZipEntry(){
		return entry;
	}
}
