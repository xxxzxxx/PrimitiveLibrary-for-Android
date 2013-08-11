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
