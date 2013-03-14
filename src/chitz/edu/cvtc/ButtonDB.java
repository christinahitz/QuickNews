package chitz.edu.cvtc;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.ContextMenu;  

import android.view.ContextMenu.ContextMenuInfo; 

/**
 * Manages the actions of the dynamically created buttons, allowing the information to 
 * be passed to QuickNewsActivity
 * 
 * @ author Christina Hitz
 */

public class ButtonDB extends Button implements View.OnClickListener {
	DBRowBean dbrow;
	QuickNewsActivity a;

	public ButtonDB(QuickNewsActivity a, DBRowBean dbrow) {
		super(a);
		this.a = a;
		this.dbrow = dbrow;
		setOnClickListener(this);
		this.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
				fireLongClick(); // can't invoke the fireLongClick on
									// QuickNewsActivity
				return true;
			}
		});
		
	}

	/**
	 * Wrapper method to get back to QuickNewsActivity
	 */
	public void fireLongClick() {
		a.fireLongClick(dbrow);
	}

	/**
	 * called when a button has been clicked
	 */
	public void onClick(View v) {	
		//a.fireToast("howdy howdy from ButtonDB " + dbrow.getName());
		a.fireWebView(dbrow);
	}

}
