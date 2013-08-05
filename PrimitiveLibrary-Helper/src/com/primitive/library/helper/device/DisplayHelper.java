package com.primitive.library.helper.device;

import java.util.ArrayList;
import java.util.List;

import com.primitive.library.math.MathEx;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera.Size;
import android.view.Display;
import android.view.View;
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

	/**
	 *
	 * @param context
	 * @return
	 */
	public static Point getDisplayAspectRatio(Context context){
		Point displaySize = getDisplaySize(context);
		int gcd = MathEx.GCD(displaySize.x, displaySize.y);
		return new Point(displaySize.x/gcd,displaySize.y/gcd);
	}

	/**
	 *
	 * @param sizes
	 * @param width
	 * @param height
	 * @return
	 */
	public static List<Size> getOptimalPreviewSizes(List<Size> sizes, int width, int height) {
		final int ratio = MathEx.GCD(width, height);
		final int ratio_w = width / ratio;
		final int ratio_h = height / ratio;
		if (sizes == null) {
			return null;
		}
		final List<Size> optimalSizes = new ArrayList<Size>();
		if (optimalSizes.size() == 0) {
			for (final Size size : sizes) {
				final int ratiom = MathEx.GCD(size.width, size.height);
				final int ratiom_w = size.width / ratiom;
				final int ratiom_h = size.height / ratiom;
				if ((ratio_w == ratiom_w && ratio_h == ratiom_h)
						|| (ratio_w == ratiom_h && ratio_h == ratiom_w)) {
					optimalSizes.add(size);
				}
			}
		}
		return optimalSizes;
	}

	/**
	 *
	 * @param sizes
	 * @param width
	 * @param height
	 * @return
	 */
	public static Size getOptimalPreviewSize(final List<Size> sizes, final int width, final int height) {
		final double ASPECT_TOLERANCE = 0.1;
		final double targetRatio = (double) width / height;
		if (sizes == null) {
			return null;
		}

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		final int targetHeight = height;
		for (final Size size : sizes) {
			final double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
				continue;
			}
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (final Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	/**
	 *
	 * @param context
	 * @return
	 */
	public static boolean isPortrait(final Context context) {
		return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
	}
	/**
	 *
	 * @param context
	 * @return
	 */
	public static boolean isLandscape(final Context context) {
		return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
	}
}
