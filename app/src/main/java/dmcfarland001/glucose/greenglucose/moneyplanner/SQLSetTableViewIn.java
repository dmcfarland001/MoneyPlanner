package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author Daniel McFarland
 * 			DMcFarland001@gmail.com "Green Glucose Inc." "Green Glucose"
 * 			This is an Android application that uses statistics to map out a future bank account.
 * 			This information is meant only for informational purposes and no responcibility's are on me the author of this application.
 * 			I take no responsibility and have no liability for any information displayed on or in this application.
 * 			Only intention for this application is to guess information.
 *
 */

public class SQLSetTableViewIn extends Activity {

	SQLMoneyIn infoIn;

	String grouped_notes_ordered_dates_in_notes;
	String grouped_notes_ordered_dates_in_values;
	String grouped_notes_ordered_dates_in_dates;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.in_sql_table);

		TextView tvn = (TextView) findViewById(R.id.tvSQLinfoIn_notes);
		TextView tvv = (TextView) findViewById(R.id.tvSQLinfoIn_values);
		TextView tvd = (TextView) findViewById(R.id.tvSQLinfoIn_dates);

		infoIn = new SQLMoneyIn(this);

		try {

			infoIn.open();

			grouped_notes_ordered_dates_in_notes = infoIn
					.groupNameOrderDateNote();
			grouped_notes_ordered_dates_in_values = infoIn
					.groupNameOrderDateValue();
			grouped_notes_ordered_dates_in_dates = infoIn
					.groupNameOrderDateDate();

			infoIn.close();

			tvn.setText(grouped_notes_ordered_dates_in_notes);
			tvv.setText(grouped_notes_ordered_dates_in_values);
			tvd.setText(grouped_notes_ordered_dates_in_dates);

		} catch (Exception e) {
			String error = e.toString();
			Log.d("SQLSetTableViewIn error", error);
		}
	}

	public void OnClick(View view) {

		switch (view.getId()) {

		case R.id.min_btn:
			Intent intent1 = new Intent(this, SQLMinViewIn.class);
			startActivity(intent1);
			break;
		case R.id.out_table_btn:
			Intent intent2 = new Intent(this, SQLSetTableViewOut.class);
			startActivity(intent2);
			break;
		case R.id.in_averages_btn:
			Intent intent3 = new Intent(this, SQLAveIn.class);
			startActivity(intent3);
			break;
		case R.id.add_btn:
			Intent intent4 = new Intent(this, DatePicker.class);
			startActivity(intent4);
			break;
		case R.id.numbers_btn_in:
			Intent intent5 = new Intent(this, SQLNumbersViewIn.class);
			startActivity(intent5);
			break;
		}
	}
}
