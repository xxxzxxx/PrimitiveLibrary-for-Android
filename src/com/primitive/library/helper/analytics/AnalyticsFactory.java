/**
 * AnalyticsFactory
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.helper.analytics;

/**
 *
 * @author xxxzxxx
 *
 */
public class AnalyticsFactory {
	enum AnalyticsType {
		GoogleAnalytics
	};

	public static AnalyticsHelper getHelper(final AnalyticsType analyticsType) {
		AnalyticsHelper helper = null;
		switch (analyticsType) {
		case GoogleAnalytics:
			helper = new GAHelper();
			break;
		default:
			//TODO: UnSupportedException
			break;
		}
		return helper;
	}
}
