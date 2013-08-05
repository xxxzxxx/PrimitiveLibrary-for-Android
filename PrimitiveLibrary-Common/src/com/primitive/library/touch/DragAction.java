package com.primitive.library.touch;

import android.view.MotionEvent;
import android.view.View;

/**
 *
 * @author xxx
 *
 */
public abstract class DragAction {
	protected float mLastMotionX = 0f;
	protected boolean mOpening = false;
	protected boolean mDraggable = false;
	protected View mTarget = null;
	public DragAction(View v){
		this.mTarget = v;
	}
	abstract public boolean onTouchEvent(MotionEvent event);
}
