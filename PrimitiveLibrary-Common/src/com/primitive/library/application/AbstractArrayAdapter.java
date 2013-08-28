/**
 * AbstractArrayAdapter
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.application;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.primitive.library.common.log.Logger;

/**
 * @author xxx
 *
 * @param <T>
 */
public abstract class AbstractArrayAdapter<T> extends ArrayAdapter<T> {
	public AbstractArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	public AbstractArrayAdapter(Context context, int resource,int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}
	public AbstractArrayAdapter(Context context, int resource,int textViewResourceId, List<T> objects) {
		super(context, resource, textViewResourceId, objects);
	}
	public AbstractArrayAdapter(Context context, int resource,int textViewResourceId, T[] objects) {
		super(context, resource, textViewResourceId, objects);
	}
	public AbstractArrayAdapter(Context context, int textViewResourceId,List<T> objects) {
		super(context, textViewResourceId, objects);
	}
	public AbstractArrayAdapter(Context context, int textViewResourceId,T[] objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		long start = Logger.start();
		T value = getItem(position);
		final View view = super.getView(position,convertView,parent);
		view.setTag(createViewExtraObject(position,value,view));
		Logger.end(start);
		return view;
	}

	/**
	 *
	 * @param position
	 * @param values
	 * @param resultView
	 */
	public abstract Object createViewExtraObject(final int position,final T value,View view);
}
