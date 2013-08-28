/**
 * AbstractArrayAdapter
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.task.http;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * @author xxx
 */
public final class HttpTask extends AsyncTask<HttpRequestBase, Integer, HashMap<HttpRequestBase,Object>>{
	/**
	 * @author xxx
	 */
	public interface Adapter {
		void onFailue(final HttpTask task,final Throwable ex);
		void onSucess(final HttpTask task,final HttpResponse response) throws Throwable;
		void onEndl(final HashMap<HttpRequestBase,Object> result);
	}
	private final Adapter adapter;
	private HttpClient cli = null;
	private long timeout = 10000;
	public ProgressDialog getProgress() {
		return progress;
	}

	public void setProgress(ProgressDialog progress) {
		this.progress = progress;
	}

	private ProgressDialog progress = null;
	/**
	 *
	 */
	private void setup(){
		if(this.cli == null){
			cli = new DefaultHttpClient();
		}
		cli.getParams().setParameter("http.connection.timeout",Long.valueOf(timeout));
	}

	/**
	 *
	 * @param adapter
	 */
	public HttpTask(final Adapter adapter) {
		this.adapter = adapter;
		setup();
	}

	/**
	 *
	 * @param adapter
	 */
	public HttpTask(final Adapter adapter,HttpClient cli) {
		this.adapter = adapter;
		this.cli = cli;
		setup();
	}

	/**
	 *
	 * @param adapter
	 */
	public HttpTask(final Adapter adapter,HttpClient cli,final long timeout) {
		this.adapter = adapter;
		this.cli = cli;
		this.timeout = timeout;
		setup();
	}

	@Override
	protected void onPreExecute() {
		if(this.progress != null){
			this.progress.show();
		}
	}
	@Override
	protected void onProgressUpdate(final Integer... progress){
		if(this.progress != null){
			this.progress.setProgress(this.progress.getProgress() + progress[0]);
		}
	}
	@Override
	protected HashMap<HttpRequestBase,Object> doInBackground(HttpRequestBase... params) {
		HashMap<HttpRequestBase,Object> result = new HashMap<HttpRequestBase, Object>();
		for(HttpRequestBase request : params){
			try{
				HttpResponse response = this.cli.execute(request);
				adapter.onSucess(this,response);
				result.put(request, response);
			} catch (Throwable ex) {
				adapter.onFailue(this,ex);
				result.put(request, ex);
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(final HashMap<HttpRequestBase,Object> result) {
		if(progress != null){
			progress.hide();
		}
		adapter.onEndl(result);
	}
}