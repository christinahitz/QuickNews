package chitz.edu.cvtc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Creates a webivew inside the appliction to be used for all application
 * specific web viewing
 * 
 * @author Christina Hitz
 * 
 */

public class MyWebView extends Activity {

	private WebView webView; // declare a reference to the webView
	private WebSettings webSettings; // declare a referece to webSettings
	private EditText urlField; // declare a reference to the urlField
	private Button goButton; // declare a reference to the goButton
	private InterfaceDB db; // declare a reference to the database
	private String mTitle; // declare a reference for the Name of the Website
	private String mURL; // declare a reference for the URL of website

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			
			this.mURL = extras.getString("url");
		}

		onCreateOptionsMenu();

		// Create reference to UI elements
		webView = (WebView) findViewById(R.id.webview);
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		urlField = (EditText) findViewById(R.id.url);
		goButton = (Button) findViewById(R.id.btnGo);

		// pass dynamic buttons URL as text of urlField
		urlField.setText(this.mURL);

		// workaround so that the default browser doesn't take over
		webView.setWebViewClient(new MyWebViewClient());

		// pass a reference to this activity to the InterfaceDB constructor
		db = new InterfaceDB(this);

		// Setup click listener for goButton to open the URL
		goButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// Toast.makeText(MyWebView.this, "Go Button Clicked",
				// Toast.LENGTH_SHORT).show();
				openURL();

			}

		});

		// Setup key listener: open URL when keyboard enter key is pressed
		urlField.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					openURL();
					return true;
				} else {
					return false;
				}
			}
		});

		// and finally load the url: opens URL for button from QuickNewsACtivity
		openURL();

	}

	// Setup key listener: back button on phone to perform back action within
	// website
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void onCreateOptionsMenu() {
		// TODO Auto-generated method stub

	}

	/** Opens the URL in a webView */
	private void openURL() {
		// Toast.makeText(MyWebView.this, "Open URL" +
		// urlField.getText().toString(),
		// Toast.LENGTH_SHORT).show();

		// trims white space in urlField and convert to lower case
		String s = urlField.getText().toString().trim().toLowerCase();

		// Don't load empty url
		if (s.length() == 0) {
			return;
		}

		if (s.contains("http://www.")) {
			webView.loadUrl(urlField.getText().toString());
		} else if (s.contains("http://m")){
			webView.loadUrl(urlField.getText().toString());
		}else	{
			webView.loadUrl("http://www." + urlField.getText().toString());
		}

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		// stops the default browser from opening the url, opens in webView
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		// when page is finished loading
		public void onPageFinished(WebView view, String url) {

			// assign the URL from the website to mUrl
			mURL = webView.getUrl();			
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(urlField.getWindowToken(), 0);

			// assign the title from the website to mTitle
			mTitle = webView.getTitle();

		}

	}

	// action bar createion
	public boolean onCreateOptionsMenu(Menu menu) {
		// instantiate menu XML file
		MenuInflater inflater = getMenuInflater();
		// uses res/menu/main.xml
		inflater.inflate(R.menu.webview, menu);
		return true;
	}

	// used when menu items are selected by the user
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		// handles save on action bar
		case R.id.menu_save:
			// calls saveWebsite method
			saveWebsite();

			return true;

			// handles the action of home on action bar, sends user back to
			// QuickNewsActivity
		case R.id.menu_home:
			Intent i = new Intent(this, QuickNewsActivity.class);
			startActivity(i);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Save website method
	 */
	private void saveWebsite() {

		// open database
		db.open();

		// declare and assign variables
		String strURL = mURL;
		String strName = mTitle;

		long id;

		id = db.insertName(strName, strURL);
		if (id != -1L) {

		} else { // if the id is -1, the insert failed.

			new AlertDialog.Builder(this)
					.setMessage("Failed to save your event to the database")
					.setNeutralButton("Close", null).show();

		}

		db.close();

	}

}
