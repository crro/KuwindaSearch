package mx.suh.crro.kuwinda.application;


import mx.suh.crro.kuwinda.datastorage.GroupLoader;
import mx.suh.crro.kuwinda.datastorage.WebsiteLoader;
import android.app.Application;

public class KuwindaApplication extends Application {
	/*
	 * this class will mantain the DB connection so that we dont have to instantiate a new TodoDbLoader in every activity
	 */
	private static WebsiteLoader _wbLoader;
	private static GroupLoader _groupLoader;

	public static WebsiteLoader getWebsiteLoader() {
		return _wbLoader;
	}
	
	public static GroupLoader getGroupLoader() {
		return _groupLoader;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		_wbLoader = new WebsiteLoader(this);
		_groupLoader =  new GroupLoader(this);
		_wbLoader.open();
		_groupLoader.open();
	}
	
	@Override public void onTerminate() { 
		// Close the internal db 
		super.onTerminate();
		_wbLoader.close();
		_groupLoader.close();
	}
}
