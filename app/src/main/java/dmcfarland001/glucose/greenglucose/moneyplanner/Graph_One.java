package dmcfarland001.glucose.greenglucose.moneyplanner;
/**
 * 
 * @author 
 * @copywright 
 * 			Daniel McFarland DMcFarland001@gmail.com "Green Glucose Inc."
 *         "Green Glucose" This is an Android application that uses statistics
 *         to map out a future bank account. This information is meant only for
 *         informational purposes and no responsibility's are on me the author
 *         of this application. I take no responsibility and have no liability
 *         for any information displayed on or in this application. Only
 *         intention for this application is to guess information.
 * 
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Graph_One extends Activity {
	
	Graph_Three Graph_three;

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		Graph_three = new Graph_Three(this);

		//Graph_three.setBackgroundColor(Color.WHITE);
		
	//	Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.graphim);
		
		int imageResource = R.drawable.graphim;
		Drawable image = getResources().getDrawable(imageResource);
		Graph_three.setBackgroundDrawable(image);
	//	Graph_three.setBackground(image);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		Graph_three.setwidth(width);
		Graph_three.setheight(height);
		
		setContentView(Graph_three);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
