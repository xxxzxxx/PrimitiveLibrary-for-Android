package com.primitive.library.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PeriodicService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
