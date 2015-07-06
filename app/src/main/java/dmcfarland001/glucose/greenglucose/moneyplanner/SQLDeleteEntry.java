package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Daniel on 5/21/2015.
 */

public class SQLDeleteEntry extends Activity implements OnClickListener {

    // Initialize variable for IN table
    SQLMoneyIn sqlInTable;

    // Initialize variable for OUT table
    SQLMoneyOut sqlOutTable;


    String[] sorted_min_note, sorted_min_date, sorted_min_id;
    String[] sorted_min_note_out, sorted_min_date_out, sorted_min_id_out;

    String ident_string = "", min_sorted_min_note = "", min_sorted_min_date = "", min_sorted_min_id = "";

    String min_sorted_min_note_out = "", min_sorted_min_date_out = "", min_sorted_min_id_out = "";

    Button myButton, hi_btn;

    View view;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Set content view to corresponding XML layout.
        setContentView(R.layout.sql_pop_values);

        // Assign the IN table to a variable.
        sqlInTable = new SQLMoneyIn(this);

        // Assign the OUT table to a variable.
        sqlOutTable = new SQLMoneyOut(this);

        LinearLayout delete_left_lay_in, delete_right_lay_out;

        delete_left_lay_in = (LinearLayout) findViewById(R.id.delete_pop_left_in_lay);

        delete_right_lay_out = (LinearLayout) findViewById(R.id.delete_pop_right_out_lay);


        try {

            // Only grabbing id's and notes in a minimized list.
            // Click to delete last entry.
            // Popup saying Deleted Last Entry.

            sqlInTable.open();


            sqlInTable.set_minviewpoplastdate();


            sorted_min_note = sqlInTable.sorted_min_note();

            sorted_min_date = sqlInTable.sorted_min_date();

            sorted_min_id = sqlInTable.sorted_min_id();


            for (int ii = 0; ii < sorted_min_note.length; ii++) {


                min_sorted_min_note += sorted_min_note[ii] + "\n";

                min_sorted_min_date += sorted_min_date[ii] + "\n";

                min_sorted_min_id += sorted_min_id[ii] + "\n";


            }


            sqlInTable.close();


            sqlOutTable.open();


            sqlOutTable.set_minviewpoplastdate();


            sorted_min_note_out = sqlOutTable.sorted_min_note();

            sorted_min_date_out = sqlOutTable.sorted_min_date();

            sorted_min_id_out = sqlOutTable.sorted_min_id();


            for (int ii = 0; ii < sorted_min_note_out.length; ii++) {


                min_sorted_min_note_out += sorted_min_note_out[ii] + "\n";

                min_sorted_min_date_out += sorted_min_date_out[ii] + "\n";

                min_sorted_min_id_out += sorted_min_id_out[ii] + "\n";


            }


            sqlOutTable.close();

        } catch (Exception e) {

            String error = e.toString();

            Log.d("SQLDeleteEntry error", error);

        }

        String temp = "", temp_y = "", temp_m = "", temp_d = "";

        try {

            for (int i = 0; i < sorted_min_note.length; i++) {

                myButton = new Button(this);

                temp = sorted_min_date[i].replace("-", " ");

                temp_y = temp.substring(0, 4);
                temp_m = temp.substring(5, 7);
                temp_d = temp.substring(8, 10);

                temp = "";

                temp = temp_m.concat("-");
                temp = temp.concat(temp_d);
                temp = temp.concat("-");
                temp = temp.concat(temp_y);


                myButton.setText(temp + " " + sorted_min_note[i]);

                myButton.setTag(sorted_min_note[i]);

                myButton.setId(i + 1);

                myButton.setPadding(10, 10, 10, 10);

                delete_left_lay_in.addView(myButton);

                myButton.setOnClickListener(this);

            }

            for (int i = 0; i < sorted_min_note_out.length; i++) {

                myButton = new Button(this);

                temp = sorted_min_date_out[i].replace("-", " ");

                temp_y = temp.substring(0, 4);
                temp_m = temp.substring(5, 7);
                temp_d = temp.substring(8, 10);

                temp = "";

                temp = temp_m.concat("-");
                temp = temp.concat(temp_d);
                temp = temp.concat("-");
                temp = temp.concat(temp_y);


                myButton.setText(temp +  " " + sorted_min_note_out[i]);

                myButton.setTag(sorted_min_note_out[i]);

                myButton.setId(i + 1);

                myButton.setPadding(10, 10, 10, 10);

                delete_right_lay_out.addView(myButton);

                myButton.setOnClickListener(this);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Intent intent;

    public void onClick(View v) {

        ident_string = String.valueOf(v.getTag());

        for (int y = 0; y < sorted_min_note.length; y++) {

            // Compare the note
            if (ident_string.matches(sorted_min_note[y])) {

                sqlInTable.open();

                // delete according to id.
                sqlInTable.DeleteEntry(Long.valueOf(sorted_min_id[y]));


                sqlInTable.close();

            }

        }
        for (int y = 0; y < sorted_min_note_out.length; y++) {

            // Compare the note
            if (ident_string.matches(sorted_min_note_out[y])) {

                sqlOutTable.open();

                // delete according to id.
                sqlOutTable.DeleteEntry(Long.valueOf(sorted_min_id_out[y]));


                sqlOutTable.close();

            }

        }

        // Send to main to prevent over deletion.
        intent = new Intent(this, MainActivity.class);

        startActivity(intent);


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

}