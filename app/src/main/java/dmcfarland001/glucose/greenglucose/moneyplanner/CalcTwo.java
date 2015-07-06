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

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class CalcTwo {

	/*
     * *
	 * 
	 * Color coordinate each value drawn to graph then list them with their
	 * notes in a key.
	 *
	 * Draw text Finish the linear dates, values to plot line.
	 * 
	 * Add a login page
	 *
	 * Add a backup database page
	 * 
	 *
	 *
	 * Polish this baby.
	 *
	 *
	 */

    DecimalFormat df = new DecimalFormat("#.##");

    float tempFlo = 0;
    SQLMoneyIn sql_in_table;
    SQLMoneyOut sql_out_table;

    int[] mean_in_date;
    int[] mean_out_date;
    int pl = 0;

    // a mean value to keep a mean value relevant to a set values
    float[] mean_in_value;
    float[] mean_out_value;

    // a last date value to keep a mean value relevant to a set values
    String[] last_dates_in;
    String[] last_dates_out;

    // a set for notes in and out which might trump top method.
    // Initiate a String array with no set size yet.
    String[] c_set_notes_in;
    String[] c_set_notes_out;

    String[] GraphLinearValues;

    int value_to_divide_cntr = 0;
    int year, month, day;

    String[] future_date_in;
    String[] future_date_out;
    String[] future_value_in;
    String[] future_value_out;

    /**
     * The future and set notes line up so when showing pull future IN with set
     * IN and work.
     */

    DateTime current_date, next_date, month_day_year_datetime_cntr;

    DateTime now = DateTime.now(TimeZone.getDefault());

    String month_day_year_cntr;

    // Mean_in_value array. Sets an 'set' of values to each average corresponding.
    public float[] getMeanValues_IN(String[] set_notes_in,
                                    String[] grouped_ordered_notes_in,
                                    String[] grouped_ordered_values_in) {

        pl = 0;

        mean_in_value = new float[set_notes_in.length];

        // a String[] from the set
        // So no doubles.
        // For notes in.
        // Set the size of the String Array
        c_set_notes_in = new String[set_notes_in.length];

        // Set the values of the String Array. No Doubles.
        c_set_notes_in = set_notes_in;

        // Initiate a value for mean values.
        float mean_values = 0;


        // This is to calculate the Mean IN value.
        // Compares a note and if they match add them to a max. Divide.

        try {

            String er = null;

            // mddi is the i counter.
            for (int mddi = 0; mddi < set_notes_in.length; mddi++) {

                // Grouped ordered notes is just a ordered list Sorted of the notes.
                for (int j = 0; j < grouped_ordered_notes_in.length; j++) {

                    // This is where we check, while going through a FULL list
                    // Check if it matches the ONE set value.
                    // Set a counter and iff they match count. check for a one value.
                    // make it a two layer system.
                    // Gets the average in one pass for a set of notes.
                    if (grouped_ordered_notes_in[j].matches(set_notes_in[mddi])) {

                        mean_values += Float
                                .valueOf(grouped_ordered_values_in[j]);

                        value_to_divide_cntr++;


                        er = String.valueOf(value_to_divide_cntr);
                        Log.d("value_to_divide_cntr IN", "div cntr   " + er);
                        Log.d("grped_ord_notes_in", "note   " + grouped_ordered_notes_in[j]);


                        pl = j;

                    }
                }

                // This sets up mean values for IN
                // The zero check is for when they
                // This should never == 0
                if (0 == value_to_divide_cntr) {
                    er = String.valueOf(value_to_divide_cntr);
                    Log.d("val_to_divi_ctr == 0", " IN " + er);
                }

                if (0 < value_to_divide_cntr) {

                    // Divide the added up mean_values by the counted value_to_divide_cntr.
                    tempFlo = mean_values / value_to_divide_cntr;

                    // tempFlo is the average value. Store it in a float array mean_in_value
                    mean_in_value[mddi] = Float.valueOf(df.format(tempFlo));

                    // This will run when only one
                } else {

                    // never should run.
                    er = String.valueOf(mddi);
                    Log.d("Mean IN else", "counter   " + er);

                    // Get the place in the array where the == occured.
                    //
                    mean_in_value[mddi] = Float
                            .valueOf(grouped_ordered_values_in[pl]);
                }

                mean_values = 0;
                value_to_divide_cntr = 0;
                pl = 0;
                tempFlo = 0;
            }

            value_to_divide_cntr = 0;
            mean_values = 0;
            pl = 0;
            tempFlo = 0;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error1  ", error);
        }

        return mean_in_value;
    }

    /**
     * Mean Values OUT
     * <p/>
     * Returns mean_out_value
     * Array 'Set' of the average out values.
     */

    public float[] getMeanValues_OUT(String[] set_notes_out,
                                     String[] grouped_ordered_notes_out,
                                     String[] grouped_ordered_values_out) {

        pl = 0;
        tempFlo = 0;
        // GROUPED NOTES SORTED DATES
        // use SET notes to go through and while NOTE == NOTE
        // DAYS DIFF += then divide by occurrences.

        mean_out_value = new float[set_notes_out.length];

        c_set_notes_out = new String[set_notes_out.length];
        c_set_notes_out = set_notes_out;

        float mean_values = 0;

        // OUT
        try {

            String er = null;

            for (int mddo = 0; mddo < set_notes_out.length; mddo++) {

                for (int j = 0; j < grouped_ordered_notes_out.length; j++) {

                    if (grouped_ordered_notes_out[j]
                            .matches(set_notes_out[mddo])) {

                        mean_values += Float
                                .valueOf(grouped_ordered_values_out[j]);
                        value_to_divide_cntr++;

                        er = String.valueOf(value_to_divide_cntr);
                        Log.d("value_to_div_cntr out", "div cntr   " + er);

                        Log.d("grped_ord_val_out", "  " + grouped_ordered_values_out[j]);
                        Log.d("grped_ord_no_out", "  " + grouped_ordered_notes_out[j]);

                        pl = j;

                    }
                }

                if (0 == value_to_divide_cntr) {
                    er = String.valueOf(value_to_divide_cntr);
                    Log.d("val_to_divi_ctr == 0", " OUT " + er);
                }
                // This sets up both mean dates and mean values. for OUT
                if (value_to_divide_cntr > 0) {

                    tempFlo = mean_values / value_to_divide_cntr;
                    mean_out_value[mddo] = Float.valueOf(df.format(tempFlo));

                } else { // If the there is only one entry

                    er = String.valueOf(grouped_ordered_values_out[pl]);
                    Log.d("Out else", "   " + er);

                    mean_out_value[mddo] = Float
                            .valueOf(grouped_ordered_values_out[pl]);
                }

                mean_values = 0;
                value_to_divide_cntr = 0;
                pl = 0;
                tempFlo = 0;
            }

            value_to_divide_cntr = 0;
            mean_values = 0;
            pl = 0;
            tempFlo = 0;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error2  ", error);
        }

        return mean_out_value;
    }

    /**
     * Mean Dates IN
     * <p/>
     * Returns mean_in_date.
     */
    public int[] getMeanDays_IN(String[] set_notes_in,
                                String[] grouped_ordered_notes_in, String[] grouped_ordered_dates_in) {

        int mean_days = 0, placeHolder = 0;

        mean_in_date = new int[set_notes_in.length];

        last_dates_in = new String[set_notes_in.length];

        try {

            String er = null;

            // for loop less than the size of the set.
            for (int mddi = 0; mddi < set_notes_in.length; mddi++) {

                // for loop less than the full array length.
                for (int j = 0; j < grouped_ordered_notes_in.length; j++) {

                    // if full note matches set note
                    if (grouped_ordered_notes_in[j].matches(set_notes_in[mddi])) {

                        // Grab from the full dates array.
                        String cur_day = grouped_ordered_dates_in[j].substring(
                                0, 10);
                        String curr_day_rem = cur_day.replace("-", " ");
                        // Set current date and next date
                        // YYYY-MM-DD to YYYY-MM-DD

                        year = Integer.parseInt(curr_day_rem.substring(0, 4));
                        month = Integer.parseInt(curr_day_rem.substring(5, 7));
                        day = Integer.parseInt(curr_day_rem.substring(8, 10));
                        current_date = DateTime.forDateOnly(year, month, day);

                        // The + 1 is to check for end of array.
                        if (1 + j < grouped_ordered_notes_in.length) {

                            // Check if they match. If true go through.
                            if (grouped_ordered_notes_in[1 + j].matches(set_notes_in[mddi])) {


                                String ne_day = grouped_ordered_dates_in[1 + j]
                                        .substring(0, 10);
                                String next_day_rem = ne_day.replace("-", " ");

                                // Set next date and next date
                                year = Integer.parseInt(next_day_rem.substring(
                                        0, 4));
                                month = Integer.parseInt(next_day_rem
                                        .substring(5, 7));
                                day = Integer.parseInt(next_day_rem.substring(
                                        8, 10));

                                next_date = DateTime.forDateOnly(year, month,
                                        day);

                                mean_days += current_date
                                        .numDaysFrom(next_date);

                                value_to_divide_cntr++;

                            }
                        }

                        placeHolder = j;
                    }
                    // This is only for values of multiple.
                    if (value_to_divide_cntr > 0) {

                        mean_in_date[mddi] = mean_days / value_to_divide_cntr;

                        er = String.valueOf(mean_in_date[mddi]);
                        Log.d("mean_in_date[mddi] IN", "IN   " + er);

                    } else

                        // This will run now with j + 1 the next check equal.
                        mean_in_date[mddi] = 0;

                    last_dates_in[mddi] = grouped_ordered_dates_in[placeHolder];
                }

                // After inner most for loop
                placeHolder = 0;
                mean_days = 0;
                value_to_divide_cntr = 0;

            }

            value_to_divide_cntr = 0;
            mean_days = 0;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error3", error);
        }

        return mean_in_date;
    }

    /**
     * Mean Dates OUT
     * <p/>
     * Returns mean_out_date
     */
    public int[] getMeanDays_OUT(String[] set_notes_out,
                                 String[] grouped_ordered_notes_out,
                                 String[] grouped_ordered_dates_out) {

        for (int h = 0; h < grouped_ordered_dates_out.length; h++) {
            Log.d("GORO ", grouped_ordered_dates_out[h]);
        }

        value_to_divide_cntr = 0;

        int mean_days = 0, placeHolder = 0;

        mean_out_date = new int[set_notes_out.length];

        last_dates_out = new String[set_notes_out.length];

        Log.e("getMeanDays_OUT", "Enter");

        try {

            String er = null;

            for (int mddo = 0; mddo < set_notes_out.length; mddo++) {

                for (int j = 0; j < grouped_ordered_notes_out.length; j++) {

                    Log.e("get_OUT grdt[j]", grouped_ordered_notes_out[j]);
                    Log.e("getMeanDays_OUT", set_notes_out[mddo]);

                    if (grouped_ordered_notes_out[j]
                            .matches(set_notes_out[mddo])) {

                        String cur_day = grouped_ordered_dates_out[j]
                                .substring(0, 10);
                        String curr_day_rem = cur_day.replace("-", " ");

                        Log.e("getMeay_rem", curr_day_rem);

                        // Set current date and next date
                        // MM-DD-YYYY to YYYY-MM-DD
                        year = Integer.parseInt(curr_day_rem.substring(0, 4));
                        Log.e("getMeanDays_OUT year", String.valueOf(year));
                        month = Integer.parseInt(curr_day_rem.substring(5, 7));
                        Log.e("getMeanDays_OUT month", String.valueOf(month));
                        day = Integer.parseInt(curr_day_rem.substring(8, 10));
                        Log.e("getMeanDays_OUT day", String.valueOf(day));

                        current_date = DateTime.forDateOnly(year, month, day);


                        if (1 + j < grouped_ordered_notes_out.length) {


                            if (grouped_ordered_notes_out[1 + j]
                                    .matches(set_notes_out[mddo])) {

                                String ne_day = grouped_ordered_dates_out[1 + j]
                                        .substring(0, 10);
                                String next_day_rem = ne_day.replace("-", " ");

                                Log.e("getMeanday_rem", next_day_rem);

                                // Set next date and next date
                                year = Integer.parseInt(next_day_rem.substring(
                                        0, 4));
                                month = Integer.parseInt(next_day_rem
                                        .substring(5, 7));
                                day = Integer.parseInt(next_day_rem.substring(
                                        8, 10));

                                next_date = DateTime.forDateOnly(year, month,
                                        day);

                                mean_days += current_date
                                        .numDaysFrom(next_date);

                                value_to_divide_cntr++;

                            }
                        }

                        placeHolder = j;
                    }

                    // This sets up both mean dates and mean values. for OUT
                    if (value_to_divide_cntr > 0) {
                        mean_out_date[mddo] = mean_days / value_to_divide_cntr;

                        er = String.valueOf(mean_out_date[mddo]);
                        Log.d("mean_out_date[mddo] OUT", "OUT   " + er);

                    } else
                        mean_out_date[mddo] = 0;

                    last_dates_out[mddo] = grouped_ordered_dates_out[placeHolder];
                }
                placeHolder = 0;
                mean_days = 0;
                value_to_divide_cntr = 0;
            }

            value_to_divide_cntr = 0;
            mean_days = 0;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error4", error);
        }

        return mean_out_date;
    }

    public String[] futureDates_IN(int[] mean_dates_in) {

        future_date_in = new String[last_dates_in.length];

        try {

            for (int cntr = 0; cntr < last_dates_in.length; cntr++) {

                String cur_day = last_dates_in[cntr].substring(0, 10);
                String curr_day_rem = cur_day.replace("-", " ");

                // Set current date and next date
                // MM-DD-YYYY to YYYY-MM-DD
                year = Integer.parseInt(curr_day_rem.substring(0, 4));
                month = Integer.parseInt(curr_day_rem.substring(5, 7));
                day = Integer.parseInt(curr_day_rem.substring(8, 10));

                current_date = DateTime.forDateOnly(year, month, day);

                next_date = current_date.plusDays(mean_dates_in[cntr]);

                future_date_in[cntr] = next_date.format("YYYY-MM-DD");

            }

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error5", error);
        }

        return future_date_in;
    }

    public String[] futureDates_OUT(int[] mean_dates_out) {

        future_date_out = new String[last_dates_out.length];

        try {

            for (int cntr = 0; cntr < last_dates_out.length; cntr++) {

                String cur_day = last_dates_out[cntr].substring(0, 10);

                String curr_day_rem = cur_day.replace("-", " ");

                // Set current date and next date
                // MM-DD-YYYY to YYYY-MM-DD

                year = Integer.parseInt(curr_day_rem.substring(0, 4));
                month = Integer.parseInt(curr_day_rem.substring(5, 7));
                day = Integer.parseInt(curr_day_rem.substring(8, 10));

                current_date = DateTime.forDateOnly(year, month, day);

                next_date = current_date.plusDays(mean_dates_out[cntr]);

                future_date_out[cntr] = next_date.format("YYYY-MM-DD");
            }

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error6", error);
        }

        return future_date_out;
    }

    public float[] GraphPastVals_IN(String[] grouped_ordered_values_in,
                                    String[] grouped_ordered_dates_in, String[] no_duplicates_dates_in) {

        // going through this ordered full list you miss adding duplicates on
        // same days.
        // Set up a new array -> graph_past_vals with an assigned sorted no
        // duplicates.

        // Call SQL to get a group by created at which will remove
        // duplicates.
        // run an inner j loop with the ordered_d_o array to form a
        // graph_past_vals array.
        // Do a raw add then reset to get a total for the day.

        float[] graph_values = new float[no_duplicates_dates_in.length];

        float addr = 0;
        int ticker = 0;

        try {

            for (int i = 0; i < no_duplicates_dates_in.length; i++) {

                for (int j = 0; j < grouped_ordered_dates_in.length; j++) {


                    if (grouped_ordered_dates_in[j]
                            .matches(no_duplicates_dates_in[i])) {

                        addr += Float.valueOf(grouped_ordered_values_in[j]);

                        ticker++;

                    }
                }

                graph_values[i] = addr / ticker;
                addr = 0;
                ticker = 0;
            }

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error7", error);
        }

        return graph_values;
    }

    public float[] GraphLinearValues(String[] grouped_ordered_values_out,
                                     String[] grouped_ordered_dates_out, String[] no_duplicates_dates_out) {


        float[] graph_values = new float[no_duplicates_dates_out.length];

        float addr = 0;
        int ticker = 0;

        try {

            for (int i = 0; i < no_duplicates_dates_out.length; i++) {


                for (int j = 0; j < grouped_ordered_dates_out.length; j++) {


                    if (grouped_ordered_dates_out[j]
                            .matches(no_duplicates_dates_out[i])) {


                        addr += Float.valueOf(grouped_ordered_values_out[j]);

                        ticker++;
                    }
                }

                graph_values[i] = addr / ticker;

                addr = 0;

                ticker = 0;
            }

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error8", error);
        }

        return graph_values;
    }

    ArrayList<String> set_dates = new ArrayList<String>();
    ArrayList<String> set_values = new ArrayList<String>();
    ArrayList<String> set_notes = new ArrayList<String>();
    ArrayList<String> set_complete_linear_list = new ArrayList<String>();
    ArrayList<String> set_complete_linear_dates = new ArrayList<String>();

    String[] array_notes;
    String[] array_values;
    String[] array_dates;
    String[] complete_linear_list;
    String[] complete_linear_dates;
    DateTime first_in, first_out;

    float current_val = 0, next_val = 0, funct_val = 0, value_to_add_daily = 0,
            day_value_cntr = 0;

    // no_duplicates_dates_in is lined up to show all dates with no duplicates.
    // Size based on dates not notes.
    // graph_past_vals_in lines up with no_duplicates_dates_in. Size based on
    // dates not notes.
    // future_date_in2 is lined up with present SET in out. Size is dependent on
    // no duplicates ASC order by date and Group By Note.
    // mean_values_in is lined up with present SET in out. Size is dependent on
    // no duplicates ASC order by date and Group By Note.

    /**
     * @param no_duplicates_dates_in
     * @param no_duplicates_dates_out
     * @param graph_past_vals_in
     * @param no_duplicates_notes_in
     * @param no_duplicates_notes_out
     * @param graph_past_vals_out
     * @param future_date_in2
     * @param future_date_out2
     * @param mean_values_in
     * @param mean_values_out
     * @return
     */

    public String[] GraphLinearPlots(String[] no_duplicates_dates_in,
                                     String[] no_duplicates_dates_out, float[] graph_past_vals_in,
                                     String[] no_duplicates_notes_in, String[] no_duplicates_notes_out,
                                     float[] graph_past_vals_out, String[] future_date_in2,
                                     String[] future_date_out2, float[] mean_values_in,
                                     float[] mean_values_out) {


        int in_days, out_days, day_cntr = -1;

        DateTime start_from;

        boolean stopper = true;

        int trueStopper = 0;

        float variable = 0;

        String in_day = no_duplicates_dates_in[0].substring(0, 10);
        String curr_day_rem = in_day.replace("-", " ");

        String out_day = no_duplicates_dates_out[0].substring(0, 10);
        String nxt_day_rem = out_day.replace("-", " ");

        // Set current date and next date
        // MM-DD-YYYY to YYYY-MM-DD
        year = Integer.parseInt(curr_day_rem.substring(0, 4));
        month = Integer.parseInt(curr_day_rem.substring(5, 7));
        day = Integer.parseInt(curr_day_rem.substring(8, 10));

        first_in = DateTime.forDateOnly(year, month, day);

        year = Integer.parseInt(nxt_day_rem.substring(0, 4));
        month = Integer.parseInt(nxt_day_rem.substring(5, 7));
        day = Integer.parseInt(nxt_day_rem.substring(8, 10));

        first_out = DateTime.forDateOnly(year, month, day);

        in_days = first_in.numDaysFrom(now);
        out_days = first_out.numDaysFrom(now);

        if (in_days > out_days)
            start_from = first_in;
        else
            start_from = first_out;

        // Log.d("start_from", start_from.format("MM-DD-YYYY"));
        try {

            while (stopper) {

                month_day_year_datetime_cntr = start_from.plusDays(++day_cntr);

                month_day_year_cntr = month_day_year_datetime_cntr
                        .format("YYYY-MM-DD");

                // Log.d("month_day_year_cntr", month_day_year_cntr);

                for (int y = 0; y < no_duplicates_dates_in.length; y++) {

                    // Log.d("no_duplicates_dates_in",
                    // no_duplicates_dates_in[y]);
                    if (no_duplicates_dates_in[y].matches(month_day_year_cntr)) {

                        variable += graph_past_vals_in[y];

                        set_values.add(String.valueOf(variable));

                        set_dates
                                .add(String.valueOf(no_duplicates_dates_in[y]));
                        set_notes
                                .add(String.valueOf(no_duplicates_notes_in[y]));

                        trueStopper = 0;
                    }

                }
                // Log.d("1 trueStopper", String.valueOf(trueStopper));

                for (int y = 0; y < no_duplicates_dates_out.length; y++) {

                    // Log.d("no_duplicates_dates_out",
                    // no_duplicates_dates_out[y]);
                    if (no_duplicates_dates_out[y].matches(month_day_year_cntr)) {

                        variable -= graph_past_vals_out[y];

                        set_values.add(String.valueOf(variable));


                        set_dates.add(String
                                .valueOf(no_duplicates_dates_out[y]));


                        set_notes.add(String
                                .valueOf(no_duplicates_notes_out[y]));

                        trueStopper = 0;
                    }
                }

                // Log.d("2 trueStopper", String.valueOf(trueStopper));

                for (int y = 0; y < future_date_in.length; y++) {

                    // Log.d("future_date_in", future_date_in[y]);

                    if (future_date_in[y].matches(month_day_year_cntr)) {


                        variable += mean_values_in[y];

                        set_values.add(String.valueOf(variable));

                        set_dates.add(String.valueOf(future_date_in[y]));

                        set_notes.add(String.valueOf(c_set_notes_in[y]));

                        trueStopper = 0;
                    }

                }

                // Log.d("3 trueStopper", String.valueOf(trueStopper));

                for (int y = 0; y < future_date_out.length; y++) {

                    // Log.d("future_date_out", future_date_out[y]);

                    if (future_date_out[y].matches(month_day_year_cntr)) {

                        variable -= mean_values_out[y];

                        set_values.add(String.valueOf(variable));

                        set_dates.add(String.valueOf(future_date_out[y]));

                        set_notes.add(String.valueOf(c_set_notes_out[y]));

                        trueStopper = 0;
                    }

                }

                trueStopper++;

                // Log.d("Bottom trueStopper", String.valueOf(trueStopper));

                /**
                 * subtract the days from the future days and see if greater than 0 days from.
                 *
                 *
                 */
                if (trueStopper > 80)
                    stopper = false;

                // Log.d("Bottom stopper", String.valueOf(stopper));

            }

            array_values = new String[set_values.size()];

            array_values = set_values.toArray(new String[set_values.size()]);

            array_dates = new String[set_dates.size()];

            array_dates = set_dates.toArray(new String[set_dates.size()]);

            // Remember that this is its own free existing list. Size is large.
            array_notes = new String[set_notes.size()];

            array_notes = set_notes.toArray(new String[set_notes.size()]);

            /**
             * Get datetime use current_date and next_date int days from float
             * value_difference <- Math.abs to get value to add or subtract.
             * value_difference / days_from check if next is greater or less
             * than current to ( + or - ) value add value to new
             * linear_day_array
             *
             */

            stopper = true;

            day_cntr = -1;

            int stopper_cntr = 0;

            if (array_dates.length > 0) {

                year = Integer.parseInt(array_dates[0].substring(0, 4));
                month = Integer.parseInt(array_dates[0].substring(5, 7));
                day = Integer.parseInt(array_dates[0].substring(8, 10));

                start_from = DateTime.forDateOnly(year, month, day);

                while (stopper) {

                    stopper_cntr++;

                    month_day_year_datetime_cntr = start_from
                            .plusDays(++day_cntr);

                    month_day_year_cntr = month_day_year_datetime_cntr
                            .format("YYYY-MM-DD");

                    // while(true){
                    // set up while loop to count days
                    // current_day

                    for (int l_cn = 0; l_cn < array_values.length - 1; l_cn++) {

                        // if( current_day 'array_dates[l_cn]' ==
                        // current_array_date)

                        // if (array_dates[1 + l_cn] == null) {
                        // stopper = false;
                        // break;
                        // }

                        if (array_dates[l_cn].matches(month_day_year_cntr)) {

                            stopper_cntr = 0;

                            if (array_dates[1 + l_cn] != null) {

                                year = Integer.parseInt(month_day_year_cntr
                                        .substring(0, 4));

                                month = Integer.parseInt(month_day_year_cntr
                                        .substring(5, 7));

                                day = Integer.parseInt(month_day_year_cntr
                                        .substring(8, 10));

                                first_in = DateTime.forDateOnly(year, month,
                                        day);


                                year = Integer.parseInt(array_dates[1 + l_cn]
                                        .substring(0, 4));

                                month = Integer.parseInt(array_dates[1 + l_cn]
                                        .substring(5, 7));

                                day = Integer.parseInt(array_dates[1 + l_cn]
                                        .substring(8, 10));

                                first_out = DateTime.forDateOnly(year, month,
                                        day);

                                // in_days is days difference.
                                // This number is the denominator

                                in_days = first_in.numDaysFrom(first_out);

                                day_value_cntr = Float
                                        .valueOf(array_values[l_cn]);

                                if (in_days > 1) {

                                    // find a numerator with the values

                                    current_val = Math.abs(Float
                                            .valueOf(array_values[l_cn]));

                                    next_val = Math.abs(Float
                                            .valueOf(array_values[1 + l_cn]));

                                    funct_val = Math
                                            .abs(current_val - next_val);

                                    value_to_add_daily = funct_val / in_days;

                                    for (int e = 0; e < in_days; e++) {

                                        // add value after calculations
                                        // set_complete_linear_list.add(object);
                                        // now check for adding or subing

                                        set_complete_linear_list.add(String
                                                .valueOf(day_value_cntr));

                                        if (Float.valueOf(array_values[l_cn]) > Float
                                                .valueOf(array_values[1 + l_cn])) {

                                            // subtract values
                                            day_value_cntr -= value_to_add_daily;

                                        } else {

                                            day_value_cntr += value_to_add_daily;

                                        }

                                        // Add DATE to array.
                                        set_complete_linear_dates
                                                .add(month_day_year_cntr);

                                        month_day_year_datetime_cntr = start_from
                                                .plusDays(++day_cntr);

                                        month_day_year_cntr = month_day_year_datetime_cntr
                                                .format("YYYY-MM-DD");

                                    }

                                    --day_cntr;
                                    break;

                                } else if (in_days == 1) {

                                    set_complete_linear_list.add(String
                                            .valueOf(day_value_cntr));

                                    set_complete_linear_dates
                                            .add(month_day_year_cntr);

                                }
                                // else if you have reached the end of the loop
                                // set while to false

                            }

                        }
                    }
                    if (stopper_cntr > array_values.length) {
                        stopper = false;
                        break;
                    }
                }
            }
            day_cntr = 0;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Calc_two error9", error);
        }
        // set_complete_linear_list = new
        // String[set_complete_linear_list.size()];

        // set. Initiate String Array
        complete_linear_list = new String[set_complete_linear_list.size()];


        // array
        complete_linear_list = set_complete_linear_list
                .toArray(new String[set_complete_linear_list.size()]);

        // set
        complete_linear_dates = new String[set_complete_linear_dates.size()];


        // array
        complete_linear_dates = set_complete_linear_dates
                .toArray(new String[set_complete_linear_dates.size()]);

        // now you have a complete linear list.

        return array_values;
    }

    public String[] GraphLinearDates() {
        return array_dates;
    }

    public String[] GraphLinearNotes() {
        return array_notes;
    }

    public String[] GraphLinearValues() {
        return array_values;
    }

    public String[] CompleteLinearValues() {
        return complete_linear_list;
    }

    public String[] CompleteLinearDates() {
        return complete_linear_dates;
    }

}
