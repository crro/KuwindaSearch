package mx.suh.crro.kuwinda.datastorage;


import mx.suh.crro.kuwinda.data.group.Group;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GroupLoader {
	private Context _ctx;
	private DatabaseHelper _dbHelper;
	private SQLiteDatabase _mDb;
	
	public GroupLoader(Context ctx) {
		_ctx = ctx;
	}

	public void open() throws SQLException{ 
		//DatabaseHelper object 
		_dbHelper = new DatabaseHelper(_ctx, DbConstants.DATABASE_NAME); 
		// database object 
		_mDb = _dbHelper.getWritableDatabase(); //this calls the on create method
		
		// create schema if not exist yet 
		//_dbHelper.onCreate(_mDb); 
	}
	
	public void close(){ 
		_dbHelper.close(); 
	}

	// INSERT
	public long createGroup(Group group) {
		//ContentValues is used t wrap multiple data inside itself. and then put it in the database
		ContentValues values = new ContentValues();
		values.put(DbConstants.Groups.KEY_NAME, group.getName()); //column name that holds the title
		values.put(DbConstants.Groups.KEY_WEBSITES, group.getWebsitesString());
		values.put(DbConstants.Groups.KEY_KEYWORD, group.getKeyword());
		return _mDb.insert(DbConstants.Groups.DATABASE_TABLE, null, values);
	}

	//DELETE
	public boolean deleteGroup(long rowId) {
		//this eliminates a specific todo
		return _mDb.delete(DbConstants.Groups.DATABASE_TABLE, DbConstants.Groups.KEY_ROWID + "=" + rowId, null) > 0;
	}

	//DELETE ALL TODOs
	public boolean deleteAllGroups() {
		//this eliminates the whole database
		return _mDb.delete(DbConstants.Groups.DATABASE_TABLE, null, null) > 0;
	}

	//UPDATE
	public boolean updateGroup(long rowId, Group newCategory) {
		ContentValues values = new ContentValues();
		values.put(DbConstants.Groups.KEY_NAME, newCategory.getName()); //column name that holds the title
		values.put(DbConstants.Groups.KEY_WEBSITES, newCategory.getWebsitesString());
		values.put(DbConstants.Groups.KEY_KEYWORD, newCategory.getKeyword());
		return _mDb.update(DbConstants.Groups.DATABASE_TABLE, values,
				DbConstants.Groups.KEY_ROWID + "=" + rowId, null) > 0;
	}

	//read all Todos
	public Cursor fetchAll() {
		return _mDb.query( DbConstants.Groups.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Groups.KEY_ROWID, 
				DbConstants.Groups.KEY_NAME, 
				DbConstants.Groups.KEY_WEBSITES, 
				DbConstants.Groups.KEY_KEYWORD }, 
				null, null, null, null, 
				DbConstants.Groups.KEY_NAME);
	}

	//When we inject the database into the listView, the system gives the appropriate IDs to each list element
	//so when the user selects an element we have the appropriate id
	//When we create the list view, it will ask for the Key that identifies the column with the id
	public Group fetchGroup(long rowId){
		Cursor c = _mDb.query( DbConstants.Groups.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Groups.KEY_ROWID, 
				DbConstants.Groups.KEY_NAME, 
				DbConstants.Groups.KEY_WEBSITES, 
				DbConstants.Groups.KEY_KEYWORD 
		}, DbConstants.Groups.KEY_ROWID + "=" + rowId, 
		null, null, null, DbConstants.Groups.KEY_NAME);
		// if the Category is found with a given ID
		if(c.moveToFirst()) {
			return getGroupByCursor(c);
		}
		//otherwise return null
		return null;
	}
	
	public Cursor getCursorOfGroup(long rowId) {
		Cursor c = _mDb.query( DbConstants.Groups.DATABASE_TABLE, 
				new String[]{ 
				DbConstants.Groups.KEY_ROWID, 
				DbConstants.Groups.KEY_NAME, 
				DbConstants.Groups.KEY_WEBSITES, 
				DbConstants.Groups.KEY_KEYWORD 
		}, DbConstants.Groups.KEY_ROWID + "=" + rowId, 
		null, null, null, DbConstants.Groups.KEY_NAME);
		// if the Category is found with a given ID
		if(c.moveToFirst()) {
			return c;
		}
		//otherwise return null
		return null;
	}
	
	public static String getNameByCursor(Cursor c) {
		return c.getString(c.getColumnIndex(DbConstants.Groups.KEY_NAME));
	}

	public static Group getGroupByCursor(Cursor c) {
		String webString = c.getString(c.getColumnIndex(DbConstants.Groups.KEY_WEBSITES));
		String[] webSArray = webString.split(",");
		return new Group(c.getString(c.getColumnIndex(DbConstants.Groups.KEY_NAME)),
				webSArray,
				c.getString(c.getColumnIndex(DbConstants.Groups.KEY_KEYWORD)));
	}
}
