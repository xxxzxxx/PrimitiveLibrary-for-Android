package com.primitive.library.fragment;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.primitive.library.listener.OnRefreshListener;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public abstract class TaskingFragment
<TASK extends AsyncTask<Void, Void, ArrayList<?>>
, ADPT extends ArrayAdapter<?>
, LIST extends ArrayList<?>
, VIEW extends View>
extends SherlockFragment
implements OnScrollListener, OnItemClickListener, OnRefreshListener{
	private final Handler mHandler = new Handler();
	protected LIST items = null;
	protected ADPT adapter = null;
	protected TASK task = null;
	private View componentView = null;

	protected abstract ADPT generateArrayAdapter();
	protected abstract TASK generateGetArrayTask();
	protected abstract LIST generateArrayList();
	protected abstract VIEW generateView();
	protected abstract Class componentViewClass();
	protected abstract boolean instanceOfComponentView(View view);

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Context context = getActivity();
		FrameLayout root = new FrameLayout(context);
		LinearLayout pframe = new LinearLayout(context);
		pframe.setId(16711682);
		pframe.setOrientation(1);
		pframe.setVisibility(8);
		pframe.setGravity(17);
		ProgressBar progress = new ProgressBar(context, null, 16842874);
		pframe.addView(progress, new FrameLayout.LayoutParams(-2, -2));
		root.addView(pframe, new FrameLayout.LayoutParams(-1, -1));
		FrameLayout lframe = new FrameLayout(context);
		lframe.setId(16711683);

		TextView tv = new TextView(getActivity());
		tv.setId(16711681);
		tv.setGravity(17);
		lframe.addView(tv, new FrameLayout.LayoutParams(-1, -1));
		ListView lv = new ListView(getActivity());
		lv.setId(16908298);
		lv.setDrawSelectorOnTop(false);
		lframe.addView(lv, new FrameLayout.LayoutParams(-1, -1));
		root.addView(lframe, new FrameLayout.LayoutParams(-1, -1));
		root.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
		return root;
	}

	public void onDestroyView()
	{
		this.mHandler.removeCallbacks(this.mRequestFocus);
		this.componentView = null;
		this.mListShown = false;
		this.mEmptyView = (this.mProgressContainer = this.mListContainer = null);
		this.mStandardEmptyView = null;
		super.onDestroyView();
	}

	private void ensureList() {
		if (this.componentView != null) {
			return;
		}
		View root = getView();
		if (root == null) {
			throw new IllegalStateException("Content view not yet created");
		}
		if (instanceOfComponentView(root)) {
			this.componentView = root;
		} else {
			this.mStandardEmptyView = ((TextView)root.findViewById(16711681));
			if (this.mStandardEmptyView == null)
				this.mEmptyView = root.findViewById(16908292);
			else {
				this.mStandardEmptyView.setVisibility(8);
			}
			this.mProgressContainer = root.findViewById(16711682);
			this.mListContainer = root.findViewById(16711683);
			View rawListView = root.findViewById(16908298);
			if (!(rawListView instanceof ListView)) {
				if (rawListView == null) {
					throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");

				}

				throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");

			}

			this.mList = ((ListView)rawListView);
			if (this.mEmptyView != null) {
				this.mList.setEmptyView(this.mEmptyView);
			} else if (this.mEmptyText != null) {
				this.mStandardEmptyView.setText(this.mEmptyText);
				this.mList.setEmptyView(this.mStandardEmptyView);
			}
		}
		this.mListShown = true;
		this.mList.setOnItemClickListener(this.mOnClickListener);
		if (this.mAdapter != null) {
			ListAdapter adapter = this.mAdapter;
			this.mAdapter = null;
			setListAdapter(adapter);


		}
		else if (this.mProgressContainer != null) {
			setListShown(false, false);
		}

		this.mHandler.post(this.mRequestFocus);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
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
