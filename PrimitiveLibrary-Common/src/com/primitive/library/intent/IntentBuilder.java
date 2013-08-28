package com.primitive.library.intent;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class IntentBuilder {
	public static Intent createViewIntent(final Uri uri){
		return new Intent(Intent.ACTION_VIEW, uri);
	}
	public static final String ImageType = "image/*";
	public static Intent createImagePicupIntent(final String extension){
		return createPicupIntent(ImageType+(extension != null ? "." + extension : ""));
	}
	public static final String AudioType = "Audio/*";
	public static Intent createAudioPicupIntent(final String extension){
		return createPicupIntent(AudioType+(extension != null ? "." + extension : ""));
	}
	public static final String VideoType = "video/*";
	public static Intent createVideoPicupIntent(final String extension){
		return createPicupIntent(VideoType+(extension != null ? "." + extension : ""));
	}
	public static final String TextType = "text/*";
	public static Intent createTextPicupIntent(final String extension){
		return createPicupIntent(TextType+(extension != null ? "." + extension : ""));
	}
	public static Intent createPicupIntent(final String type){
		return createIntent(type,Intent.ACTION_PICK,null);
	}
	public static Intent createEditIntent(String type){
		return createIntent(type,Intent.ACTION_EDIT,null);
	}
	public static boolean checkImplicitIntent(final Context context, final Intent intent){
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> resolves= packageManager.queryIntentActivities(intent, 0);
		return resolves.size() > 0 ?true:false;
	}
	public static Intent createIntent(
			final String type,
			final String action,
			final Uri data
			){
		final Intent intent = new Intent();
		intent.setType(type);
		intent.setAction(action);
		if(data != null){
			intent.setData(data);
		}
		return intent;
	}
}
