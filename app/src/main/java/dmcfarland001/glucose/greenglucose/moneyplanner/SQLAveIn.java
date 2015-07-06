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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SQLAveIn extends Activity {

	SQLMoneyIn infoIn;
	CalcTwo calc_two;

	String grouped_notes_ordered_dates_in_notes;
	String grouped_notes_ordered_dates_in_values;
	String grouped_notes_ordered_dates_in_dates;

	String[] set_notes_in;
	String[] grouped_ordered_values_in;
	String[] grouped_ordered_dates_in;
	String[] grouped_ordered_notes_in;

	float[] mean_values_in;
	int[] mean_dates_in;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.sql_ave_in);

		TextView c_tvn = (TextView) findViewById(R.id.sql_ave_in_notes);
		TextView c_tvv = (TextView) findViewById(R.id.sql_ave_in_values);
		TextView c_tvd = (TextView) findViewById(R.id.sql_ave_in_dates);

		infoIn = new SQLMoneyIn(this);
		calc_two = new CalcTwo();

		try {

			infoIn.open();

			grouped_notes_ordered_dates_in_notes = infoIn.MinViewNote();

			set_notes_in = infoIn.SetgetAllNotesIn();

			grouped_ordered_notes_in = infoIn.groupNameOrderDateNoteA();

			grouped_ordered_values_in = infoIn.groupNameOrderDateValueA();

			grouped_ordered_dates_in = infoIn.groupNameOrderDateDateA();

			infoIn.close();

			mean_values_in = calc_two.getMeanValues_IN(set_notes_in,
					grouped_ordered_notes_in, grouped_ordered_values_in);

			mean_dates_in = calc_two.getMeanDays_IN(set_notes_in,
					grouped_ordered_notes_in, grouped_ordered_dates_in);

			String values = "";
			String dates = "";

			for (int i = 0; i < mean_values_in.length; i++) {
				values = values + String.valueOf(mean_values_in[i]) + "\n";
			}

			for (int j = 0; j < mean_dates_in.length; j++) {
				dates = dates + String.valueOf(mean_dates_in[j]) + "\n";
			}

			c_tvn.setText(grouped_notes_ordered_dates_in_notes);
			c_tvv.setText(values);
			c_tvd.setText(dates);

		} catch (Exception e) {
			String error = e.toString();
			Log.d("SQLMinViewIn error", error);
		}
	}
	Intent intent3;
	// Averages In Table
	public void OnClick(final View view) {

		switch (view.getId()) {

		// goto mini in table
		case R.id.plus_btn:
			Intent intent1 = new Intent(this, SQLMinViewIn.class);
			startActivity(intent1);
			break;

		// goto mini OUT
		case R.id.out_min_btn:
			Intent intent2 = new Intent(this, SQLAveOut.class);
			startActivity(intent2);
			break;

		case R.id.ave_graph_btn:
			setContentView(R.layout.splash);

			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					intent3 = new Intent(view.getContext(), Graph_One.class);
					startActivityForResult(intent3, 0);
				}
			}, 100);
			break;

		case R.id.add_btn:
			Intent intent4 = new Intent(this, DatePicker.class);
			startActivity(intent4);
			break;
		}
	}

}
