package mx.suh.crro.kuwinda.adapter;

import mx.suh.crro.kuwinda.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainMenuListAdapter extends BaseAdapter {
	private String[] _menu;
	private Context _ctx;
	
	public MainMenuListAdapter(String[] menuItems, Context ctx) {
		_menu = menuItems;
		_ctx = ctx;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _menu.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _menu[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public static class ViewHolder { 
		TextView _menuTitle;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) _ctx.getSystemService(_ctx.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.menu_item, null);
			
			holder = new ViewHolder();
			holder._menuTitle = (TextView) convertView.findViewById(R.id.tvMenuItemTitle);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//The holder method stores the reference to the view elements to avoid unnecessary computation.
		holder._menuTitle.setText(_menu[position]);
		return convertView;
	}

}
