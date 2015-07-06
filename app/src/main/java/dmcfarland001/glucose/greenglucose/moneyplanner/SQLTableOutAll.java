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
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SQLTableOutAll extends Activity
{
	SQLMoneyOut infoOut;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sql_table_view);
		
		TextView tvn = (TextView) findViewById(R.id.tvSQLinfo);
		
		
		infoOut = new SQLMoneyOut(this);
		
		try{
			infoOut.open();
			
			String notesOut = infoOut.getData();
			
			infoOut.close();
			
			tvn.setText(notesOut);
			
		} catch (Exception e) {
			String error = e.toString();
			Log.d("Graph error", error);
		}
	}
}
