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
 */
import hirondelle.date4j.DateTime;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SQLMinViewIn extends Activity {

	SQLMoneyIn infoIn;
	CalcTwo calc_two;

	String grouped_notes_ordered_dates_in_notes;
	String grouped_notes_ordered_dates_in_values;
	String grouped_notes_ordered_dates_in_dates;
	String dates = "";
	String values = "";

	// notes
	String[] set_notes_in;
	String[] grouped_ordered_notes_in;

	// values
	String[] grouped_ordered_values_in;

	// dates
	String[] future_dates_in;
	String[] grouped_ordered_dates_in;

	float[] mean_values_in;
	int[] mean_dates_in;

	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);

		setContentView(R.layout.col_sql_in_table);

		TextView c_tvn = (TextView) findViewById(R.id.col_tvSQLinfoIn_notes);
		TextView c_tvv = (TextView) findViewById(R.id.col_tvSQLinfoIn_values);
		TextView c_tvd = (TextView) findViewById(R.id.col_tvSQLinfoIn_dates);

		infoIn = new SQLMoneyIn(this);

		calc_two = new CalcTwo();

		try {

			infoIn.open();

			grouped_notes_ordered_dates_in_notes = infoIn.MinViewNote();

			set_notes_in = infoIn.SetgetAllNotesIn();

			// This goes into later function to get means.
			grouped_ordered_values_in = infoIn.groupNameOrderDateValueA();
			grouped_ordered_notes_in = infoIn.groupNameOrderDateNoteA();
			grouped_ordered_dates_in = infoIn.groupNameOrderDateDateA();

			infoIn.close();

			mean_values_in = calc_two.getMeanValues_IN(set_notes_in,
					grouped_ordered_notes_in, grouped_ordered_values_in);

			mean_dates_in = calc_two.getMeanDays_IN(set_notes_in,
					grouped_ordered_notes_in, grouped_ordered_dates_in);

			future_dates_in = calc_two.futureDates_IN(mean_dates_in);

			String temp, tmp, temp_m, temp_d, temp_y, dateForm;

			DateTime yo;

			for (int j = 0; j < future_dates_in.length; j++) {

				temp = future_dates_in[j];
				tmp = temp.replace("-", " ");
				temp_m = tmp.substring(5, 7);
				temp_d = tmp.substring(8, 10);
				temp_y = tmp.substring(0, 4);

				yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
						Integer.valueOf(temp_m), Integer.valueOf(temp_d));

				dateForm = yo.format("MM-DD-YYYY");

				dates = dates + dateForm + "\n";
			}

			for (int i = 0; i < mean_values_in.length; i++) {
				values = values + String.valueOf(mean_values_in[i]) + "\n";
			}

			c_tvn.setText(grouped_notes_ordered_dates_in_notes);
			c_tvv.setText(values);
			c_tvd.setText(dates);

		} catch (Exception e) {
			String error = e.toString();
			Log.d("SQLMinViewIn-0 error", error);
		}
	}

	Intent intent1, intent2, intent3, intent4;

	// Minimized IN table
	public void OnClick(final View view) {

		switch (view.getId()) {

		// goto SQLSetTableViewIn
		case R.id.plus_btn:
			intent1 = new Intent(this, SQLSetTableViewIn.class);
			startActivity(intent1);
			break;

		// goto mini OUT
		case R.id.out_min_btn:
			intent2 = new Intent(this, SQLMinViewOut.class);
			startActivity(intent2);
			break;

		case R.id.list_btn:

			setContentView(R.layout.splash);

			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					intent3 = new Intent(view.getContext(), LinearList.class);
					startActivityForResult(intent3, 0);
				}
			}, 100);

			break;

		case R.id.add_btn:
			intent4 = new Intent(this, DatePicker.class);
			startActivity(intent4);
			break;
		}
	}

}
