package com.primitive.library.fragment;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockListFragment;
import com.primitive.library.listener.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public abstract class TaskingListFragment <TASK extends AsyncTask<Void, Void, ArrayList<?>>, ADPT extends ArrayAdapter<?>, LIST extends ArrayList<?>> extends SherlockListFragment implements OnScrollListener, OnItemClickListener, OnRefreshListener{
	protected LIST items = null;
	protected ADPT adapter = null;
	protected TASK task = null;
	protected abstract ADPT generateArrayAdapter();
	protected abstract TASK generateGetArrayTask();
	protected abstract LIST generateArrayList();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.items = generateArrayList();
		this.adapter = generateArrayAdapter();
		this.task = generateGetArrayTask();
		setListAdapter(this.adapter);
		if (this.task != null)
		{
			this.task.execute();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getListView().setOnScrollListener(this);
		getListView().setOnItemClickListener(this);
	}
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}
	private int executedItemCount = 0;
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		if (Status.FINISHED == this.task.getStatus())
		{
			if (totalItemCount == firstVisibleItem + visibleItemCount && totalItemCount != executedItemCount)
			{
				executedItemCount = totalItemCount;
				additionalReading();
			}
		}
	}
	protected void additionalReading()
	{
		this.task = generateGetArrayTask();
		this.task.execute();
	}
	protected void showToast(int id, int duration)
	{
		Toast.makeText(this.getActivity(), getString(id), duration).show();
	}
}
