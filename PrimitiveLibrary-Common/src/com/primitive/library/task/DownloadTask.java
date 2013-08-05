/**
 * DownloadTask
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.primitive.library.common.log.Logger;

/**
 * @author xxx
 */
public class DownloadTask extends AsyncTask<Void, Integer, String> {

	/**
	 * @author xxx
	 */
	public interface DownloadCallback {
		void onFailue();
		void onSucess();
	}

	private final DownloadCallback callback;
	private String hashValue;
	private ProgressDialog progress = null;
	private final String savePath;
	private final String fileName;
	private final Activity owner;
	private final HttpClient cli = new DefaultHttpClient();
	private final HttpRequestBase request;
	private long timeout = 10000;
	private int buffer_size = 1024 * 16;

	/**
	 *
	 * @param request
	 * @param savePath
	 * @param fileName
	 * @param owner
	 * @param callback
	 */
	public DownloadTask(final HttpRequestBase request, final String savePath,
			final String fileName, final Activity owner,
			final DownloadCallback callback) {
		this.request = request;
		this.savePath = savePath;
		this.fileName = fileName;
		this.owner = owner;
		this.callback = callback;
	}

	@Override
	protected String doInBackground(Void... params) {
		File file = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			cli.getParams().setParameter("http.connection.timeout",
					Long.valueOf(timeout));
			HttpResponse res = cli.execute(request);
			int rescode = res.getStatusLine().getStatusCode();

			if (rescode == HttpStatus.SC_OK) {
				int length = (int) res.getEntity().getContentLength();
				this.progress.setMax(length);
				byte[] buffer = new byte[buffer_size];
				file = new File(this.savePath);
				is = res.getEntity().getContent();
				bis = new BufferedInputStream(is, buffer_size);
				bos = new BufferedOutputStream(
						new FileOutputStream(file, false), buffer_size);
				for (int size = 0; -1 < (size = bis.read(buffer));) {
					bos.write(buffer);
					this.publishProgress(size);
				}
				bos.flush();
				return this.savePath;
			} else {
				switch (rescode) {
				case HttpStatus.SC_NOT_FOUND:
					break;
				case HttpStatus.SC_REQUEST_TIMEOUT:
					break;
				default:
					break;
				}
				return null;
			}

		} catch (Throwable ex) {
			Logger.err(ex);
			return null;
		} finally {
			Closeable[] closeables = new Closeable[] { bos, bis, is };
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
	protected void onPostExecute(final String filePath) {

	}

	@Override
	protected void onPreExecute() {
		this.progress = new ProgressDialog(this.owner);
		this.progress.setMessage("Downloading...");
		this.progress.setIndeterminate(false);
		this.progress.setCancelable(false);
		this.progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// this.mProgressDialog.setMax(1);
		this.progress.show();
	}

	@Override
	protected void onProgressUpdate(final Integer... progress) {
		try {
			this.progress
					.setProgress(this.progress.getProgress() + progress[0]);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
