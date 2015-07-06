package dmcfarland001.glucose.greenglucose.moneyplanner;

/**
 * @author
 * @copywright Daniel McFarland DMcFarland001@gmail.com "Green Glucose Inc."
 * "Green Glucose" This is an Android application that uses statistics
 * to map out a future bank account. This information is meant only for
 * informational purposes and no responsibility's are on me the author
 * of this application. I take no responsibility and have no liability
 * for any information displayed on or in this application. Only
 * intention for this application is to guess information.
 */

import hirondelle.date4j.DateTime;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LinearList extends Activity {

    CalcTwo calc_two;
    Running_Calculations running_calculations;
    SQLMoneyIn sql_in_table;
    SQLMoneyOut sql_out_table;

    DecimalFormat df = new DecimalFormat("#.##");

    // Future dates match up with SET notes
    String[] future_date_in;
    String[] future_date_out;
    // String[] future_value_in;
    // String[] future_value_out;

    float[] mean_values_in;
    float[] mean_values_out;
    int[] mean_dates_in;
    int[] mean_dates_out;

    // Linear String arrays sorted by date.
    // note value date
    String[] ordered_notes_in;
    String[] ordered_notes_out;
    String[] ordered_values_in;
    String[] ordered_values_out;
    String[] ordered_dates_in;
    String[] ordered_dates_out;

    // Grouped by notes and sorted by date.
    // These are to be sent into calc two to get averages.
    String[] grouped_ordered_notes_in;
    String[] grouped_ordered_notes_out;
    String[] grouped_ordered_values_in;
    String[] grouped_ordered_values_out;
    String[] grouped_ordered_dates_in;
    String[] grouped_ordered_dates_out;

    float[] graph_past_vals_in;
    float[] graph_past_vals_out;

    String[] no_duplicates_dates_in;
    String[] no_duplicates_dates_out;

    String[] no_duplicates_notes_in;
    String[] no_duplicates_notes_out;

    // SET NOTES
    String[] set_notes_in;
    String[] set_notes_out;

    String[] graph_linear_dates;
    String[] graph_linear_plots;
    String[] graph_linear_notes;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.linear_list);

        TextView c_tvn = (TextView) findViewById(R.id.linear_notes);
        TextView c_tvv = (TextView) findViewById(R.id.linear_values);
        TextView c_tvd = (TextView) findViewById(R.id.linear_dates);

        calc_two = new CalcTwo();

        running_calculations = new Running_Calculations();

        sql_in_table = new SQLMoneyIn(this);
        sql_out_table = new SQLMoneyOut(this);

        try {

            sql_in_table.open();
            sql_out_table.open();

            // Main set of notes with no dup.
            set_notes_in = sql_in_table.SetgetAllNotesIn();
            set_notes_out = sql_out_table.SetgetAllNotesOut();

            // Order by date. This is for the linear view of a graph.
            // Not for comparing.
            ordered_notes_in = sql_in_table.orderByDateNotes();
            ordered_notes_out = sql_out_table.orderByDateNotes();

            ordered_values_in = sql_in_table.orderByDateValues();
            ordered_values_out = sql_out_table.orderByDateValues();

            ordered_dates_in = sql_in_table.orderByDateDate();
            ordered_dates_out = sql_out_table.orderByDateDate();

            // Get the grouped arrays for comparing.
            grouped_ordered_notes_in = sql_in_table.groupNameOrderDateNoteA();
            grouped_ordered_notes_out = sql_out_table.groupNameOrderDateNoteA();

            grouped_ordered_values_in = sql_in_table.groupNameOrderDateValueA();
            grouped_ordered_values_out = sql_out_table
                    .groupNameOrderDateValueA();

            grouped_ordered_dates_in = sql_in_table.groupNameOrderDateDateA();
            grouped_ordered_dates_out = sql_out_table.groupNameOrderDateDateA();

            no_duplicates_dates_in = sql_in_table.NoDuplicatesDates();
            no_duplicates_dates_out = sql_out_table.NoDuplicatesDates();

            no_duplicates_notes_in = sql_in_table.NoDuplicatesDatesNotes();
            no_duplicates_notes_out = sql_out_table.NoDuplicatesDatesNotes();

            sql_in_table.close();
            sql_out_table.close();

            // open calc two get a SET.size() == array of mean days difference
            // for
            // each note in the SET
            // mean values and mean days that match up with SET notes
            mean_values_in = calc_two.getMeanValues_IN(set_notes_in,
                    grouped_ordered_notes_in, grouped_ordered_values_in);

            mean_values_out = calc_two.getMeanValues_OUT(set_notes_out,
                    grouped_ordered_notes_out, grouped_ordered_values_out);

            mean_dates_in = calc_two.getMeanDays_IN(set_notes_in,
                    grouped_ordered_notes_in, grouped_ordered_dates_in);

            mean_dates_out = calc_two.getMeanDays_OUT(set_notes_out,
                    grouped_ordered_notes_out, grouped_ordered_dates_out);

            future_date_in = calc_two.futureDates_IN(mean_dates_in);
            future_date_out = calc_two.futureDates_OUT(mean_dates_out);


            // These values match up with no_duplicates_dates_in / out BECAUSE
            // its on a daily counter. lined up with days.
            graph_past_vals_in = calc_two.GraphPastVals_IN(
                    grouped_ordered_values_in, grouped_ordered_dates_in,
                    no_duplicates_dates_in);

            graph_past_vals_out = calc_two.GraphLinearValues(
                    grouped_ordered_values_out, grouped_ordered_dates_out,
                    no_duplicates_dates_out);


            // This is to get the values added up in a linear list.
            graph_linear_plots = calc_two.GraphLinearPlots(
                    no_duplicates_dates_in, no_duplicates_dates_out,
                    graph_past_vals_in, no_duplicates_notes_in,
                    no_duplicates_notes_out, graph_past_vals_out,
                    future_date_in, future_date_out, mean_values_in,
                    mean_values_out);


            graph_linear_dates = calc_two.GraphLinearDates();

            graph_linear_notes = calc_two.GraphLinearNotes();


            String values = "";
            String dates = "";
            String notes = "";

            String temp, tmp, temp_m, temp_d, temp_y, dateForm;

            DateTime yo;

            for (int j = graph_linear_notes.length - 1; j >= 0; j--) {


                notes = notes + graph_linear_notes[j] + "\n";
            }

            for (int i = graph_linear_plots.length - 1; i >= 0; i--) {

                values = values
                        + df.format(Float.valueOf(graph_linear_plots[i]))
                        + "\n";
            }

            for (int j = graph_linear_dates.length - 1; j >= 0; j--) {

                temp = graph_linear_dates[j];

                tmp = temp.replace("-", " ");

                temp_y = tmp.substring(0, 4);
                temp_m = tmp.substring(5, 7);
                temp_d = tmp.substring(8, 10);


                yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
                        Integer.valueOf(temp_m), Integer.valueOf(temp_d));


                dateForm = yo.format("MM-DD-YYYY");

                dates = dates + dateForm + "\n";
            }

            // Text View Notes, Values, Dates.
            c_tvn.setText(notes);
            c_tvv.setText(values);
            c_tvd.setText(dates);

        } catch (Exception e) {
            String error = e.toString();
            Log.d("LinearList error", error);
        }
    }

    Intent intent1, intent2, intent3, intent4, intent5;

    String[] BASE_and_min_values;

    // Linear List Table in out graph add
    public void OnClick(final View view) {

        switch (view.getId()) {

            // goto mini in table
            case R.id.in_min_btn:

                intent1 = new Intent(this, SQLMinViewIn.class);

                startActivity(intent1);

                break;

            // goto mini OUT
            case R.id.out_min_btn:

                intent2 = new Intent(this, SQLMinViewOut.class);

                startActivity(intent2);

                break;

            case R.id.graph_btn:

                setContentView(R.layout.splash);

                final Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent3 = new Intent(view.getContext(), Graph_One.class);
                        startActivityForResult(intent3, 0);
                    }
                }, 2000);

                break;

            case R.id.add_btn:

                intent4 = new Intent(this, DatePicker.class);

                startActivity(intent4);

                break;

            case R.id.main_button_000:

                intent5 = new Intent(this, MainActivity.class);

                BASE_and_min_values = running_calculations.set_ticker(graph_linear_plots, graph_linear_dates, graph_linear_notes);

                intent5.putExtra("BASE_cntr", BASE_and_min_values[0]);
                intent5.putExtra("working_values_diff_per_minute", BASE_and_min_values[1]);

                startActivity(intent5);

                break;
        }
    }

}