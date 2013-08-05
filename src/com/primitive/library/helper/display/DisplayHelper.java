package com.primitive.library.helper.display;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayHelper {
	public static Display getDisplay(final Context context){
		return ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	}
	/**
	 *
	 * @param context
	 * @return
	 */
	public static Point getDisplaySize(final Context context){
		Display display = getDisplay(context);
		Point point = new Point();
		display.getSize(point);
		return point;
	}

	public static Point getDisplayAspectRatio(Context context){
		Point displaySize = getDisplaySize(context);
		int gcd = getGCD(displaySize.x, displaySize.y);
		return new Point(displaySize.x/gcd,displaySize.y/gcd);
	}

	private static int getGCD(int x, int y) {
		if (y == 0) {
			return x;
		} else {
			return getGCD(y, x % y);
		}
	}

}
