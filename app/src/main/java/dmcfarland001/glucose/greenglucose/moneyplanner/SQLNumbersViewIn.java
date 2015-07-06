package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SQLNumbersViewIn extends Activity {
	SQLMoneyIn sqlIn;

	String grouped_notes_ordered_dates_in_notes;
	String grouped_notes_ordered_dates_in_values;
	String grouped_notes_ordered_dates_in_dates;

//	String notesIn;
//	String valuesIn;
//	String datesIn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.number_note_value_date_in);

		TextView tvn = (TextView) findViewById(R.id.numSQLinfoIn_notes);
		TextView tvv = (TextView) findViewById(R.id.numSQLinfoIn_values);
		TextView tvd = (TextView) findViewById(R.id.numSQLinfoIn_dates);
		
		sqlIn = new SQLMoneyIn(this);

		try {

			sqlIn.open();

			grouped_notes_ordered_dates_in_notes = sqlIn
					.getNotes();
			grouped_notes_ordered_dates_in_values = sqlIn
					.getValues();
			grouped_notes_ordered_dates_in_dates = sqlIn
					.getDates();

			sqlIn.close();

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
			Intent intent2 = new Intent(this, SQLNumbersViewOut.class);
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
		}
	}
}