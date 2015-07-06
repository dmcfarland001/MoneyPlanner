package dmcfarland001.glucose.greenglucose.moneyplanner;

/**
 * 
 * @author 
 * @copywright 
 * 			Daniel McFarland DMcFarland001@gmail.com "Green Glucose Inc."
 *         "Green Glucose" This is an Android application that uses statistics
 *         to map out a future bank account. This information is meant only for
 *         informational purposes and no responsibility's are on me the author
 *         of this application. I take no responsibility and have no liability
 *         for any information displayed on or in this application. Only
 *         intention for this application is to guess information.
 *
 *
 *         Change when taking in a value. Take in a string of length cut off but then when printing to a string
 *         in the sql database file create a function that scans the item before adding it to the string for print and cuts it off.
 *
 *
 * 			Check now many nodes in each bit of the set.
 * 				if = 1	attach a tag or either exclude later.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutPage extends Activity {

	

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.about_page);
		
	}

	public void OnClick(View view) {

		switch (view.getId()) {
		case R.id.view_in_all:
			Intent intent6 = new Intent(this, SQLTableInAll.class);
			startActivity(intent6);
			break;

		case R.id.view_out_all:
			Intent intent7 = new Intent(this, SQLTableOutAll.class);
			startActivity(intent7);
			break;
			
		case R.id.delete_but:
			Intent intent8 = new Intent(this, SQLDeleteDB.class);
			startActivity(intent8);
			break;

		
		}
	}

}
