package chitz.edu.cvtc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * A front end for the database.
 * 
 * Contains methods to update, delete, and insert rows.
 * 
 * @author Christina Hitz
 * 
 * 
 */
public class InterfaceDB {
	public static final String KEY_ROWID = "_id"; // field name for id
	public static final String NAME = "name"; // field name for name of website
	public static final String URL = "url"; // field for the url of the website
	public static final String TAG = "WebsiteTag"; // tag used for the log message
	private static final String DATABASE_NAME = "Websites"; // database name
	private static final String DATABASE_TABLE = "entries"; // table name
	private static final int DATABASE_VERSION = 1; // version number
	public static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + "(_id integer primary key autoincrement, "
			+ NAME + " text not null," + URL + " text not null);"; // create
	private Context context; // passed on to the helper class..so it is used.
	private DatabaseHelper DBHelper; // reference variable for the
										// DatabaseHelper..
	private SQLiteDatabase db; // reference variable for the database itself

	/**
	 * 
	 * The context in which this is running(e.g. Activity)
	 * 
	 * @param context
	 */
	public InterfaceDB(Context context) {
		this.context = context;
		DBHelper = new DatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION,
				DATABASE_CREATE, TAG);
	}

	/**
	 * 
	 * Open the database
	 */

	public InterfaceDB open() throws SQLException {
		db = DBHelper.getWritableDatabase();

		return this;
	}

	/**
	 * 
	 * Close the database
	 */
	public void close() {
		DBHelper.close();
	}

	/**
	 * 
	 * Insert a event Insert a name
	 * 
	 * @return
	 */

	public long insertName(String name, String url) {
		ContentValues insertValues = new ContentValues();
		insertValues.put(NAME, name);
		insertValues.put(URL, url);
		return db.insert(DATABASE_TABLE, null, insertValues);
	}

	/**
	 * 
	 * Delete by rowID
	 * 
	 * @param rowId
	 * 
	 * @return
	 */
	public boolean deleteRow(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * 
	 * Get all of the entries
	 * 
	 * Note: A Cursor captures results from a query on a SQLiteDatabase.
	 * 
	 * @return
	 */

	public Cursor getAllEntries() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, NAME, URL },
				null, null, null, null, null);
	}

	public Cursor getByName() {
		return db.query(DATABASE_TABLE, new String[] { NAME }, null, null,
				null, null, null);

	}

}
