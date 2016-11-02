package com.flywolf.furniture;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBInit extends SQLiteOpenHelper {
	final String LOG_TAG = "myLogs";
	private static DBInit dbInit;

	// singletone
	public static DBInit getDBHelper(Context context) {
		if (dbInit == null) {
			dbInit = new DBInit(context, Activity1.DB_VERSION);
		}
		return dbInit;
		
	}

	public DBInit(Context context, int dbVersion) {
		// create constractor
		super(context, Activity1.DB_NAME, null, dbVersion);
		// context.deleteDatabase("box_creator");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(LOG_TAG, "--- onCreate database ---");
		// create tables
		db.execSQL("create table boxes ("
				+ "id integer primary key autoincrement,"
				+ "name text," + "x integer," + "y integer,"
				+ "z integer," + "type text," + "quantity integer,"
				+ "add_facade boolean," + "shelf_quantity integer,"
				+ "edge_depth integer" + ");");
		db.execSQL("create table details ("
				+ "id integer primary key autoincrement," + "box_id integer,"
				+ "x integer," + "y integer," + "quantity integer,"
				+ "x_edge integer," + "y_edge integer" + ");");
		db.execSQL("create table box_settings ("
				+ "id integer primary key autoincrement,"
				+ "box_id integer," + "type text," + "value integer,"
				+ "additional text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion + " to "
				+ newVersion + " version --- ");
	}
}
