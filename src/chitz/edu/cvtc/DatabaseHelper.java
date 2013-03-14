package chitz.edu.cvtc;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

/**
 * 
 * Contains methods common to any database. Uses SQLiteOpenHelper to allow
 * 
 * database access.
 * 
 * 
 * 
 * @author Christina Hitz
 * 
 * 
 */

public class DatabaseHelper extends SQLiteOpenHelper {

	private String creationStr;

	private String table;

	private String tag; // for logging.

	/**
	 * 
	 * The Constructor passes the database name, etc. on to SQLiteOpenHelper to
	 * 
	 * connect to database.
	 * 
	 * 
	 * 
	 * @param context
	 * 
	 * @param databaseName
	 * 
	 * @param databaseVersion
	 * 
	 * @param creationStr
	 * 
	 *            Stored here to allow creation(onCreate) of the database.
	 * 
	 * @param tag
	 * 
	 *            The tag for logging
	 */

	DatabaseHelper(Context context, String databaseName, int databaseVersion,

	String creationStr, String tag) {

		super(context, databaseName, null, databaseVersion);
		this.tag = tag;
		this.creationStr = creationStr;

	}

	/**
	 * 
	 * Called when the database is created for the first time
	 */

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(creationStr); // creation string was stored in the
									// constructor

	}

	/**
	 * 
	 * Called when the database needs to be updated.
	 * 
	 * For an example, see the NotePadProvider class in the NotePad sample
	 * application
	 * 
	 * in the samples/ directory of the SDK.
	 */

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(tag, "Upgrading database from version " + oldVersion

		+ " to " + newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + table);

		onCreate(db); // calls onCreate to create the new database.

	}

}
