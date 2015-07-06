package dmcfarland001.glucose.greenglucose.moneyplanner;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class HourGlass_One extends Activity {

	HourGlass_Two hgt;

	/*

		Unused. This is a canvas file to take the daily average and visualize it in an hour glass.



	 */

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		hgt = new HourGlass_Two(this);

	//	int imageResource = R.drawable.graphim;
	//	Drawable image = getResources().getDrawable(imageResource);
	//	hgt.setBackgroundDrawable(image);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		hgt.setwidth(width);
		hgt.setheight(height);

		setContentView(hgt);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
}
