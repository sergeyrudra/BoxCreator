package com.flywolf.furniture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.flywolf.furniture.CommonStatic.BoxesType;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class DbWorker {
	int x = 400, y = 350, z = 450, quantity = 1, shelfQuantity = 1;

	final String LOG_TAG = "myLogs";
	DBInit dbInit;
	String name;
	BoxSettings boxSettings = BoxSettings.getBoxSettings();
	// HashMap<SettingsType, Integer> settingsDefault;
	// Element[] boxes;
	ArrayList<Element> boxes = new ArrayList();
	long boxId = 0;
	Context context;
	BoxesType type;

	DbWorker(Context cnt) {
		this.context = cnt;
		dbInit = DBInit.getDBHelper(cnt);// new DBHelper(cnt);
		// settingsDefault = new HashMap<SettingsType, Integer>();
		// settingsDefault.put(SettingsType.DSP_THICK, 16);
		// settingsDefault.put(SettingsType.SOCLE_Y, 50);
		// settingsDefault.put(SettingsType.FACADE_CLEARANCE, 4);
		// settingsDefault.put(SettingsType.REAR_DEEP, 4);
		// settingsDefault.put(SettingsType.DSP_COST, 1000);
		// settingsDefault.put(SettingsType.REAR_COST, 120);
		// settingsDefault.put(SettingsType.EDGE_COST, 25);
	}

	private long deleteAll() {
		// подключаемся к БД
		SQLiteDatabase db = dbInit.getWritableDatabase();
		long rowID = db.delete("boxes", null, null);
		Log.d(LOG_TAG, "row deleted, ID = " + rowID);
		// закрываем подключение к БД
		dbInit.close();
		return rowID;
	}

	public long deleteBox(long boxId) {
		// подключаемся к БД
		SQLiteDatabase db = dbInit.getWritableDatabase();
		long rowID = db.delete("boxes", "id=" + boxId, null);
		Log.d(LOG_TAG, "row deleted, ID = " + rowID);
		rowID = db.delete("box_settings", "box_id=" + boxId, null);
		Log.d(LOG_TAG, "row deleted, ID = " + rowID);
		// закрываем подключение к БД
		dbInit.close();
		return rowID;
	}

	public long addToDb() {

		if (boxId != 0)
			deleteBox(boxId);
		if (Activity1.low)
			deleteAll();
		// deleteAll();
		// создаем объект для данных
		ContentValues cv = new ContentValues();

		// получаем данные из полей ввода
		// String name = etName.getText().toString();
		// String email = etEmail.getText().toString();

		// подключаемся к БД
		SQLiteDatabase db = dbInit.getWritableDatabase();

		Log.d(LOG_TAG, "--- Insert in mytable: ---");
		// подготовим данные для вставки в виде пар: наименование столбца -
		// значение

		cv.put("name", name);
		cv.put("type", type.name());
		cv.put("x", x);
		cv.put("y", y);
		cv.put("z", z);
		cv.put("quantity", quantity);
		cv.put("shelf_quantity", shelfQuantity);

		// вставляем запись и получаем ее ID
		boxId = db.insert("boxes", null, cv);
		Log.d(LOG_TAG, "row inserted, ID = " + boxId);
		// закрываем подключение к БД

		Iterator<Entry<SettingsType, Integer>> it = boxSettings.getSettings()
				.entrySet().iterator();
		while (it.hasNext()) {
			// BoxSetting bs = it.next();
			Map.Entry<SettingsType, Integer> pairs = (Map.Entry) it.next();
			cv = new ContentValues();
			cv.put("box_id", boxId);
			cv.put("type", pairs.getKey().name());
			cv.put("value", pairs.getValue());
			long sRowID = db.insert("box_settings", null, cv);
			Log.d(LOG_TAG, "row inserted, box_settings ID = " + sRowID);
		}

		dbInit.close();
		return boxId;
	}

	public void updateDb() {
		SQLiteDatabase db = dbInit.getWritableDatabase();
		try {
			db.execSQL("drop table boxes;");
		} catch (Exception ex) {
		}
		db.execSQL("create table boxes ("
				+ "id integer primary key autoincrement," + "name text,"
				+ "x integer," + "y integer," + "z integer," + "type text,"
				+ "quantity integer," + "add_facade boolean,"
				+ "shelf_quantity integer," + "edge_depth integer" + ");");
		try {
			db.execSQL("drop table details;");
		} catch (Exception ex) {
		}
		db.execSQL("create table details ("
				+ "id integer primary key autoincrement," + "box_id integer,"
				+ "x integer," + "y integer," + "quantity integer,"
				+ "x_edge integer," + "y_edge integer" + ");");
		try {
			db.execSQL("drop table box_settings;");
		} catch (Exception ex) {
		}
		db.execSQL("create table box_settings ("
				+ "id integer primary key autoincrement," + "box_id integer,"
				+ "type text," + "value integer," + "additional text);");

	}

	public void readFromDb() {
		// создаем объект для данных
		ContentValues cv = new ContentValues();

		// получаем данные из полей ввода
		// String name = etName.getText().toString();
		// String email = etEmail.getText().toString();

		// подключаемся к БД
		SQLiteDatabase db = dbInit.getWritableDatabase();

		Log.d(LOG_TAG, "--- Rows in mytable: ---");
		// делаем запрос всех данных из таблицы mytable, получаем Cursor
		// Cursor c = db.query("boxes", null, null, null, null, null, null);
		Cursor c = db.query("boxes", null, boxId != 0 ? "id=" + boxId : null,
				null, null, null, null);

		// ставим позицию курсора на первую строку выборки
		// если в выборке нет строк, вернется false
		int boxId = 0;
		if (c.moveToLast()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex("id");
			int nameColIndex = c.getColumnIndex("name");
			int typeColIndex = c.getColumnIndex("type");
			int xColIndex = c.getColumnIndex("x");
			int yColIndex = c.getColumnIndex("y");
			int zColIndex = c.getColumnIndex("z");
			int quantityColIndex = c.getColumnIndex("quantity");
			int shelfQuantityColIndex = c.getColumnIndex("shelf_quantity");

			do {
				// получаем значения по номерам столбцов и пишем все в лог
				boxId = c.getInt(idColIndex);
				name = c.getString(nameColIndex);
				type = BoxesType.valueOf(c.getString(typeColIndex));
				x = c.getInt(xColIndex);
				y = c.getInt(yColIndex);
				z = c.getInt(zColIndex);
				quantity = c.getInt(quantityColIndex);
				shelfQuantity = c.getInt(shelfQuantityColIndex);
				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false -
				// выходим из цикла
			} while (c.moveToNext());
		} else
			Log.d(LOG_TAG, "0 rows");

		c = db.query("box_settings", null, "box_id=" + boxId, null, null, null,
				null);

		if (c.moveToFirst()) {
			int typeColIndex = c.getColumnIndex("type");
			int valueColIndex = c.getColumnIndex("value");

			boxSettings.settings = new LinkedHashMap<SettingsType, Integer>();
			do {

				boxSettings.settings.put(
						SettingsType.valueOf(c.getString(typeColIndex)),
						c.getInt(valueColIndex));
				// Log.d(LOG_TAG,"percentage="+cs.type);
			} while (c.moveToNext());
		} else
			Log.d(LOG_TAG, "0 rows");

		c.close();
		// закрываем подключение к БД
		dbInit.close();
	}

	public void readBoxes() {
		// deleteAll();
		// создаем объект для данных
		try {
			if (boxes == null)
				boxes = new ArrayList<DbWorker.Element>();
			else
				boxes.clear();
			ContentValues cv = new ContentValues();

			// получаем данные из полей ввода
			// String name = etName.getText().toString();
			// String email = etEmail.getText().toString();

			// подключаемся к БД
			SQLiteDatabase db = dbInit.getWritableDatabase();

			Log.d(LOG_TAG, "--- Rows in mytable: ---");
			// делаем запрос всех данных из таблицы mytable, получаем Cursor
			Cursor c = db.query("boxes", null, null, null, null, null,
					"id desc");

			// ставим позицию курсора на первую строку выборки
			// если в выборке нет строк, вернется false
			int boxId = 0;
			if (c.moveToFirst()) {

				// определяем номера столбцов по имени в выборке
				int idColIndex = c.getColumnIndex("id");
				int nameColIndex = c.getColumnIndex("name");
				int xColIndex = c.getColumnIndex("x");
				int yColIndex = c.getColumnIndex("y");
				int zColIndex = c.getColumnIndex("z");
				int typeColIndex = c.getColumnIndex("type");
				do {
					// получаем значения по номерам столбцов и пишем все в лог
					// boxId = c.getInt(idColIndex);
					int id = context.getResources().getIdentifier(
							context.getPackageName() + ":string/"
									+ c.getString(typeColIndex), null, null);
					Element e = new Element(c.getString(nameColIndex) + "/"
							+ context.getString(id) + "(" + c.getInt(xColIndex)
							+ "x" + c.getInt(yColIndex) + "x"
							+ c.getInt(zColIndex) + ")", c.getInt(idColIndex));
					boxes.add(e);

					// name = c.getString(nameColIndex);
					// l.add(c.getString(nameColIndex)+"(x:"+c.getInt(xColIndex)+"y:"+c.getInt(yColIndex)+"z:"+c.getInt(zColIndex)+")");
					// x = c.getInt(xColIndex);
					// y = c.getInt(yColIndex);
					// z = c.getInt(zColIndex);
					// quantity = c.getInt(quantityColIndex);
					// shelfQuantity = c.getInt(shelfQuantityColIndex);
					// переход на следующую строку
					// а если следующей нет (текущая - последняя), то false -
					// выходим из цикла
				} while (c.moveToNext());
			} else
				Log.d(LOG_TAG, "0 rows");
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}

	}

	public static class Element {
		private String mText;
		private int mId;

		public Element(String text, int id) {
			mText = text;
			mId = id;
		}

		public int getId() {
			return mId;
		}

		public void setId(int id) {
			mId = id;
		}

		public String getmText() {
			return mText;
		}

		public void setmText(String mText) {
			this.mText = mText;
		}

		@Override
		public String toString() {
			return mText;
		}
	}

	/*
	 * public void saveBoxSettings(SettingsType type, int value) { for
	 * (Iterator<BoxSetting> iterator = boxSettings.iterator(); iterator
	 * .hasNext();) { BoxSetting x = iterator.next(); if (x.type == type) {
	 * x.value = value; } } }
	 */
}
