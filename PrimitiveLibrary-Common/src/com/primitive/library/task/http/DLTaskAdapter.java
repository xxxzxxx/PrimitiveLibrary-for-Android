/**
 * DLTaskAdapter
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.task.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpRequestBase;

import com.primitive.library.common.log.Logger;

import android.app.Activity;

/**
 * @author xxx
 *
 */
public class DLTaskAdapter implements HttpTask.Adapter{
	protected final Activity activity;
	protected final Runnable action;
	protected final File saveDirectory;
	protected int bufferSize = 1024 * 8;

	/**
	 *
	 * @param context
	 * @param adapter
	 * @param action
	 */
	public DLTaskAdapter(final File saveDirectory,final Activity activity,final Runnable action){
		this.activity = activity;
		this.action = action;
		this.saveDirectory = saveDirectory;
		if (saveDirectory.isDirectory() && saveDirectory.exists()){

		}
	}

	@Override
	public void onFailue(HttpTask task, Throwable ex) {
	}

	@Override
	public void onSucess(HttpTask task, HttpResponse response) throws Throwable{
		int status = response.getStatusLine().getStatusCode();
		switch (status){
		case HttpStatus.SC_OK:
			writeResponse(task, response);
			break;
		}
	}

	private void writeResponse(HttpTask task, HttpResponse response) throws Throwable{
		InputStream inputStream = null;
		BufferedInputStream binaryInputStream = null;
		BufferedOutputStream outputStream = null;
		int bufferSize = this.bufferSize;
		try {
			long length = response.getEntity().getContentLength();
			final long persent = length / 100;
			task.getProgress().setMax(100);
			byte[] buffer = new byte[bufferSize];
			File saveFile = new File(saveDirectory,"hoge");
			inputStream = response.getEntity().getContent();
			binaryInputStream = new BufferedInputStream(inputStream, bufferSize);
			outputStream = new BufferedOutputStream(new FileOutputStream(saveFile, false), bufferSize);
			int hold = 0;
			for (int size = 0; -1 < (size = binaryInputStream.read(buffer));) {
				outputStream.write(buffer);
				int persents = (int) (size / persent);;
				if(hold < persents){
					task.getProgress().setProgress(persents);
				}
			}
			outputStream.flush();
		} finally {
			Closeable[] closeables = new Closeable[] { outputStream, binaryInputStream, inputStream };
			for (Closeable close : closeables) {
				if (close != null) {
					try {
						close.close();
					} catch (IOException ex) {
						Logger.warm(ex);
					}
				}
			}
		}
	}

	@Override
	public void onEndl(HashMap<HttpRequestBase, Object> result) {
		if(action != null && activity != null){
			activity.runOnUiThread(action);
		}
	}
}
