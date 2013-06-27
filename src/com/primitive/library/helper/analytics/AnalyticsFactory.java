package com.primitive.library.helper.analytics;

public class AnalyticsFactory {
	enum AnalyticsType{
		GoogleAnalytics
	};
	static GAHelper gahelper = null;

	public static AnalyticsHelper createHelper(final AnalyticsType analyticsType){
		switch (analyticsType){
		case GoogleAnalytics:
			gahelper = gahelper != null ? gahelper : new GAHelper();
			break;
		}
		return null;
	}
}
