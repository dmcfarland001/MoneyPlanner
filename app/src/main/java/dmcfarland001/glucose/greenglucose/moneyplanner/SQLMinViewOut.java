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

public class SQLMinViewOut extends Activity {

	SQLMoneyOut infoOut;
	CalcTwo calc_two;

	String grouped_notes_ordered_dates_out_notes;
	String grouped_notes_ordered_dates_out_values;
	
	String dates = "";
	
	float[] mean_values_out;
	int[] mean_dates_out;
	
	String[] future_dates_out;

	String[] set_notes_out;
	String[] grouped_ordered_notes_out;
	String[] grouped_ordered_values_out;
	String[] grouped_ordered_dates_out;
	
	String values = "";

	// String notesOut;
	// String valuesOut;
	// String datesOut;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Set to col_sql_out_table.xml
		setContentView(R.layout.col_sql_out_table);

		TextView tvn = (TextView) findViewById(R.id.col_tvSQLinfoOut_notes);
		TextView tvv = (TextView) findViewById(R.id.col_tvSQLinfoOut_values);
		TextView tvd = (TextView) findViewById(R.id.col_tvSQLinfoOut_dates);

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

			future_dates_out = calc_two.futureDates_OUT(mean_dates_out);
			
			String temp, tmp, temp_m, temp_d, temp_y, dateForm;

			DateTime yo;

			for (int j = 0; j < future_dates_out.length; j++) {
				
				temp = future_dates_out[j];
				tmp = temp.replace("-", " ");
				temp_m = tmp.substring(5, 7);
				temp_d = tmp.substring(8, 10);
				temp_y = tmp.substring(0, 4);

				yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
						Integer.valueOf(temp_m), Integer.valueOf(temp_d));

				dateForm = yo.format("MM-DD-YYYY");
				
				dates = dates + dateForm + "\n";
			}

			
			
			for (int i = 0; i < mean_values_out.length; i++) {
				values = values + String.valueOf(mean_values_out[i]) + "\n";
			}
			
			tvn.setText(grouped_notes_ordered_dates_out_notes);
			tvv.setText(values);
			tvd.setText(dates);

		} catch (Exception e) {
			String error = e.toString();
			Log.d("SQLMinViewOut error", error);
		}
	}
	
	Intent intent1, intent2, intent3, intent4;
	
	// minimized table OUT view
	public void OnClick(final View view) {

		switch (view.getId()) {

		// goto SQLSetTableViewOut
		case R.id.plus_btn:
			 intent1 = new Intent(this, SQLSetTableViewOut.class);
			startActivity(intent1);
			break;

		// go to mini IN
		case R.id.in_min_btn:
			 intent2 = new Intent(this, SQLMinViewIn.class);
			startActivity(intent2);
			break;

		// goto OUT averages
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

		// add values
		case R.id.add_btn:
			 intent4 = new Intent(this, DatePicker.class);
			startActivity(intent4);
			break;
		}
	}

}
