/**
 * Callback
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.contents;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.os.RemoteException;

public interface Callback {
	/**
	 * didQuery
	 * Please cancel the cursor by yourself.
	 * @param cursor
	 */
	public void didQuery(Cursor cursor);
	public void didError(RemoteException ex,ContentProviderClient client);
}
