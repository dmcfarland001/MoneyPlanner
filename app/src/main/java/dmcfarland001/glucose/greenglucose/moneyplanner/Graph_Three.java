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

import java.util.TimeZone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Graph_Three extends View {

    /**
     * consider reworking the calculations to line up with how you did the
     * linear equations.
     */

    CalcTwo calc_two = new CalcTwo();
    SQLMoneyIn sql_in_table;
    SQLMoneyOut sql_out_table;
    Paint paint = new Paint();
    Path path = new Path();

    DateTime todays_date, month_day_year_datetime_cntr;
    int day_cntr = -1;

    // use a current date counter to compare past dates IN OUT and future IN OUT
    // dates to.
    String current_date, future_day, future_month, future_month_day;

    // Use this as a counter to add dates.
    String month_day_year_cntr;

    int maxHeight = 0, maxWidth = 0;

    float max_in_value = 0, min_in_value, max_out_value = 0, min_out_value;

    float zeroValue;
    double limitMax = zeroValue - 20;
    double valueIn = 0, valueOut = 0;
    float max_in, max_out;

    double baseMin_in = 0.0;
    double baseMax_in = 0.0;
    double baseMin_out = 0.0;
    double baseMax_out = 0.0;
    final double limitMin = 20.0;

    // Future dates match up with SET notes
    String[] future_date_in;
    String[] future_date_out;

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
    String[] g_complete_linear_list;
    String[] g_complete_linear_dates;

    int past_length_in = 0, past_length_out = 0, future_length_in = 0,
            future_length_out = 0;

    float BaselinMax = 0;

    public Graph_Three(Context context) {

        super(context);

        sql_in_table = new SQLMoneyIn(context);
        sql_out_table = new SQLMoneyOut(context);

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
        grouped_ordered_values_out = sql_out_table.groupNameOrderDateValueA();

        grouped_ordered_dates_in = sql_in_table.groupNameOrderDateDateA();
        grouped_ordered_dates_out = sql_out_table.groupNameOrderDateDateA();

        no_duplicates_dates_in = sql_in_table.NoDuplicatesDates();
        no_duplicates_dates_out = sql_out_table.NoDuplicatesDates();

        no_duplicates_notes_in = sql_in_table.NoDuplicatesDatesNotes();
        no_duplicates_notes_out = sql_out_table.NoDuplicatesDatesNotes();

        sql_in_table.close();
        sql_out_table.close();

        try {

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
            // its
            // on a daily counter. lined up with days.
            graph_past_vals_in = calc_two.GraphPastVals_IN(
                    grouped_ordered_values_in, grouped_ordered_dates_in,
                    no_duplicates_dates_in);

            graph_past_vals_out = calc_two.GraphLinearValues(
                    grouped_ordered_values_out, grouped_ordered_dates_out,
                    no_duplicates_dates_out);


            graph_linear_plots = calc_two.GraphLinearPlots(
                    no_duplicates_dates_in, no_duplicates_dates_out,
                    graph_past_vals_in, no_duplicates_notes_in,
                    no_duplicates_notes_out, graph_past_vals_out,
                    future_date_in, future_date_out, mean_values_in,
                    mean_values_out);

            // Here is the complete list of values and dates.
            g_complete_linear_list = calc_two.CompleteLinearValues();
            g_complete_linear_dates = calc_two.CompleteLinearDates();

            for (int uu = 0; uu < graph_linear_plots.length; uu++) {
                if (Math.abs(Float.valueOf(graph_linear_plots[uu])) > BaselinMax)
                    BaselinMax = Math
                            .abs(Float.valueOf(graph_linear_plots[uu]));
            }

            todays_date = currentDateTime();

            UpdateSS(0);

            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);

            past_length_in = no_duplicates_dates_in.length;
            past_length_out = no_duplicates_dates_out.length;
            future_length_in = future_date_in.length;
            future_length_out = future_date_out.length;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Graph_Three error", error);
        }

    }

    /**
     * mean_values_in
     * mean_values_out
     * graph_past_vals_in
     * graph_past_vals_out
     * no_duplicates_dates_in
     * no_duplicates_dates_out
     * future_length_in
     * future_length_out
     * g_complete_linear_dates
     */

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // canvas.drawBitmap(b, 0, 0, paint);

        boolean first = true;

        zeroValue = maxHeight / 2;
        limitMax = zeroValue - 20;

        try {
            // Main for loop to draw lines and dates.
            // Inner loops will scan through the shown dates and see if a value
            // can be drawn.
            for (int i = 0; i < maxWidth; i += 50) {

                paint.setColor(Color.LTGRAY);
                paint.setStrokeWidth(1);
                canvas.drawLine(i, 0, i, maxHeight, paint);

                // This returns a String of today's date plus a counter.
                // The ON TOUCH will subtract or add to today's_date counter.

                // FORMAT YYYY-MM-DD
                month_day_year_datetime_cntr = todays_date.plusDays(++day_cntr);

                // FORMAT MM-DD-YYYY
                month_day_year_cntr = month_day_year_datetime_cntr
                        .format("YYYY-MM-DD");

                // This will cut off the YEAR and give only MONTH DAY for
                // printing.
                String month_day = month_day_year_cntr.substring(5, 10);
                paint.setColor(Color.DKGRAY);
                paint.setTextSize(14);

                canvas.drawText(month_day, i + 8, maxHeight - 10, paint);
                paint.setStrokeWidth(4);

                for (int all = 0; all < g_complete_linear_dates.length; all++) {

                    // Past in values
                    if (all < past_length_in) {
                        if (no_duplicates_dates_in[all]
                                .matches(month_day_year_cntr)) {

                            valueIn = graph_past_vals_in[all];

                            max_in = (float) Algorithms.scale(valueIn,
                                    baseMin_in, baseMax_in, limitMin, limitMax);

                            // Original is the light mint green
                            paint.setColor(Color.rgb(116, 128, 116));

                            canvas.drawRect(i + 20, zeroValue - max_in, i + 30,
                                    zeroValue, paint);
                        }

                    }
                    // Past out values
                    if (all < past_length_out) {
                        if (no_duplicates_dates_out[all]
                                .matches(month_day_year_cntr)) {

                            valueOut = graph_past_vals_out[all];

                            max_out = (float) Algorithms.scale(valueOut,
                                    baseMin_out, baseMax_out, limitMin,
                                    limitMax);

                            // convert max_out to a string to print as mo
                            String mo = String.valueOf(max_out);

                            Log.d("Graph_Three past", mo);

                            // This is a subtle plum purple. Cloudy day plum. Rain cloud purple/ plum #887D9C
                            paint.setColor(Color.rgb(136, 125, 156));

                            //canvas.drawRect(i + 20, zeroValue + max_out, i + 30,
                            //        zeroValue, paint);
                            canvas.drawRect(i + 20, zeroValue,
                                    i + 30, zeroValue + max_out, paint);
                            //   canvas.drawCircle(i + 20, zeroValue, zeroValue/2, paint);
                        }

                    }

                    // future in
                    if (all < future_length_in) {

                        if (future_date_in[all].matches(month_day_year_cntr)) {

                            valueIn = Double.valueOf(mean_values_in[all]);

                            max_in = (float) Algorithms.scale(valueIn,
                                    baseMin_in, baseMax_in, limitMin, limitMax);

                            // Color is a lush money kelly green.
                            paint.setColor(Color.rgb(19, 118, 71));

                            canvas.drawRect(i + 20, zeroValue - max_in, i + 30,
                                    zeroValue, paint);
                        }
                    }

                    // future out
                    if (all < future_length_out) {
                        if (future_date_out[all].matches(month_day_year_cntr)) {

                            valueOut = Double.valueOf(mean_values_out[all]);

                            max_out = (float) Algorithms.scale(valueOut,
                                    baseMin_out, baseMax_out, limitMin,
                                    limitMax);

                            // convert max_out to a string to print as mo
                            String mo = String.valueOf(max_out);

                            Log.d("Graph_Three Fut", mo);

                            // Color is a vibrant plum money purple.
                            paint.setColor(Color.rgb(163, 120, 192));

                            //  left    top                           right     bottom   paint
                            // i + 20, zeroValue , i + 30, zeroValue "Half y"  + max out, paint
                            canvas.drawRect(i + 20, zeroValue,
                                    i + 30, zeroValue + max_out, paint);

                            //      canvas.drawCircle(i + 20, zeroValue, zeroValue/2, paint);
                        }

                    }

                    if (g_complete_linear_dates[all]
                            .matches(month_day_year_cntr)) {

                        valueOut = Math.abs(Float
                                .valueOf(g_complete_linear_list[all]));

                        Log.d("Canvas Graph value", String.valueOf(valueOut));

                        max_out = (float) Algorithms.scale(valueOut,
                                baseMin_out, BaselinMax, limitMin, limitMax);

                        if (Float.valueOf(g_complete_linear_list[all]) > 0) {
                            if (first) {
                                first = false;
                                path.moveTo(i + 20, zeroValue - max_out);
                            } else {
                                path.lineTo(i + 20, zeroValue - max_out);
                            }

                        } else { // Negative

                            if (first) {
                                first = false;
                                path.moveTo(i + 20, zeroValue + max_out);
                            } else {
                                path.lineTo(i + 20, zeroValue + max_out);
                            }

                        }

                    }
                }

            }
            paint.setColor(Color.rgb(29, 40, 42));
            canvas.drawPath(path, paint);

            path.reset();

            // This is the one middle line.
            paint.setColor(Color.rgb(129, 158, 140));
            canvas.drawLine(0, maxHeight / 2, maxWidth, maxHeight / 2, paint);

            // Reset the day counter
            day_cntr = 0;

        } catch (Exception e) {
            String error = e.toString();
            Log.d("Graph_Three error", error);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float eventX = event.getX();

        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (eventX > (maxWidth / 2)) {
                // go fwd
                todays_date = todays_date.plusDays(6);

            } else {
                // go back
                todays_date = todays_date.minusDays(6);
            }

            // Schedules a repaint.
            invalidate();
        }
        return true;
    }

    public void UpdateSS(float newMax) {

		/*
         * The screen is split at the %50 mark. So your %100 will be subtracted from the %50 mark.
		 * 
		 * Your max_out_value is subtracted from the %50 to %0. So originally that % is positive that needs to be subtracted from the %50.
		 * 
		 * 
		 * 
		 * 
		 */


        // Get the max value for the ceiling for the max_in_value.
        for (int cnt_max = 0; cnt_max < graph_past_vals_in.length; cnt_max++)
            if (Float.valueOf(graph_past_vals_in[cnt_max]) > max_in_value)
                max_in_value = Float.valueOf(graph_past_vals_in[cnt_max]);

        baseMax_in = max_in_value;

        // Get the
        for (int cnt_max = 0; cnt_max < graph_past_vals_out.length; cnt_max++)
            if (Float.valueOf(graph_past_vals_out[cnt_max]) > max_out_value)
                max_out_value = Float.valueOf(graph_past_vals_out[cnt_max]);

        baseMax_out = max_out_value;

        if (baseMax_in > baseMax_out)
            baseMax_out = baseMax_in;
        else
            baseMax_in = baseMax_out;

        if (newMax > baseMax_out || newMax > baseMax_in) {
            baseMax_out = newMax;
            baseMax_in = newMax;
        }

        Log.e("Graph_Two max in", String.valueOf(baseMax_in));
        Log.e("Graph_Two max out", String.valueOf(baseMax_out));

    }

    public static class Algorithms {
        public static double scale(final double valueIn, final double baseMin,
                                   final double baseMax, final double limitMin,
                                   final double limitMax) {
            return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin))
                    + limitMin;
        }
    }

    public int setwidth(int width) {
        maxWidth = width;
        return width;
    }

    public int setheight(int height) {
        maxHeight = height;
        return height;
    }

    /**
     * get date-time
     */
    private DateTime currentDateTime() {
        DateTime now = DateTime.now(TimeZone.getDefault());
        return now;
    }

}
