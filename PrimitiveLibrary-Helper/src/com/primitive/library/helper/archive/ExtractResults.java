/**
 * ExtractResults
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.archive;

import java.util.ArrayList;

/**
*
* @author xxxzxxx
*
*/
public class ExtractResults {
	ArrayList<ExtractResult> results = new ArrayList<ExtractResult>();
	boolean varidateError;
	/**
	 *
	 * @return
	 */
	public boolean isError(){
		for(ExtractResult result : results){
			if(result.isError()){
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @return
	 */
	public ExtractResult[] isErrors(){
		ArrayList<ExtractResult> errors = new ArrayList<ExtractResult>();
		for(ExtractResult result : results){
			if(result.isError()){
				errors.add(result);
			}
		}
		return errors.toArray(new ExtractResult[0]);
	}
}
