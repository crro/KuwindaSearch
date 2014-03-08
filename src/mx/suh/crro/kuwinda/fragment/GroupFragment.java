package mx.suh.crro.kuwinda.fragment;

import mx.suh.crro.kuwinda.KuwindaConstants;
import mx.suh.crro.kuwinda.R;
import mx.suh.crro.kuwinda.adapter.GroupsAdapter;
import mx.suh.crro.kuwinda.application.KuwindaApplication;
import mx.suh.crro.kuwinda.datastorage.GroupLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.NetworkInfo.DetailedState;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GroupFragment extends Fragment {
	private GroupsAdapter _gAdapter;
	private GroupLoader _gLoader;
	private GetAllGroups _getAllGroups;
	private LocalBroadcastManager _lbm;
	private ListView _listView;
	
	private BroadcastReceiver _udpateCategories = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			System.out.println("We are updating the categories");
			refreshList();
			if (isVisible()) {
				_listView.invalidateViews();
			}
		}
		
	};
	private class GetAllGroups extends AsyncTask<Void, Void, Cursor> {
		private static final String TAG = "GetAllGroups";

		@Override
		protected Cursor doInBackground(Void... params) {
			try {
				Cursor result = _gLoader.fetchAll();
				if (!isCancelled()) {
					return result;
				} else {
					Log.d(TAG, "Cancelled, closing cursor");
					if (result != null) {
						result.close();
					}
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Cursor result) {
			super.onPostExecute(result);
			Log.d(TAG, "Fetch completed, displaying cursor results!");
			try {
				if (_gAdapter == null) {
					_gAdapter = new GroupsAdapter(getActivity()
							.getApplicationContext(), result);
					_listView.setAdapter(_gAdapter);
					_listView.invalidateViews();
				} else {
					_gAdapter.changeCursor(result);
				}
				_getAllGroups = null;
			} catch (Exception e) {
			}
		}

	}
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		_lbm = LocalBroadcastManager.getInstance(getActivity());
		//We make the broadcast manager listen to these local intents
		IntentFilter filter = new IntentFilter(KuwindaConstants.ACTION_GROUPS_CHANGED);
		_lbm.registerReceiver(_udpateCategories, filter);
		_gLoader = KuwindaApplication.getGroupLoader();
		//_gAdapter = new GroupsAdapter(getActivity(), _gLoader.fetchAll());
		
		setHasOptionsMenu(true);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.groups_layout, null);
		//Set up the the search.
		_listView = (ListView) v.findViewById(R.id.groupList);
		_listView.setAdapter(_gAdapter);
		
		
		/*TODO: I need to fetch the action bar and put a button there (+ sign)
		 * This will call the other function that will have the other layout 
		 * that creates categories*/
		
		
		return v;
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.main_group_menu, menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_add:
				FragmentManager fragMan = getFragmentManager();
				CreateGroupFragment gF = new CreateGroupFragment();
				fragMan.beginTransaction().replace(R.id.content_frame, gF).commit();
				return true;
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void onStart() {
		super.onStart();
		_listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		if (_getAllGroups != null) {
//			_getAllGroups.cancel(false);
//		}
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		_lbm.unregisterReceiver(_udpateCategories);
		// Close the cursor if there is any assigned to the adapter
		if (_gAdapter != null && _gAdapter.getCursor() != null) {
			_gAdapter.getCursor().close();
		}
	}

	public void refreshList() {
//		if (_getAllGroups != null) {
//			_getAllGroups.cancel(false);
//		}
//		_getAllGroups = new GetAllGroups();
//		_getAllGroups.execute();
	}
	
	/*
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick( l, v, position, id);
		TextView catName = (TextView) v.findViewById(R.id.groupName);
		
		String groupName = catName.getText().toString();
		
		Cursor cursorOfRow = _gLoader.getCursorOfGroup(id);
		
		//I need to start making the start of activity
		
		Intent searchInGroupIntent = new Intent(getActivity(), SearchInGroupActivity.class);
		searchInGroupIntent.putExtra(KuwindaConstants.ROWID_CAT_BUNDLE_KEY, id);
		searchInGroupIntent.putExtra(KuwindaConstants.Group_NAME_BUNDLE_KEY, groupName);
		startActivity(searchInGroupIntent);
	}*/
}
