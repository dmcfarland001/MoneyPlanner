package dmcfarland001.glucose.greenglucose.moneyplanner;

import hirondelle.date4j.DateTime;

import java.util.TimeZone;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class HourGlass_Two extends View {

	int maxHeight = 0, maxWidth = 0;
	DateTime todays_date, month_day_year_datetime_cntr;

	public HourGlass_Two(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
	}

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
	 * */
	
	@SuppressWarnings("unused")
	private DateTime currentDateTime() {
		DateTime now = DateTime.now(TimeZone.getDefault());
		return now;
	}
}
