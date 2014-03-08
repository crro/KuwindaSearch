package mx.suh.crro.kuwinda.adapter;

import java.util.HashMap;

import mx.suh.crro.kuwinda.KuwindaConstants;
import mx.suh.crro.kuwinda.R;
import mx.suh.crro.kuwinda.application.KuwindaApplication;
import mx.suh.crro.kuwinda.data.group.Group;
import mx.suh.crro.kuwinda.datastorage.DbConstants;
import mx.suh.crro.kuwinda.datastorage.GroupLoader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GroupsAdapter extends CursorAdapter {

	//TODO: We need to implement the holder pattern here
		private int _rowIdIndex;
		private HashMap<String, Boolean> _expandable;
		private Context _ctx;
		private GroupLoader _gLoader;
		
		public GroupsAdapter(Context context, Cursor c) {
			super(context, c, false);
			
			System.out.println("We create a new adapter");
			_ctx = context;
			_rowIdIndex = c.getColumnIndex(DbConstants.Groups.KEY_ROWID);
			_expandable = new HashMap<String, Boolean>();
			_gLoader = KuwindaApplication.getGroupLoader();
			if (c.moveToFirst()) {
				do {
					String catName = GroupLoader.getNameByCursor(c);
					_expandable.put(catName, Boolean.valueOf(false));
				} while (c.moveToNext());
			}
		}

		@Override
		public void bindView(View view,Context context, Cursor cursor) {
			boolean expandedView = false;
			TextView gName = (TextView) view.findViewById(R.id.groupName);
			Group group = GroupLoader.getGroupByCursor(cursor);
			//here we would change the color of the group.
			
//			Boolean expandableBool = _expandable.get(cat.getName());
//			if (expandableBool != null) {
//				boolean boolVal = expandableBool.booleanValue();
//				if (boolVal) {
//					searchBar.setVisibility(View.VISIBLE);
//					searchBtn.setVisibility(View.VISIBLE);
//					deleteBtn.setVisibility(View.VISIBLE);
//				} else {
//					searchBar.setVisibility(View.GONE);
//					searchBtn.setVisibility(View.GONE);
//					deleteBtn.setVisibility(View.GONE);
//				}
//			} else {
//				searchBar.setVisibility(View.GONE);
//				searchBtn.setVisibility(View.GONE);
//				deleteBtn.setVisibility(View.GONE);
//				_expandable.put(cat.getName(), Boolean.valueOf(false));
//			}
//			if (_expandable.get(Integer.valueOf(cursor.getInt(_rowIdIndex))).booleanValue()) {
//				searchBar.setVisibility(View.VISIBLE);
//				searchBtn.setVisibility(View.VISIBLE);
//				deleteBtn.setVisibility(View.VISIBLE);
//			} else {
//				searchBar.setVisibility(View.GONE);
//				searchBtn.setVisibility(View.GONE);
//				deleteBtn.setVisibility(View.GONE);
//			}
			
			
			
			gName.setTag(Boolean.valueOf(expandedView));
			gName.setText(group.getName());
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View row = inflater.inflate(R.layout.group_row, null);
			bindView(row, context, cursor);
			return row;
		}
		
		

		public boolean areEqual(Group c1, Group c2) {
			boolean val1 = c1.getKeyword().equals(c2.getKeyword());
			boolean val2 =  c1.getName().equals(c2.getName());
			if (val1 && val2) {
				return true;
			} else {
				return false;
			}
		}
		
		private class DeleteOnClickListener implements OnClickListener {
			private Cursor _cursor;
			private Context _ctx;
			private Button _sButon;
			private EditText _sBar;
			private TextView _catName;
			
			public DeleteOnClickListener(Cursor c, Context cx, EditText sBar, Button sButon, TextView catName) {
				_cursor = c;
				_ctx = cx;
				_sBar = sBar;
				_sButon = sButon;
				_catName = catName;
				
			}
			
			public void onClick(View v) {
				GroupLoader gLoader = KuwindaApplication.getGroupLoader();
				String categoryName = _catName.getText().toString();
				//int rowId = _cursor.getInt(_cursor.getColumnIndex(DbConstants.Groups.KEY_ROWID));
				Integer rowId = (Integer) v.getTag();
				gLoader.deleteGroup(rowId.intValue());
				Intent catChanged = new Intent(KuwindaConstants.ACTION_GROUPS_CHANGED);
				Intent defChanged = new Intent(KuwindaConstants.UPDATE_DEFAULT_WEBSITE);
				LocalBroadcastManager.getInstance(_ctx).sendBroadcast(catChanged);
				LocalBroadcastManager.getInstance(_ctx).sendBroadcast(defChanged);
				//_expandable.put(categoryName, Boolean.valueOf(false));
				
				 
				v.setVisibility(View.GONE);
				_sBar.setVisibility(View.GONE);
				_sButon.setVisibility(View.GONE);
				
				notifyDataSetChanged();
			}
			
		}
		
		private class SearchOnClickListener implements OnClickListener {
			private Context _ctx;
			private Cursor _cursor;
			private EditText _searchBar;
			private String _searchQuery;
			
			public SearchOnClickListener(Context context, Cursor c, EditText sBar) {
				_ctx = context;
				_cursor = c;
				_searchBar = sBar;
			}
			
			public void onClick(View v) {
				/*_searchQuery = _searchBar.getText().toString().trim();
				if (_searchQuery.equals("")) { 
					Toast.makeText(_ctx, "Please enter a search query", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Intent resultsIntent = new Intent();
				resultsIntent.setClass(_ctx, ResultsActivity.class);
				 
				Group group = GroupLoader.getGroupByCursor(_cursor);
				
				System.out.println(group.getName());
				
				resultsIntent.putExtra(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY, _searchQuery);
				resultsIntent.putExtra(KuwindaConstants.WEBSITES_BUNDLE_KEY, group.getWebsitesStringArray());
				_ctx.startActivity(resultsIntent);*/
			}
		}

}
