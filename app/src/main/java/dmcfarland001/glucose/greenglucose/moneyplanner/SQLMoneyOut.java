package dmcfarland001.glucose.greenglucose.moneyplanner;

import hirondelle.date4j.DateTime;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author
 * @copywright Daniel McFarland DMcFarland001@gmail.com "Green Glucose Inc."
 *             "Green Glucose" This is an Android application that uses
 *             statistics to map out a future bank account. This information is
 *             meant only for informational purposes and no responcibility's are
 *             on me the author of this application. I take no responsibility
 *             and have no liability for any information displayed on or in this
 *             application. Only intention for this application is to guess
 *             information.
 * 
 */
public class SQLMoneyOut {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NOTE = "note";
	public static final String KEY_VALUE = "value";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String DATABASE_NAME = "FirstWalletValuesOut";
	private static final String DATABASE_TABLE = "FirstMoneyTableOut";
	private static final int DATABASE_VERSION = 1;

	private DbHelper dbHelper;
	private Context dbContext;
	private SQLiteDatabase dbDatabase;

	public static final String CREATE_FIRST_TABLE = "CREATE TABLE "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOTE
			+ " TEXT NOT NULL, " + KEY_VALUE + " TEXT NOT NULL, "
			+ KEY_CREATED_AT + " DATETIME " + ")";

	public static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(CREATE_FIRST_TABLE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);

		}

	}

	public SQLMoneyOut(Context c) {
		dbContext = c;
	}

	// going to set up the database
	public SQLMoneyOut open() throws SQLException {

		dbHelper = new DbHelper(dbContext);
		dbDatabase = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbDatabase.close();
	}

	public void deleteDataBase() {
		dbContext.deleteDatabase(DATABASE_NAME);
	}

	public long createEntry(String note, String value) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_NOTE, note);
		cv.put(KEY_VALUE, value);
		cv.put(KEY_CREATED_AT, currentDateTime());
		return dbDatabase.insert(DATABASE_TABLE, null, cv);

	}

	public long addEntry(String id_a, String note, String value) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ROWID, id_a);
		cv.put(KEY_NOTE, note);
		cv.put(KEY_VALUE, value);
		cv.put(KEY_CREATED_AT, currentDateTime());
		return dbDatabase.insert(DATABASE_TABLE, null, cv);

	}


	public void DeleteEntry(long ID) {


		try {

			dbDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + ID, null);

			// Define 'where' part of query.
			//String selection = KEY_ROWID + "=" + ID;

			// Specify arguments in placeholder order.
			//String[] selectionArgs = {String.valueOf(ID)};

			// Issue SQL statement.
			//dbDatabase.delete(DATABASE_TABLE, selection, selectionArgs);


		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			// dbDatabase.close();

		}

	}


	// String for printing
	public String getData() {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT, };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iNote = c.getColumnIndex(KEY_NOTE);
		int iValue = c.getColumnIndex(KEY_VALUE);
		int iDateTime = c.getColumnIndex(KEY_CREATED_AT);

		String temp, tmp, temp_m, temp_d, temp_y, dateForm;

		DateTime yo;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			temp = c.getString(iDateTime);

			// SQL OUT﹕ 2015-05-18
			// Values are saved as YYYY-MM-DD
			Log.d("SQL OUT", "  " + temp);

			tmp = temp.replace("-", " ");

			temp_y = tmp.substring(0, 4);
			temp_m = tmp.substring(5, 7);
			temp_d = tmp.substring(8, 10);

			// Create a valid Date
			yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
					Integer.valueOf(temp_m), Integer.valueOf(temp_d));

			// Access the Date and format it MM-DD-YYYY
			dateForm = yo.format("MM-DD-YYYY");

			// This is a scan building String.
			result = result + c.getString(iRow) + "\t" + c.getString(iNote)
					+ "\t\t\t" + c.getString(iValue) + "\t\t\t" + dateForm + "\n";
		}
		c.close();
		return result;
	}

	public String getNotes() {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iNote = c.getColumnIndex(KEY_NOTE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{

			result = result + c.getString(iRow) + " " + c.getString(iNote)
					+ "\n";
		}

		c.close();

		return result;
	}

	public String getValues() {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String result = "";

		int iValue = c.getColumnIndex(KEY_VALUE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iValue) + "\n";
		}

		c.close();

		return result;
	}

	public String getDates() {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String result = "";

		int iDateTime = c.getColumnIndex(KEY_CREATED_AT);

		String temp, tmp, temp_m, temp_d, temp_y, dateForm;

		DateTime yo;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			temp = c.getString(iDateTime);

			tmp = temp.replace("-", " ");

			temp_y = tmp.substring(0, 4);
			temp_m = tmp.substring(5, 7);
			temp_d = tmp.substring(8, 10);

			yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
					Integer.valueOf(temp_m), Integer.valueOf(temp_d));

			dateForm = yo.format("MM-DD-YYYY");

			result = result + dateForm + "\n";
		}

		c.close();
		return result;
	}

	public String getNote(long id_l_i) {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ id_l_i, null, null, null, null);

		if (c != null) {

			c.moveToFirst();

			String note = c.getString(1);

			c.close();

			return note;
		}

		return null;
	}

	public String getValue(long id_l_i) {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ id_l_i, null, null, null, null);

		if (c != null) {

			c.moveToFirst();

			String value = c.getString(2);

			c.close();

			return value;
		}

		return null;
	}

	public String getDate(long id_l_i) {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ id_l_i, null, null, null, null);

		if (c != null) {

			c.moveToFirst();

			String date = c.getString(3);

			c.close();

			return date;
		}
		return null;
	}

	public void updateEntry(long l, String note, String value, String date) {
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(KEY_NOTE, note);
		cvUpdate.put(KEY_VALUE, value);
		cvUpdate.put(KEY_CREATED_AT, date);

		dbDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + l, null);
	}

	private String currentDateTime() {

		DateTime now = DateTime.now(TimeZone.getDefault());

		String result = now.format("YYYY-MM-DD");

		return result;
	}

	public Set<String> getAllNotesOut() {

		Set<String> str = new HashSet<String>();

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		if (c.getCount() > 0) {

			int iNote = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				str.add(c.getString(iNote));

			}

		}
		c.close();
		return str;
	}

	// Notes in a Set.
	public String[] SetgetAllNotesOut() {

		int i = -1;
		int iNote;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE },
				null, null, "note", null, "note ASC");

		String[] str = new String[c.getCount()];

		if (c.getCount() > 0) {

			iNote = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

				str[++i] = c.getString(iNote);

			}

		}
		c.close();
		return str;
	}

	public String[] getAllNotesOutString() {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String[] str = new String[c.getCount()];


		if (c.getCount() > 0) {

			int iNote = c.getColumnIndex(KEY_NOTE);

			int i = -1;

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iNote);


			c.close();

			return str;
		}

		return null;
	}

	public String[] getAllValuesOut() {

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String[] str = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			int i = -1;

			int iValue = c.getColumnIndex(KEY_VALUE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iValue);

			c.close();

			return str;
		}

		return null;
	}

	public String[] getAllDatesOut()
	{

		String[] columns = new String[] { KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT };

		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		String[] str = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			int iDate = c.getColumnIndex(KEY_CREATED_AT);

			int i = -1;

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iDate);

			c.close();

			return str;
		}
		return null;
	}

	public long createEntry_date(String note, String value, String date)
	{

		ContentValues cv = new ContentValues();

		cv.put(KEY_NOTE, note);
		cv.put(KEY_VALUE, value);
		cv.put(KEY_CREATED_AT, date);

		return dbDatabase.insert(DATABASE_TABLE, null, cv);

	}

	public String[] orderByDateNotes() {

		// SELECT KEY_VALUE, KEY_CREATED_AT ORDER BY KEY_CREATED_AT DEC;
		int i = -1;
		int iNote;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE },
				null, null, null, null, "created_at ASC");

		String[] note = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			iNote = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				note[++i] = c.getString(iNote);

			return note;
		}

		c.close();

		return null;
	}

	public String[] orderByDateValues() {

		// SELECT KEY_VALUE, KEY_CREATED_AT ORDER BY KEY_CREATED_AT DEC;
		int i = -1;
		int iValue;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_VALUE },
				null, null, null, null, "created_at ASC");

		String[] value = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			iValue = c.getColumnIndex(KEY_VALUE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				value[++i] = c.getString(iValue);

			return value;

		}

		c.close();

		return null;
	}

	public String[] orderByDateDate() {

		// SELECT KEY_VALUE, KEY_CREATED_AT ORDER BY KEY_CREATED_AT DEC;
		int i = -1;

		int iDate;

		Cursor c = dbDatabase.query(DATABASE_TABLE,
				new String[] { KEY_CREATED_AT }, null, null, null, null,
				"created_at ASC");

		String[] date = new String[c.getCount()];

		if (c.getCount() > 0) {

			iDate = c.getColumnIndex(KEY_CREATED_AT);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				date[++i] = c.getString(iDate);

			return date;

		}

		c.close();

		return null;
	}

	/*
	 * for list printing
	 */
	public String groupNameOrderDateNote() {

		int iNote;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE },
				null, null, null, null, "note, created_at DESC");

		String result = "";

		if (c.getCount() > 0) {

			iNote = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				result = result + c.getString(iNote) + "\n";
			}

			return result;
		}

		c.close();

		return null;
	}




	public String groupNameOrderDateValue()
	{

		int iDate;


		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_VALUE },
				null, null, null, null, "note, created_at DESC");


		String result = "";

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_VALUE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				result = result + c.getString(iDate) + "\n";
			}

			return result;
		}

		c.close();

		return null;
	}




	public String groupNameOrderDateDate()
	{

		int iDate;

		Cursor c = dbDatabase.query(DATABASE_TABLE,
				new String[] { KEY_CREATED_AT }, null, null, null, null,
				"note, created_at DESC");


		String result = "";

		if (c.getCount() > 0)
		{


			iDate = c.getColumnIndex(KEY_CREATED_AT);


			String temp, tmp, temp_m, temp_d, temp_y, dateForm;


			DateTime yo;

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

				temp = c.getString(iDate);

				tmp = temp.replace("-", " ");


				temp_y = tmp.substring(0, 4);
				temp_m = tmp.substring(5, 7);
				temp_d = tmp.substring(8, 10);


				yo = DateTime.forDateOnly(Integer.valueOf(temp_y),
						Integer.valueOf(temp_m), Integer.valueOf(temp_d));

				dateForm = yo.format("MM-DD-YYYY");

				result = result + dateForm + "\n";
			}

			return result;
		}

		c.close();

		return null;
	}

	/**
	 * Minimized list view note value date
	 * 
	 */

	// String for list
	public String MinViewNote()
	{

		int iDate;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE },
				null, null, "note", null, "note ASC");

		String result = "";

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				result = result + c.getString(iDate) + "\n";
			}

			return result;
		}

		c.close();

		return null;
	}

	// String for list
	public String MinViewValue()
	{

		int iDate;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_VALUE },
				null, null, "note", null, "note ASC");

		String result = "";

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_VALUE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				result = result + c.getString(iDate) + "\n";
			}

			return result;
		}

		c.close();

		return null;
	}

	// String for list
	public String MinViewDate()
	{

		int iDate;

		Cursor c = dbDatabase.query(DATABASE_TABLE,
				new String[] { KEY_CREATED_AT }, null, null, "note", null,
				"note, created_at DESC");

		String result = "";

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_CREATED_AT);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				result = result + c.getString(iDate) + "\n";
			}

			return result;
		}

		c.close();

		return null;
	}

	public String[] groupNameOrderDateNoteA()
	{

		int iDate;

		int i = -1;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE },
				null, null, null, null, "note, created_at ASC");

		String[] str = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iDate);

			return str;
		}

		return null;
	}

	public String[] groupNameOrderDateValueA()
	{

		int iDate;

		int i = -1;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_VALUE },
				null, null, null, null, "note, created_at ASC");

		String[] str = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_VALUE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iDate);

			return str;
		}

		return null;

	}

	public String[] groupNameOrderDateDateA()
	{

		int iDate;

		int i = -1;


		Cursor c = dbDatabase.query(DATABASE_TABLE,
				new String[] { KEY_CREATED_AT }, null, null, null, null,
				"note, created_at ASC");

		String[] str = new String[c.getCount()];


		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_CREATED_AT);


			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iDate);

			return str;
		}

		return null;

	}

	public String[] NoDuplicatesDates()
	{

		int iDate;

		int i = -1;

		Cursor c = dbDatabase.query(DATABASE_TABLE,
				new String[] { KEY_CREATED_AT }, null, null, "created_at",
				null, "created_at ASC");


		String[] str = new String[c.getCount()];


		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_CREATED_AT);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iDate);

			return str;
		}
		return null;
	}

	public String[] NoDuplicatesDatesNotes()
	{

		int iDate;

		int i = -1;

		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE },
				null, null, "created_at", null, "created_at ASC");


		String[] str = new String[c.getCount()];

		if (c.getCount() > 0)
		{

			iDate = c.getColumnIndex(KEY_NOTE);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
				str[++i] = c.getString(iDate);

			return str;
		}

		return null;
	}

	String[] sorted_min_note, sorted_min_date, sorted_min_id;


	public void set_minviewpoplastdate() {

		int iNote, iDate, iID;

		// boolean to ensure only one click at a time.
		boolean truth = true;

		int i = -1, j = -1;

		String[] columns = new String[]{KEY_ROWID, KEY_NOTE, KEY_VALUE,
				KEY_CREATED_AT};


		Cursor c = dbDatabase.query(DATABASE_TABLE, columns, null, null, null, null, "note, created_at ASC");

		Cursor b = dbDatabase.query(DATABASE_TABLE, columns, null, null, "note", null, "note, created_at ASC");


		String[] all_str_note = new String[c.getCount()];
		String[] all_str_date = new String[c.getCount()];
		String[] all_str_id = new String[c.getCount()];


		String[] min_str_note = new String[b.getCount()];
		String[] min_str_date = new String[b.getCount()];
		String[] min_str_id = new String[b.getCount()];


		// This is what will be returned.
		sorted_min_note = new String[b.getCount()];
		sorted_min_date = new String[b.getCount()];
		sorted_min_id = new String[b.getCount()];


		// Get the whole list. Group by note and order by date.
		if (c.getCount() > 0) {

			iNote = c.getColumnIndex(KEY_NOTE);
			iDate = c.getColumnIndex(KEY_CREATED_AT);
			iID = c.getColumnIndex(KEY_ROWID);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				all_str_note[++i] = c.getString(iNote);
				all_str_date[i] = c.getString(iDate);
				all_str_id[i] = c.getString(iID);
			}
		}

		c.close();

		// Get a list with no doubles in the notes.
		// This list is topped with the last inserted into the sql table. NOT the stored date.
		// The above [] IS sorted by date so get the first to match then move on. simple i j loop

		if (b.getCount() > 0) {

			iNote = b.getColumnIndex(KEY_NOTE);
			iDate = b.getColumnIndex(KEY_CREATED_AT);
			iID = b.getColumnIndex(KEY_ROWID);

			for (b.moveToFirst(); !b.isAfterLast(); b.moveToNext()) {
				min_str_note[++j] = b.getString(iNote);
				min_str_id[j] = b.getString(iID);
				min_str_date[j] = b.getString(iDate);
			}
		}

		b.close();


		for (int cntr_i = 0; cntr_i < min_str_note.length; cntr_i++)

		{

			for (int cntr_j = 0; cntr_j < all_str_note.length; cntr_j++)

			{

				if (truth && min_str_note[cntr_i].matches(all_str_note[cntr_j])) {


					truth = false;

					sorted_min_note[cntr_i] = all_str_note[cntr_j];
					sorted_min_date[cntr_i] = all_str_date[cntr_j];
					sorted_min_id[cntr_i] = all_str_id[cntr_j];


				}
				if (!min_str_note[cntr_i].matches(all_str_note[cntr_j])) {

					truth = true;

				}

			}

		}


	}

	public String[] sorted_min_note() {

		return sorted_min_note;

	}

	public String[] sorted_min_date() {

		return sorted_min_date;
	}

	public String[] sorted_min_id() {

		return sorted_min_id;

	}







	/**
	 * call for an update sql_in read in sql order by date get first value.
	 * start a counter to count 1++ base first value to 25 if greater > 25 start
	 * to update database. start counter [25] into [0]
	 * 
	 * else give error else 25
	 * 
	 * 
	 * @return
	 * 
	 * 
	 *         int iDate, iNote, iValue; int i = -1, numDays;
	 * 
	 *         String temp, tmp, temp_m, temp_d, temp_y;
	 * 
	 *         DateTime firstCntrDate, secondCntrDate;
	 * 
	 *         Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] {
	 *         KEY_NOTE }, null, null, null, null, "created_at ASC"); Cursor c1
	 *         = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_VALUE },
	 *         null, null, null, null, "created_at ASC"); Cursor c2 =
	 *         dbDatabase.query(DATABASE_TABLE, new String[] { KEY_CREATED_AT },
	 *         null, null, null, null, "created_at ASC");
	 * 
	 *         String[] str = new String[c.getCount()];
	 * 
	 *         if (c.getCount() > 0) { iDate = c.getColumnIndex(KEY_CREATED_AT);
	 *         iNote = c.getColumnIndex(KEY_NOTE); iValue =
	 *         c.getColumnIndex(KEY_VALUE); /* get first value. start a counter
	 *         to count 1++ base first value to 25 if greater > 25 start to
	 *         update database.
	 * 
	 *         c.moveToFirst(); String firstNot = c.getString(iDate);
	 * 
	 *         firstNot = firstNot.replace("-", " "); temp_m =
	 *         firstNot.substring(5, 7); temp_d = firstNot.substring(8, 10);
	 *         temp_y = firstNot.substring(0, 4);
	 * 
	 *         firstCntrDate = DateTime.forDateOnly(Integer.valueOf(temp_y),
	 *         Integer.valueOf(temp_m), Integer.valueOf(temp_d));
	 * 
	 *         for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
	 * 
	 *         temp = c.getString(iDate); tmp = temp.replace("-", " "); temp_m =
	 *         tmp.substring(5, 7); temp_d = tmp.substring(8, 10); temp_y =
	 *         tmp.substring(0, 4);
	 * 
	 *         secondCntrDate = DateTime.forDateOnly(Integer.valueOf(temp_y),
	 *         Integer.valueOf(temp_m), Integer.valueOf(temp_d));
	 * 
	 *         numDays = secondCntrDate.numDaysFrom(firstCntrDate);
	 * 
	 *         if(numDays > 25 ) {
	 * 
	 * 
	 * 
	 *         c.getString(iNote); c1.getString(iValue); c2.getString(iDate);
	 * 
	 *         }
	 * 
	 * 
	 * 
	 *         } return str; } return null; }
	 *

	String[] value = null, note = null, date = null;

	public void DeleteLastThirtyUpdate() {

		Log.d("DeleteLasdateOut", "DeleteLastThirtyUpdateOut");

		boolean isGreater = false;

		DateTime firstCntrDate, secondCntrDate;

		int numDays, ko = 0;

		Log.d("DeleteLartyUpdateOut", "2");
		
		Cursor c = dbDatabase.query(DATABASE_TABLE, new String[] { KEY_NOTE, KEY_VALUE, KEY_CREATED_AT },
				null, null, null, null, "created_at ASC");
		
		Log.d("DeleteLhirtyUpdateOut", "3");

		int iNote = c.getColumnIndex(KEY_NOTE);
		int iValue = c.getColumnIndex(KEY_VALUE);
		int iDate = c.getColumnIndex(KEY_CREATED_AT);


		String temp, tmp, temp_m, temp_d, temp_y, firstNot;

		Log.d("DeleteLasrtyUpdateOut", "4");

		if (c.getCount() > 3)
		{

			value = new String[c.getCount()];

			Log.d("DeleteLirtyUpdateOut", "45");

			note = new String[c.getCount()];

			Log.d("DeleteLasirtyUpdateOut", "445");

			date = new String[c.getCount()];

			Log.d("DeleteLayUpdateOut", "4445");

			c.moveToFirst();

			Log.d("DeleteirtyUpdateOut", "44445");

			firstNot = c.getString(iDate);

			Log.d("DeleteLastThpdateOut", "444445");

			firstNot = firstNot.replace("-", " ");

			Log.d("DeleteLastThdateOut", "444444445");

			temp_m = firstNot.substring(5, 7);

			temp_d = firstNot.substring(8, 10);

			temp_y = firstNot.substring(0, 4);

			Log.d("DeleteLastpdateOut", "4444444444445");


			firstCntrDate = DateTime.forDateOnly(Integer.valueOf(temp_y),
					Integer.valueOf(temp_m), Integer.valueOf(temp_d));


			Log.e("firstCntrDate", firstCntrDate.format("MM-DD-YYYY"));

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
			{

				temp = c.getString(iDate);

				tmp = temp.replace("-", " ");

				temp_m = tmp.substring(5, 7);
				temp_d = tmp.substring(8, 10);
				temp_y = tmp.substring(0, 4);

				Log.d("DeleteLrtyUpdateOut", "6");

				secondCntrDate = DateTime.forDateOnly(Integer.valueOf(temp_y),
						Integer.valueOf(temp_m), Integer.valueOf(temp_d));

				Log.e("secondCntrDate", secondCntrDate.format("MM-DD-YYYY"));

				numDays = firstCntrDate.numDaysFrom(secondCntrDate);

				Log.e("ND " + String.valueOf(numDays), String.valueOf(numDays));

				if (numDays > 25) {

					Log.d("DeletertyUpdateOut", String.valueOf(numDays));

					isGreater = true;

					note[++ko] = c.getString(iNote);
					value[ko] = c.getString(iValue);
					date[ko] = c.getString(iDate);

					Log.d("note[ko]", note[ko]);

					Log.d("value[ko]", value[ko]);
					
					Log.d("date[ko]", date[ko]);
					// update by storing into a temp string array for all notes
					// values and dates.

				}
			}
			
			Log.d("DeleteLastThiateOut", "7");
			
			if (isGreater) {

				dbContext.deleteDatabase(DATABASE_NAME);
				open();
				
				for (int i = 1; i <= ko; i++) {
					
					Log.d("note[ko]", note[i]);

					Log.d("value[ko]", value[i]);
					
					Log.d("date[ko]", date[i]);
					
					createEntryThirty(note[i], value[i], date[i]);
				}

			} else if (isGreater == false) {

			}
		}
	}

	// The function that is called from delete last thirty days. Buggy.
	public long createEntryThirty(String note, String value, String date)
	{
		
		ContentValues cv = new ContentValues();

		cv.put(KEY_NOTE, note);
		cv.put(KEY_VALUE, value);
		cv.put(KEY_CREATED_AT, date);

		return dbDatabase.insert(DATABASE_TABLE, null, cv);

	}
	*/




}
