package mx.suh.crro.kuwinda;

import mx.suh.crro.kuwinda.adapter.MainMenuListAdapter;
import mx.suh.crro.kuwinda.fragment.GroupFragment;
import mx.suh.crro.kuwinda.fragment.SearchFragment;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
/*This is the class where the main search is performed.*/
public class MainSearchActivity extends FragmentActivity {
	private DrawerLayout _mDrawerLayout;
	private ListView _mDrawerList;
	private ActionBarDrawerToggle _mDrawerToggle;
	
	private CharSequence _mDrawerTitle;
	private CharSequence _mTitle;
	private String[] _mMenuTitles;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_search);
		
		//We first set the title
		_mTitle = _mDrawerTitle = getTitle();
		_mMenuTitles = getResources().getStringArray(R.array.menu_array);
		_mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		_mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		_mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		_mDrawerList.setAdapter(new MainMenuListAdapter(_mMenuTitles, this));
		
		_mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		//The action bar toggle ties together the proper interactions 
		//between the sliding drawer and the action bar app icon
		
		_mDrawerToggle = new ActionBarDrawerToggle(
					this, //host activity
					_mDrawerLayout, //DrawerLayout obj
					R.drawable.ic_drawer, //nav drawer image
					R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(_mTitle);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(_mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		_mDrawerLayout.setDrawerListener(_mDrawerToggle);
		
		if (savedInstanceState == null) {
			selectItem(0);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_search, menu);
		return true;
	}
	
	public boolean onPrepareOptionsMenu(Menu menu) {
		//If the nav drawer is open, hide action items related 
		boolean drawerOpen = _mDrawerLayout.isDrawerOpen(_mDrawerList);
		//we hide the other icons when the nav drawer is open.
		return super.onPrepareOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (_mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       } 
       //Here we handle action buttons when we insert them
       return super.onOptionsItemSelected(item);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}
	public void setTitle(CharSequence title) {
		_mTitle = title;
		getActionBar().setTitle(_mTitle);
		
	}
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        _mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        _mDrawerToggle.onConfigurationChanged(newConfig);
    }
	private void selectItem(int position) {
		Fragment fragment = null;
		switch(position) {
			case 0:
				fragment = new SearchFragment();
				break;
			case 1:
				fragment =new GroupFragment(); 
				break;
			case 2:
				//fragment = 
				break;	
			case 3:
				//fragment = 
				break;
			case 4:
				//fragment = 
				break;
			case 5:
				//fragment = 
				break;
			case 6:
				//fragment =
				break;
			default:
				_mDrawerLayout.closeDrawer(_mDrawerList);
				break;
		}
		//Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(SearchFragment.ARG_ITEM_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        _mDrawerList.setItemChecked(position, true);
        setTitle(_mMenuTitles[position]);
        _mDrawerLayout.closeDrawer(_mDrawerList);
	}
	
}


