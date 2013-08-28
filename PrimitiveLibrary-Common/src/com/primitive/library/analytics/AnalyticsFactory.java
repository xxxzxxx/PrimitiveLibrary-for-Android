/**
 * AnalyticsFactory
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.library.analytics;

/**
 *
 * @author xxxzxxx
 *
 */
public class AnalyticsFactory {
	public enum AnalyticsType {
		GoogleAnalytics
	};

	public static Analytics getHelper(final AnalyticsType analyticsType) {
		Analytics helper = null;
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
