package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SQLDeleteDB extends Activity {

	SQLMoneyIn sqlIn;
	SQLMoneyOut sqlOut;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.delete_db);

		sqlIn = new SQLMoneyIn(this);
		sqlOut = new SQLMoneyOut(this);

	}

	public void OnClick(View view) {

		switch (view.getId()) {

		case R.id.delete_db_in:

			sqlIn.open();

			sqlIn.deleteDataBase();

			sqlIn.close();

			break;

		case R.id.delete_db_out:

			sqlOut.open();

			sqlOut.deleteDataBase();

			sqlOut.close();

			break;

		case R.id.delete_last_thirty:
			
			sqlIn.open();
			sqlOut.open();

			// Redo this function. to delete a certain amount of entries from a specific list.
			// Lets day my Publix list is 14 long. Cap off at 8
			// Take balance from the linear list and match up. Not going to work.
			// Recalculate everything with new databse. Popup to ask for an evening value.
			// Evening value should be added but hidden. nah just call it "Balancing Value"
			//sqlOut.DeleteLastThirtyUpdate();
			//sqlIn.DeleteLastThirtyUpdate();
			
			sqlOut.close();
			sqlIn.close();
			break;
		}
	}

}
