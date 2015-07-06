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

public class SQLAveOut extends Activity {

	SQLMoneyOut infoOut;
	CalcTwo calc_two;

	// MIN VIEW
	// SQL NOTES
	// CALC TWO VALUES AND DATES.

	String grouped_notes_ordered_dates_out_notes;
	String grouped_notes_ordered_dates_out_values;
	String grouped_notes_ordered_dates_out_dates;

	String[] set_notes_out;
	String[] grouped_ordered_notes_out;
	String[] grouped_ordered_values_out;
	String[] grouped_ordered_dates_out;
	

	float[] mean_values_out;
	int[] mean_dates_out;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.sql_ave_out);

		TextView c_tvn = (TextView) findViewById(R.id.sql_ave_out_notes);
		TextView c_tvv = (TextView) findViewById(R.id.sql_ave_out_values);
		TextView c_tvd = (TextView) findViewById(R.id.sql_ave_out_dates);

		infoOut = new SQLMoneyOut(this);
		calc_two = new CalcTwo();

		try {

			infoOut.open();

			grouped_notes_ordered_dates_out_notes = infoOut.MinViewNote();

			set_notes_out = infoOut.SetgetAllNotesOut();

			grouped_ordered_notes_out = infoOut.groupNameOrderDateNoteA();

			grouped_ordered_values_out = infoOut.groupNameOrderDateValueA();
			
			grouped_ordered_dates_out = infoOut.groupNameOrderDateDateA();

			infoOut.close();

			mean_values_out = calc_two.getMeanValues_OUT(set_notes_out,
					grouped_ordered_notes_out, grouped_ordered_values_out);

			mean_dates_out = calc_two.getMeanDays_OUT(set_notes_out,
					grouped_ordered_notes_out, grouped_ordered_dates_out);

			String values = "";
			String dates = "";

			for (int i = 0; i < mean_values_out.length; i++) {
				values = values + String.valueOf(mean_values_out[i]) + "\n";
			}

			for (int j = 0; j < mean_dates_out.length; j++) {
				dates = dates + String.valueOf(mean_dates_out[j]) + "\n";
			}

			c_tvn.setText(grouped_notes_ordered_dates_out_notes);
			c_tvv.setText(values);
			c_tvd.setText(dates);

		} catch (Exception e) {
			String error = e.toString();
			Log.d("SQLMinViewIn error", error);
		}
	}
	Intent intent3;
	// AVERAGES OUT table
	public void OnClick(final View view) {

		switch (view.getId()) {

		// goto mini view of OUT values
		case R.id.plus_btn:
			Intent intent1 = new Intent(this, SQLMinViewOut.class);
			startActivity(intent1);
			break;

		// goto mini IN
		case R.id.in_min_btn:
			Intent intent2 = new Intent(this, SQLAveIn.class);
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