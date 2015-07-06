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

import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hirondelle.date4j.DateTime;

@SuppressLint("CutPasteId")
public class DatePicker extends Activity {

    /**
     * go into calc two and reformat when the date is being picked ie. when
     * comparing dates
     * <p>
     * go into sql table and reformat how data is printed or formatted into a
     * string for printing.
     */

    // This goes to the database IN
    SQLMoneyIn sql_in_table;
    // This goes to the database OUT
    SQLMoneyOut sql_out_table;
    // Editing Note, Value, Row ID, Row Date
    EditText edit_note, edit_value, edit_row_id;
    // String Note, Value, ID, Date_String
    String note, value, id, dateS;

    // This is for the popup / Message
    static TextView set_date_btn_txt, set_date_txt_edit, edit_row_date;
    static String date_tv;
    static String m;
    static String d;
    static String y;

    static String date_for_edit;


    DateTime current_date, plus_date, minus_date;

    DateTime now = DateTime.now(TimeZone.getDefault());

    int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Saved Instance State
        super.onCreate(savedInstanceState);

        // Set the layout to date_picker.xml
        setContentView(R.layout.date_picker);

        // Access to both IN and OUT databses.
        sql_in_table = new SQLMoneyIn(this);
        sql_out_table = new SQLMoneyOut(this);

        // If desired, Not needed because rotate is supported.
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Try to catch Exception
        try {

            // Open both tables for edit.
            sql_in_table.open();
            sql_out_table.open();

            // Get All the records and set them to a SET of Strings.
            Set<String> retNoteIn = sql_in_table.getAllNotesIn();
            Set<String> retNoteOut = sql_out_table.getAllNotesOut();

            // Close both sql tables.
            sql_in_table.close();
            sql_out_table.close();

            // Combine the two sets
            retNoteOut.addAll(retNoteIn);

            // Convert to a String Array with the combined set as a size.
            String[] concatedArgs = retNoteOut.toArray(new String[retNoteOut
                    .size()]);

            // Autocomplete for the drop down box. Has no values yet. This is just setting the View
            AutoCompleteTextView autoTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_edit_note_text);

            // The container for the Autotext. Displays a list.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.autocomplete_list, concatedArgs);

            // Set the view to the container.
            autoTextView.setAdapter(adapter);

        } catch (Exception e) {
            String error = e.toString();
            Log.d("onCreate error", error);
        }

        // Read in the integer value from the text field.
        edit_note = (EditText) findViewById(R.id.auto_complete_edit_note_text);

        // Read in the label attach.
        edit_value = (EditText) findViewById(R.id.d_edit_value);

        // Associate the date with the Top Btn - "Set Date"
        set_date_btn_txt = (TextView) findViewById(R.id.set_date_btn);

        set_date_btn_txt.setText(now.format("MM-DD-YYYY"));

        set_date_txt_edit = (TextView) findViewById(R.id.edit_row_date);

        date_tv = now.format("MM-DD-YYYY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    Intent intent00;

    public void OnClick(final View view) {


        switch (view.getId()) {

            // User has pressed "In"
            case R.id.d_in_btn:
                try {

                    String temp, temp_y = null, temp_m = null, temp_d = null;

                    // The note is set earlier to the text field. Set it to a string
                    note = edit_note.getText().toString();

                    // The value is set earlier to the text field. Set it to a string
                    value = edit_value.getText().toString();

                    // Check for EMPTY
                    if (note.trim().length() > 1 && value.trim().length() > 1
                            && date_tv.trim().length() > 1) {


                        // Reformat date here to start. MM-DD-YYYY to YYYY-MM-DD

                        temp = date_tv.replace("-", " ");

                        Log.d("In btn temp" + temp, temp);


                        temp_m = temp.substring(0, 2);
                        temp_d = temp.substring(3, 5);
                        temp_y = temp.substring(6, 10);


                        Log.d("In btn temp_d" + temp_d, temp_m);
                        Log.d(temp_d, temp_y);


                        // YYYY-MM-DD
                        date_tv = temp_y.concat("-");
                        date_tv = date_tv.concat(temp_m);
                        date_tv = date_tv.concat("-");
                        date_tv = date_tv.concat(temp_d);


                        Log.d("In btn date_tv2  " + date_tv, date_tv);
                        Log.d("In btn note  " + note, note);
                        Log.d("In btn value  " + value, value);


                        sql_in_table.open();

                        sql_in_table.createEntry_date(note, value, date_tv);

                        sql_in_table.close();


                        // Reformat date  to MM-DD-YYYY
                        date_tv = temp_m.concat("-");
                        date_tv = date_tv.concat(temp_d);
                        date_tv = date_tv.concat("-");
                        date_tv = date_tv.concat(temp_y);

                        Log.d("In Ref date_tv2  " + date_tv, date_tv);

                    }

                    ((EditText) findViewById(R.id.auto_complete_edit_note_text)).setText("");
                    ((EditText) findViewById(R.id.d_edit_value)).setText("");

                } catch (Exception e) {

                    String error = e.toString();
                    Dialog d1 = new Dialog(this);
                    d1.setTitle("Please fill in all the fields.");
                    TextView tv1 = new TextView(this);
                    tv1.setText(error);
                    Log.d("d_in_btn error", error);
                    d1.show();

                }


                updateAutoText();


                break;


            // User has pressed OUT. Wants to Store values to the database in the OUT list.
            case R.id.d_out_btn:


                try {


                    String temp, temp_y = null, temp_m = null, temp_d = null;


                    // set it to a string
                    note = edit_note.getText().toString();


                    // set it to a string
                    value = edit_value.getText().toString();

                    if (note.trim().length() > 1 && value.trim().length() > 1
                            && date_tv.trim().length() > 1) {


                        Log.d("Out btn date_tv1  " + date_tv, date_tv);


                        // Coming in as MM-DD-YYYY

                        temp = date_tv.replace("-", " ");

                        Log.d("Out btn temp" + temp, temp);

                        temp_m = temp.substring(0, 2);
                        temp_d = temp.substring(3, 5);
                        temp_y = temp.substring(6, 10);

                        Log.d("Out btn temp_d" + temp_d, temp_m);

                        Log.d(temp_d, temp_y);

                        // Change to
                        // YYYY-MM-DD
                        date_tv = temp_y.concat("-");
                        date_tv = date_tv.concat(temp_m);
                        date_tv = date_tv.concat("-");
                        date_tv = date_tv.concat(temp_d);


                        Log.d("Out btn date_tv2  " + date_tv, date_tv);
                        Log.d("Out btn note  " + note, note);
                        Log.d("Out btn value  " + value, value);


                        sql_out_table.open();

                        sql_out_table.createEntry_date(note, value, date_tv);

                        sql_out_table.close();


                        // Reformat date  to MM-DD-YYYY
                        date_tv = temp_m.concat("-");
                        date_tv = date_tv.concat(temp_d);
                        date_tv = date_tv.concat("-");
                        date_tv = date_tv.concat(temp_y);

                        Log.d("Out btn date_tv2  " + date_tv, date_tv);
                    }

                    ((EditText) findViewById(R.id.auto_complete_edit_note_text)).setText("");

                    ((EditText) findViewById(R.id.d_edit_value)).setText("");

                } catch (Exception e) {

                    String error = e.toString();
                    Dialog d1 = new Dialog(this);
                    d1.setTitle("Please fill in all the fields.");
                    TextView tv1 = new TextView(this);
                    tv1.setText(error);
                    Log.d("d_out_btn error", error);
                    d1.show();

                }

                updateAutoText();

                break;


            case R.id.view_graph_btn:

                setContentView(R.layout.splash);

                final Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent00 = new Intent(view.getContext(), Graph_One.class);
                        startActivityForResult(intent00, 0);
                    }
                }, 100);

                break;


            case R.id.main_btn:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;


            case R.id.to_in_table:
                Intent intent3 = new Intent(this, SQLSetTableViewIn.class);
                startActivity(intent3);
                break;


            case R.id.to_out_table:
                Intent intent4 = new Intent(this, SQLSetTableViewOut.class);
                startActivity(intent4);
                break;


            case R.id.edit_entry_in:

                try {
                    // Read in the integer value from the text field.
                    edit_note = (EditText) findViewById(R.id.auto_complete_edit_note_text);

                    edit_row_date = (TextView) findViewById(R.id.edit_row_date);

                    // read in the label attach.
                    edit_value = (EditText) findViewById(R.id.d_edit_value);

                    edit_row_id = (EditText) findViewById(R.id.edit_row_id);
                    // set it to a string
                    note = edit_note.getText().toString();

                    dateS = edit_row_date.getText().toString();
                    // set it to a string
                    value = edit_value.getText().toString();
                    id = edit_row_id.getText().toString();

                    // set it to a string
                    long id_l = Long.parseLong(id);

                    if (note.trim().length() < 1) {
                        break;
                    }
                    if (value.trim().length() < 1) {
                        break;
                    }

                    sql_in_table.open();

                    sql_in_table.updateEntry(id_l, note, value, dateS);

                    sql_in_table.close();

                } catch (Exception e) {
                    String error = e.toString();
                    Dialog d2 = new Dialog(this);
                    d2.setTitle("Please fill in all the fields.");
                    TextView tv2 = new TextView(this);
                    tv2.setText(error);
                    Log.d("edit_entry_in error", error);
                    d2.show();
                }
                ((EditText) findViewById(R.id.auto_complete_edit_note_text)).setText("");
                ((EditText) findViewById(R.id.d_edit_value)).setText("");
                ((EditText) findViewById(R.id.edit_row_id)).setText("");
                ((TextView) findViewById(R.id.edit_row_date)).setText("");

                findViewById(R.id.edit_row_date).setVisibility(View.INVISIBLE);

                updateAutoText();
                break;


            case R.id.get_info_in:
                try {

                    findViewById(R.id.edit_entry_out).setVisibility(View.VISIBLE);
                    findViewById(R.id.edit_entry_in).setVisibility(View.VISIBLE);

                    findViewById(R.id.edit_row_id).setVisibility(View.VISIBLE);

                    edit_row_id = (EditText) findViewById(R.id.edit_row_id);
                    edit_note = (EditText) findViewById(R.id.auto_complete_edit_note_text);
                    edit_row_date = (TextView) findViewById(R.id.edit_row_date);

                    id = edit_row_id.getText().toString();

                    // int id_again = Integer.valueOf(id);

                    // date = edit_row_date.getText().toString();

                    long id_l_i = Long.parseLong(id);

                    sql_in_table.open();

                    String retNote = sql_in_table.getNote(id_l_i);

                    String retValue = sql_in_table.getValue(id_l_i);

                    String retDate = sql_in_table.getDate(id_l_i);

                    sql_in_table.close();

                    edit_note.setText(retNote);


                    edit_value.setText(retValue);


                    findViewById(R.id.edit_row_date).setVisibility(View.VISIBLE);


                    edit_row_date.setText(retDate);

                } catch (Exception e) {
                    String error = e.toString();
                    Dialog d2 = new Dialog(this);
                    d2.setTitle("Please enter an ID number.");
                    TextView tv2 = new TextView(this);
                    tv2.setText(error);
                    Log.d("get_info_in error", error);
                    d2.show();
                }

                break;


            case R.id.edit_entry_out:

                try {

                    // Read in the integer value from the text field.
                    edit_note = (EditText) findViewById(R.id.auto_complete_edit_note_text);

                    edit_row_date = (TextView) findViewById(R.id.edit_row_date);

                    // read in the label attach.
                    edit_value = (EditText) findViewById(R.id.d_edit_value);
                    edit_row_id = (EditText) findViewById(R.id.edit_row_id);
                    // set it to a string
                    note = edit_note.getText().toString();
                    // set it to a string
                    value = edit_value.getText().toString();
                    id = edit_row_id.getText().toString();
                    dateS = edit_row_date.getText().toString();

                    if (note.trim().length() < 1) {
                        break;
                    }
                    if (value.trim().length() < 1) {
                        break;
                    }

                    // set it to a string
                    long id_l1 = Long.parseLong(id);

                    sql_out_table.open();

                    sql_out_table.updateEntry(id_l1, note, value, dateS);

                    sql_out_table.close();

                } catch (Exception e) {
                    String error = e.toString();
                    Dialog d2 = new Dialog(this);
                    d2.setTitle("Please fill in all the fields.");
                    TextView tv2 = new TextView(this);
                    tv2.setText(error);
                    Log.d("edit_entry_out error", error);
                    d2.show();
                }

                ((EditText) findViewById(R.id.auto_complete_edit_note_text)).setText("");
                ((EditText) findViewById(R.id.d_edit_value)).setText("");
                ((EditText) findViewById(R.id.edit_row_id)).setText("");
                ((TextView) findViewById(R.id.edit_row_date)).setText("");

                findViewById(R.id.edit_row_date).setVisibility(View.VISIBLE);

                updateAutoText();

                break;


            case R.id.get_info_out:
                try {

                    // edit_entry_out
                    findViewById(R.id.edit_entry_out).setVisibility(View.VISIBLE);
                    findViewById(R.id.edit_entry_in).setVisibility(View.VISIBLE);
                    findViewById(R.id.edit_row_id).setVisibility(View.VISIBLE);

                    edit_row_id = (EditText) findViewById(R.id.edit_row_id);
                    edit_note = (EditText) findViewById(R.id.auto_complete_edit_note_text);
                    edit_row_date = (TextView) findViewById(R.id.edit_row_date);

                    id = edit_row_id.getText().toString();

                    long id_l_i = Long.parseLong(id);

                    sql_out_table.open();

                    String retNote = sql_out_table.getNote(id_l_i);

                    String retValue = sql_out_table.getValue(id_l_i);

                    String retDate = sql_out_table.getDate(id_l_i);

                    sql_out_table.close();

                    edit_note.setText(retNote);
                    edit_value.setText(retValue);
                    findViewById(R.id.edit_row_date).setVisibility(View.VISIBLE);
                    edit_row_date.setText(retDate);


                } catch (Exception e) {

                    String error = e.toString();
                    Dialog d2 = new Dialog(this);
                    d2.setTitle("Please Enter An ID Number.");
                    TextView tv2 = new TextView(this);
                    tv2.setText(error);
                    Log.d("get_info_out error", error);
                    d2.show();

                }

                break;


            case R.id.clear_form:

                // Hide and clear all text fields.

                ((EditText) findViewById(R.id.auto_complete_edit_note_text)).setText("");

                ((EditText) findViewById(R.id.d_edit_value)).setText("");

                ((EditText) findViewById(R.id.edit_row_id)).setText("");

                ((TextView) findViewById(R.id.edit_row_date)).setText("");

                findViewById(R.id.edit_row_date).setVisibility(View.GONE);

                findViewById(R.id.edit_row_id).setVisibility(View.INVISIBLE);

                findViewById(R.id.edit_entry_out).setVisibility(View.GONE);

                findViewById(R.id.edit_entry_in).setVisibility(View.GONE);

                break;

            case R.id.plus_set_date:

                try {


        /*
                    String temp = date_tv.replace("-", " ");
                    Log.d("Out btn temp" + temp, temp);
                    String temp_m = temp.substring(0, 2);
                    String temp_d = temp.substring(3, 5);
                    String temp_y = temp.substring(6, 10);

         */

                    year = Integer.parseInt(date_tv.substring(6, 10));
                    month = Integer.parseInt(date_tv.substring(0, 2));
                    day = Integer.parseInt(date_tv.substring(3, 5));


                    current_date = DateTime.forDateOnly(year, month, day);

                    plus_date = current_date.plusDays(1);

                    date_tv = plus_date.format("MM-DD-YYYY");

                    set_date_btn_txt.setText(date_tv);

                } catch (Exception e) {

                    String error = e.toString();

                    Dialog d2 = new Dialog(this);

                    d2.setTitle("Please Enter An ID Number.");

                    TextView tv2 = new TextView(this);

                    tv2.setText(error);

                    Log.d("get_info_out error", error);

                    d2.show();
                }

                break;

            case R.id.minus_set_date:
                try {

                    year = Integer.parseInt(date_tv.substring(6, 10));
                    month = Integer.parseInt(date_tv.substring(0, 2));
                    day = Integer.parseInt(date_tv.substring(3, 5));

                    current_date = DateTime.forDateOnly(year, month, day);

                    plus_date = current_date.minusDays(1);

                    date_tv = plus_date.format("MM-DD-YYYY");

                    set_date_btn_txt.setText(date_tv);


                } catch (Exception e) {
                    String error = e.toString();
                    Dialog d2 = new Dialog(this);
                    d2.setTitle("Please Enter An ID Number.");
                    TextView tv2 = new TextView(this);
                    tv2.setText(error);
                    Log.d("get_info_out error", error);
                    d2.show();
                }

                break;
        }
    }


    public void updateAutoText() {
        try {

            sql_in_table.open();
            sql_out_table.open();

            Set<String> retNoteIn = sql_in_table.getAllNotesIn();
            Set<String> retNoteOut = sql_out_table.getAllNotesOut();

            retNoteOut.addAll(retNoteIn);

            String[] concatedArgs = retNoteOut.toArray(new String[retNoteOut
                    .size()]);

            sql_in_table.close();
            sql_out_table.close();

            AutoCompleteTextView autoTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_edit_note_text);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.autocomplete_list, concatedArgs);
            autoTextView.setAdapter(adapter);

        } catch (Exception e) {
            String error = e.toString();
            Log.d("updateAutoText error", error);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);

        }


        /*
                    Date is going in as MM-DD-YYYY

                    Redo the database with YYYY-MM-DD format for sorting.

                    Then stop using the string grabs and just use a format.

         */
        @Override
        public void onDateSet(android.widget.DatePicker view, int year,
                              int month, int day) {
            ++month;

            if (month == 10 || month == 11 || month == 12) {

                m = String.valueOf(month);

                Log.d("DP month1", String.valueOf(month));
            } else {

                // add a zero in front.
                m = "0".concat(String.valueOf(month));

                Log.d("DP month2", String.valueOf(m));
            }
            if (day > 0 && day < 10) {

                d = "0".concat(String.valueOf(day));

                Log.d("DP month1", String.valueOf(d));

            } else if (day > 9) {

                d = String.valueOf(day);

                Log.d("DP month2", String.valueOf(d));

            }

            y = String.valueOf(year);

            Log.d("DP year", String.valueOf(y));

            // Format MM-DD-YYYY
            date_tv = m.concat("-");
            date_tv = date_tv.concat(d);
            date_tv = date_tv.concat("-");
            date_tv = date_tv.concat(y);


            Log.d("DP date_tv", date_tv);
            Log.e("DP date_tv", date_tv);
            Log.d("DP date_tv", date_tv);
            Log.e("DP date_tv", date_tv);
            Log.d("DP date_tv", date_tv);


            set_date_btn_txt.setText(date_tv);


            // Format YYYY-MM-DD
            date_for_edit = y.concat("-");
            date_for_edit = date_for_edit.concat(m);
            date_for_edit = date_for_edit.concat("-");
            date_for_edit = date_for_edit.concat(d);

            set_date_txt_edit.setText(date_for_edit);

        }
    }


    public void showDatePickerDialog(View v) {


        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "datePicker");


    }
}