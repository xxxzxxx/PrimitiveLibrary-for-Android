package com.primitive.library.application;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.primitive.library.common.log.Logger;

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
