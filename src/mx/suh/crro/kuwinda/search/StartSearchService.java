package mx.suh.crro.kuwinda.search;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StartSearchService extends Service {
	NotificationManager _nManager;
	public void onCreate() {
		super.onCreate();
		//we need to start setting things up here, although everything will be received on the
		//onStartCommand.
	}
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// Need to start a separate thread to do the intensive work
		// or instead use the intentService which looks more suited. Read on starting a notification, 
		//good thing we have the activity results
		return super.onStartCommand(intent, flags, startId);
	}
}
