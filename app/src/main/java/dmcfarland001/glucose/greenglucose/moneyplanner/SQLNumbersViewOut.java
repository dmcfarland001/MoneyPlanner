package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SQLNumbersViewOut extends Activity {
	SQLMoneyOut sqlOut;

	String grouped_notes_ordered_dates_out_notes;
	String grouped_notes_ordered_dates_out_values;
	String grouped_notes_ordered_dates_out_dates;

	// String notesOut;
	// String valuesOut;
	// String datesOut;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.number_note_value_date_out);

		TextView tvn = (TextView) findViewById(R.id.numSQLinfoIn_notes);
		TextView tvv = (TextView) findViewById(R.id.numSQLinfoIn_values);
		TextView tvd = (TextView) findViewById(R.id.numSQLinfoIn_dates);

		sqlOut = new SQLMoneyOut(this);

		try {

			sqlOut.open();

			// notesOut = infoOut.getNotes();
			// valuesOut = infoOut.getValues();
			// datesOut = infoOut.getDates();

			grouped_notes_ordered_dates_out_notes = sqlOut
					.getNotes();
			grouped_notes_ordered_dates_out_values = sqlOut
					.getValues();
			grouped_notes_ordered_dates_out_dates = sqlOut
					.getDates();

			sqlOut.close();

			tvn.setText(grouped_notes_ordered_dates_out_notes);
			tvv.setText(grouped_notes_ordered_dates_out_values);
			tvd.setText(grouped_notes_ordered_dates_out_dates);

		} catch (Exception e) {
			String error = e.toString();
			Log.d("SQLSet out", error);
		}
	}

	public void OnClick(View view) {

		switch (view.getId()) {

		case R.id.min_btn:
			Intent intent1 = new Intent(this, SQLMinViewOut.class);
			startActivity(intent1);
			break;
		case R.id.in_table_btn:
			Intent intent2 = new Intent(this, SQLNumbersViewIn.class);
			startActivity(intent2);
			break;
		case R.id.out_averages_btn:
			Intent intent3 = new Intent(this, SQLAveOut.class);
			startActivity(intent3);
			break;
		case R.id.add_btn:
			Intent intent4 = new Intent(this, DatePicker.class);
			startActivity(intent4);
			break;
		}
	}

}