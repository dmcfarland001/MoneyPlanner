package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends Activity {

    double BASE_cntr = 000.00, MIN_cntr = 000.00;


    /**
     * Tasks
     * <p>
     * Add a login page {[ Hello, "saved user name" ] [Pin] [New User] }
     * <p>
     * Add a backup database page
     * <p>
     * attach an info page. button to raw sql layout. Detail about editing Each
     * Btn
     * <p>
     * redo the database with YYYY-MM-DD format for sorting. Involvs going
     * through and making sure to reformat before printing Remember "format"
     * "DateTime".format("MM-DD-YYYY"); converts DT to string.
     * <p>
     * Color coordinate each value drawn to graph then list them with their
     * notes in a key. draw text Finish the linear dates, values to plot line.
     * <p>
     * Reformatt how data is computed. Compute in back page where no extentions
     * are made. open sql & work.
     * <p>
     * Loading wait. Add visuals.
     * <p>
     * Add the numbers # buttn to show numbers for editing. Layout its own.
     * <p>
     * Create icons. Desktop icon, btn bar icons.
     * <p>
     * Sell for $3.87
     */


    TextView secret_button_ssshhhhh_whisper;

    double base, min;


    DecimalFormat df = new DecimalFormat("#.##");

    String str_base, str_min;

    TickerSQLTable tickerSQLTable;

    LinearLayout secret_footer_top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        secret_footer_top = (LinearLayout) findViewById(R.id.secret_footer_top);

        secret_button_ssshhhhh_whisper = (TextView) findViewById(R.id.secret_button_ssshhhhh_whisper);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            str_base = extras.getString("BASE_cntr");

            str_min = extras.getString("working_values_diff_per_minute");

            base = Double.valueOf(str_base);

            min = Double.valueOf(str_min);

            //secret_footer_top.setVisibility(View.VISIBLE);

            // set to BASE_cntr
            secret_button_ssshhhhh_whisper.setText(str_base);

           // ticker_update();

        }


    }

/*
    public void ticker_update() {

        // This loop will run for 2:00 minutes. Hopefully no complications will happen like sticking.
        for (int i = 0; i < 12; i++) {

            SystemClock.sleep(10000);

            base += min * 10;

            str_base = df.format(base);

            secret_button_ssshhhhh_whisper.setText(str_base);

        }

    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    public void setBASE_cntr(double base_cntr, double minute_counter) {


        BASE_cntr = base_cntr;

        MIN_cntr = minute_counter;


        // Set this bool to true which means the text is set and can be returned.


    }


    Intent intent2, intent3, intent4, intent5, intent6, intent7, intent8, intent9;

    public void OnClick(final View view) {

        switch (view.getId()) {

            case R.id.enter_cal:
                intent2 = new Intent(this, DatePicker.class);
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
                }, 100);

                break;

            case R.id.view_in:
                intent4 = new Intent(this, SQLMinViewIn.class);
                startActivity(intent4);
                break;

            case R.id.view_out:
                intent5 = new Intent(this, SQLMinViewOut.class);
                startActivity(intent5);
                break;

            case R.id.about_page:
                intent6 = new Intent(this, AboutPage.class);
                startActivity(intent6);
                break;

            case R.id.linear_table:

                setContentView(R.layout.splash);

                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent8 = new Intent(view.getContext(), LinearList.class);
                        startActivityForResult(intent8, 0);
                    }
                }, 100);

                break;

            case R.id.hour_glass:
                intent7 = new Intent(this, AboutPage.class);
                startActivity(intent7);
                break;

            case R.id.manage_notes:
                intent9 = new Intent(this, SQLDeleteEntry.class);
                startActivity(intent9);
                break;
        }
    }

}
