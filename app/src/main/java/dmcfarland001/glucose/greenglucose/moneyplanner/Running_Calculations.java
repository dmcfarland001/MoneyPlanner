package dmcfarland001.glucose.greenglucose.moneyplanner;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by Daniel on 6/2/2015.
 */
public class Running_Calculations {

    DecimalFormat df = new DecimalFormat("#.##");


    // Grab graph_linear_plots, graph_linear_notes, graph_linear_dates

    // Cycle through until current day matches up or passes saved date.

    // This depends on how the i++ counter runs through the dates.

    // Are the arrays ASC or DESC?

    // Get to when days are different. Get that value.

    // get days from [i] to [i+1]

    // if the same day use 24 hrs.

    // Subtract the stored values at [i] and [i+1]

    // Divide this by 24 ABSOLUTE or can check which is greater to switch the two

    // Absolute for positive.

    // Must know which is greater and which is less to get a counter or a subtracter

    // Subtract values to get a total value

    // divide this value by 24

    // divide this value by 60

    // get current time. Subtract current time from midnight HOURS only

    // ex. 5:00 PM - 12:00 AM == 7 hours

    // Subtract 7 from 24 to get 17.

    // multiply 17 by value divided by 24

    // This is total at top of the hour.


    // Get a current 24 time. Subtract this from end of day
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    // Get a current 24 time. Subtract this from end of day
    String current_24_hr_String = sdf.format(new Date());

    // Get current time.
    DateTime current_YYYY_MM_DD = DateTime.now(TimeZone.getDefault());

    // get a full string range - [0-10][12-19] remove ":"
    String current_time_String = current_YYYY_MM_DD.format("YYYY-MM-DD hh:mm:ss");

    // Current hour can be subtracted from 12. if pm || am
    int current_hour = current_YYYY_MM_DD.getHour();

    // Current minute can be subtracted from 60
    int current_minute = current_YYYY_MM_DD.getMinute();

    // Hours in 24 hour format.
    int hour_24 = Integer.valueOf(current_24_hr_String.substring(0, 2));

    // this will give hours until days over.
    int hours_till_midnight = 24 - hour_24;

    // not sure if this is necessary.
    int minutes_till_60 = 60 - current_minute;


    String[] BASE_and_min_values = new String[2];


    DateTime dateAndTime_current, dateAndTime_next;

    int numDaysFrom;

    double working_values_diff, working_values_diff_per_hour, value_i, value_ii, hour_total, minute_total, line_up_with_day_start_valueii, line_up_with_day_start_valuei;

    double BASE_cntr, working_values_diff_per_minute;


    public String[] set_ticker(String[] graph_linear_plots, String[] graph_linear_dates, String[] graph_linear_notes) {

        Log.e("  set_ticker ", "  set_ticker1");

        if (graph_linear_dates.length > 1) {

            for (int i = 0; i < graph_linear_dates.length; i++) {

                // Linear List is set to .format("YYYY-MM-DD"). Now you have a working datetime with it. ugh. go through and fix all other references where you break down the string to make a dt. ugh
                dateAndTime_current = new DateTime(graph_linear_dates[i]);

                // check if best fit. when is positive and when is negative.
                numDaysFrom = current_YYYY_MM_DD.numDaysFrom(dateAndTime_current);

                // When true means we are now lined up for present information
                if (numDaysFrom <= 0) {

                    Log.e("  set_ticker ", "  set_ticker2");
                    // Check if the next is not null. In theory it should never == null. If == null there is a problem and don't run this.
                    if (null != graph_linear_dates[1 + i]) {
                        Log.e("  set_ticker ", "  set_ticker3");

                        // Get the next stored date.
                        dateAndTime_next = new DateTime(graph_linear_dates[1 + i]);

                        // Subtract to get num days difference. if == 0 don't use this.
                        numDaysFrom = dateAndTime_current.numDaysFrom(dateAndTime_next);

                        // If equal zero use standard 24. no day division. 1 check should not be necessary.
                        if (numDaysFrom <= 0) {

                            // Abs value. Will have to check for negativity later.
                            value_i = Math.abs(Double.valueOf(graph_linear_plots[i]));

                            Log.e(String.valueOf(value_i), "  Running  i");

                            // Abs value. check for negativity to set the cntr. up or down.
                            value_ii = Math.abs(Double.valueOf(graph_linear_plots[1 + i]));

                            Log.e(String.valueOf(value_ii), "  Running  ii");

                            Log.e(String.valueOf(numDaysFrom), "  Running  numDaysFrom");

                            // This is the raw number subtracted that represents the time span to divide.
                            working_values_diff = Math.abs(value_i - value_ii);

                            // value per hour.
                            working_values_diff_per_hour = working_values_diff / 24;

                            // value per minute.
                            working_values_diff_per_minute = working_values_diff_per_hour / 60;

                            Log.e(String.valueOf(working_values_diff_per_minute), " working_values_diff_per_minute");

                            // This is your base value. Start a ticker recursion that adds to it. resets when necessary.
                            hour_total = hour_24 * working_values_diff_per_hour;

                            Log.e(String.valueOf(hour_total), "  Running  hour_total");

                            // Takes in the current minute and multiply it by the set value per minute.
                            minute_total = working_values_diff_per_minute * current_minute;

                            Log.e(String.valueOf(minute_total), "  Running  minute_total");

                            // No absolute value.
                            line_up_with_day_start_valuei = Double.valueOf(graph_linear_plots[i]);

                            line_up_with_day_start_valueii = Double.valueOf(graph_linear_plots[1 + i]);

                            Log.e(String.valueOf(line_up_with_day_start_valuei), "  line_up_with_day_start_valuei");

                            Log.e(String.valueOf(line_up_with_day_start_valueii), "  line_up_with_day_start_valueii");


                            // line up line_up_with_day_start_valuei.
                            // This is where we start our counting from.
                            BASE_cntr = line_up_with_day_start_valuei + hour_total + minute_total;

                            Log.e(String.valueOf(BASE_cntr), "  Running  BASE_cntr");


                            if (line_up_with_day_start_valueii > line_up_with_day_start_valuei) {


                                BASE_and_min_values[0] = String.valueOf(df.format(BASE_cntr));
                                BASE_and_min_values[1] = String.valueOf(df.format(working_values_diff_per_minute));

                                return BASE_and_min_values;


                            }

                            // check for negative and make sure your sending the correct counters.


                            else if (line_up_with_day_start_valueii < line_up_with_day_start_valuei) {


                                BASE_and_min_values[0] = String.valueOf(df.format(BASE_cntr));
                                BASE_and_min_values[1] = String.valueOf(df.format(-working_values_diff_per_minute));

                                return BASE_and_min_values;

                            } else if (line_up_with_day_start_valueii == line_up_with_day_start_valuei) {

                                // break and do nothing. values should never == each other!!
                                break;
                            }


                        }


                        //else {

                        // Subtract the days difference.

                        // With the days and the value difference from [i] and [ii]

                        // Divide the total_value by days_diff

                        // Now kick a 24 align like up top.


                        //   }


                    }
                }


            }
        }
        return null;
    }


}