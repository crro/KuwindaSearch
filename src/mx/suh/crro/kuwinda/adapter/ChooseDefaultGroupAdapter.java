package mx.suh.crro.kuwinda.adapter;

import mx.suh.crro.kuwinda.R;
import mx.suh.crro.kuwinda.data.group.Group;
import mx.suh.crro.kuwinda.datastorage.GroupLoader;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChooseDefaultGroupAdapter extends CursorAdapter{
	private LayoutInflater _inflater;
	
	public ChooseDefaultGroupAdapter(Context context, Cursor c) {
		super(context, c, false);
		// TODO Auto-generated constructor stub
		_inflater = LayoutInflater.from(context);
		
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView groupName = (TextView) view.findViewById(R.id.groupName);
		Group group = GroupLoader.getGroupByCursor(cursor);
		groupName.setText(group.getName());
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View row = _inflater.inflate(R.layout.default_group_row, null);
		bindView(row, context, cursor);
		return row;
	}

	/*
	@Override
	public void bindView(View view,Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		boolean expandedView = false;
		TextView GroupName = (TextView) view.findViewById(R.id.GroupName);

		Group cat = GroupLoader.getGroupByCursor(cursor);
		
//		Boolean expandableBool = _expandable.get(cat.getName());
//		if (expandableBool != null) {
//			boolean boolVal = expandableBool.booleanValue();
//			if (boolVal) {
//				searchBar.setVisibility(View.VISIBLE);
//				searchBtn.setVisibility(View.VISIBLE);
//				deleteBtn.setVisibility(View.VISIBLE);
//			} else {
//				searchBar.setVisibility(View.GONE);
//				searchBtn.setVisibility(View.GONE);
//				deleteBtn.setVisibility(View.GONE);
//			}
//		} else {
//			searchBar.setVisibility(View.GONE);
//			searchBtn.setVisibility(View.GONE);
//			deleteBtn.setVisibility(View.GONE);
//			_expandable.put(cat.getName(), Boolean.valueOf(false));
//		}
//		if (_expandable.get(Integer.valueOf(cursor.getInt(_rowIdIndex))).booleanValue()) {
//			searchBar.setVisibility(View.VISIBLE);
//			searchBtn.setVisibility(View.VISIBLE);
//			deleteBtn.setVisibility(View.VISIBLE);
//		} else {
//			searchBar.setVisibility(View.GONE);
//			searchBtn.setVisibility(View.GONE);
//			deleteBtn.setVisibility(View.GONE);
//		}
		
		
		
		GroupName.setTag(Boolean.valueOf(expandedView));
		GroupName.setText(cat.getName());
	}/*

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View row = inflater.inflate(android.R.layout.simple_spinner_item, null);
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
	/*
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
			GroupLoader catLoader = KuwindaApplication.getGroupLoader();
			String GroupName = _catName.getText().toString();
			//int rowId = _cursor.getInt(_cursor.getColumnIndex(DbConstants.Categories.KEY_ROWID));
			Integer rowId = (Integer) v.getTag();
			catLoader.deleteGroup(rowId.intValue());
			Intent catChanged = new Intent(KuwindaConstants.ACTION_CATEGORIES_CHANGED);
			Intent defChanged = new Intent(KuwindaConstants.UPDATE_DEFAULT_WEBSITE);
			LocalBroadcastManager.getInstance(_ctx).sendBroadcast(catChanged);
			LocalBroadcastManager.getInstance(_ctx).sendBroadcast(defChanged);
			//_expandable.put(GroupName, Boolean.valueOf(false));
			
			 
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
			_searchQuery = _searchBar.getText().toString().trim();
			if (_searchQuery.equals("")) { 
				Toast.makeText(_ctx, "Please enter a search query", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Intent resultsIntent = new Intent();
			resultsIntent.setClass(_ctx, ResultsActivity.class);
			 
			Group group = GroupLoader.getGroupByCursor(_cursor);
			
			System.out.println(Group.getName());
			
			resultsIntent.putExtra(KuwindaConstants.SEARCH_QUERY_BUNDLE_KEY, _searchQuery);
			resultsIntent.putExtra(KuwindaConstants.WEBSITES_BUNDLE_KEY, Group.getWebsitesStringArray());
			_ctx.startActivity(resultsIntent);
		}
	}*/
}
