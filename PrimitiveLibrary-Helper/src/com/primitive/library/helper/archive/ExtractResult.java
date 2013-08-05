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

/**
*
* @author xxxzxxx
*
*/
public class ExtractResult {

    boolean varidateError = false;
    String name = null;
    String path = null;
    Throwable warning = null;
    public ExtractResult(){

    }
    public boolean isVaridateError() {
        return varidateError;
    }
    public String getPath() {
        return path;
    }
    public String getName() {
        return name;
    }

    public Throwable getWarning() {
        return warning;
    }

    public boolean isError() {
        return warning != null ? true : false;
    }
}
