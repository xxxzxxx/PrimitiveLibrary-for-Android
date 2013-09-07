/**
 * BaseActivity
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.primitive.library.R;

/**
 *
 * @author xxxzxxx
 *
 */
public abstract class BaseActivity extends SherlockActivity {
	protected abstract String getActivityName();
	protected boolean isTablet(){
		return getResources().getBoolean(R.bool.tablet);
	}
}
