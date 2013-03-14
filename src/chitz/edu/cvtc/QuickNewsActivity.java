package chitz.edu.cvtc;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * This application allows the user to save websites they have selected. These
 * sites are saved as buttons that can open the website in a webView inside of
 * this application.
 * 
 * @author Christina Hitz
 * 
 */
public class QuickNewsActivity extends Activity {

	private InterfaceDB db; // declare a reference to the database
	private static final int REQUEST_CODE = 10; // not sure if I need this
	

	/**
	 * Called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		db = new InterfaceDB(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horizontal);

		// open database
		db.open();

		// use FlowLayout
		FlowLayout layout = (FlowLayout) findViewById(R.id.flow_layout);
		
		
		// get list of website names from database
		Cursor c = db.getAllEntries();
		startManagingCursor(c);
		// populate a new array list
		ArrayList<DBRowBean> mArrayList = new ArrayList<DBRowBean>();
		c.moveToFirst();
		String id;
		String name;
		String url;

		while (!c.isAfterLast()) {
			// build row bean
			id = c.getString(0);
			name = c.getString(1);
			url = c.getString(2);
			DBRowBean dbrow = new DBRowBean(id, name, url);
			// add to arraylist
			mArrayList.add(dbrow);
			c.moveToNext();
		}

		// dynamically create Buttons from database
		for (int i = 0; i < mArrayList.size(); i++) {

			DBRowBean dbrow = (DBRowBean) mArrayList.get(i);

			// use custom layout called flowLayout
			FlowLayout.LayoutParams p = new FlowLayout.LayoutParams(
					FlowLayout.LayoutParams.FILL_PARENT,
					FlowLayout.LayoutParams.WRAP_CONTENT);
			// created Button, pass in the Database Row and reference to our
			// activity
			// ButtonDB handles its own click, but needs a reference to this
			// activity
			// to start an Intent...see fireWebView method
			ButtonDB buttonView = new ButtonDB(this, dbrow);
			buttonView.setText(dbrow.getName());
			// manually set size of buttons so they don't resize according to
			// their text length
			buttonView.setWidth(150);
			buttonView.setHeight(60);

			// set background color of buttons
			buttonView.getBackground().setColorFilter(0xff66ff00,
					PorterDuff.Mode.MULTIPLY);

			//buttonView.offsetTopAndBottom(5);

			layout.addView(buttonView, p);
			c.moveToNext();

		}
		db.close();

	}

	/**
	 * Creates an intent with an onClickListener attached.
	 * 
	 * @param onClickListener
	 * @param class1
	 * @return
	 */
	protected Intent Intent(OnClickListener onClickListener,
			Class<MyWebView> class1) {
		Intent j = new Intent(this, MyWebView.class);
		startActivity(j);
		return null;
	}

	/**
	 * action bar creation
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * create items of action bar along with their action code
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		// starts a new webView the user can enter a URL into
		case R.id.menu_search:
			Intent i = new Intent(this, MyWebView.class);
			startActivity(i);
			

			return true;

			// refresh screen
		case R.id.menu_refresh:
			startActivity(getIntent());
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// for debugging from ButtonDB
	public void fireToast(String s) {
		Toast.makeText(QuickNewsActivity.this, s, Toast.LENGTH_LONG).show();
	}

	/**
	 * Sets the longClick handler of a button. LongClick activates an Alert
	 * Dialog. Alert Dialog allows user to delete saved website.
	 * 
	 * @param dbrow
	 */
	public void fireLongClick(final DBRowBean dbrow) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Delete Website");
		builder.setMessage("Are you sure you want to Delete this website?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {				
				db.open();				
				Long Lid = new Long(dbrow.getId());				
				db.deleteRow(Lid.longValue());				
				db.close();
				startActivity(getIntent());
				
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.show();

	}

	/**
	 * attaches id, name, and url to the intent called from the ButtonDb onClick
	 * method, when user clicks a button
	 * 
	 * @param dbrow
	 */
	public void fireWebView(DBRowBean dbrow) {
		Intent i = new Intent(this, MyWebView.class);
		i.putExtra("id", dbrow.getId());
		i.putExtra("name", dbrow.getName());
		i.putExtra("url", dbrow.getUrl());
		// not sure if need results back yet
		startActivityForResult(i, REQUEST_CODE);
	}

}
