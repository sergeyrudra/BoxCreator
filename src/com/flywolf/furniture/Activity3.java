package com.flywolf.furniture;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.flywolf.boxcreator.R;
import com.flywolf.boxcreator.Settings;
import com.flywolf.furniture.CommonStatic.Detail;
import com.flywolf.furniture.CommonStatic.SettingsType;

public abstract class Activity3 extends Activity implements
		MultiSpinner.MultiSpinnerListener {
	String name;
	String boxDescription;
	String detailsDescription;
	EditText etName;
	SharedPreferences sPref;
	final String LOG_TAG = "myLogs";
	DbWorker dbWorker;
	Box box;
	int selectedId;
	ArrayList<Integer> selectedIds = new ArrayList<Integer>();
	ArrayList<String> ids = new ArrayList<String>();
	boolean projectMode = false;

	/** Called when the activity is first created. */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		etName = (EditText) findViewById(R.id.name);
		Button next = (Button) findViewById(R.id.previous);
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						Activity1.activity2);
				Bundle b = new Bundle();
				b.putInt("id", selectedId); // Your id
				myIntent.putExtras(b); // Put your id to your next Intent
				startActivity(myIntent);
				finish();
			}
		});
		sPref = getSharedPreferences("BoxPref", MODE_PRIVATE);
		String s = sPref.getString("project", null);
		if (s != null) {
			String a[] = sPref.getString("project", null).split(",");
			for (int i = 0; i < a.length; i++) {
				selectedIds.add(Integer.parseInt(a[i]));
				Log.d(LOG_TAG, "idxxx = " + Integer.parseInt(a[i]));
			}
		}

		dbWorker = new DbWorker(this);
		dbWorker.readFromDb();
		showDetails();
		loadBox();
		// selectedIds = Arrays.asList(sPref.getString("project",
		// "").split(";"));
	}

	@Override
	public void onItemsSelected(boolean[] selected) {
		selectedIds.clear();
		// String m = "";
		// ArrayList<String> idss = new ArrayList<String>();
		// idss.add("7");
		// idss.add("7");
		String p = "";
		for (int i = 0; i < selected.length; i++) {
			// m += ids.get(i) + ",";
			if (selected[i]) {
				p = p + (p.contentEquals("") ? "" : ",");
				p += ids.get(i);
				selectedIds.add(Integer.valueOf(ids.get(i)));
				// Toast.makeText(getBaseContext(), "try "+ids.get(i),
				// Toast.LENGTH_SHORT).show();
				// String el = "7";
				// Integer k = Integer.valueOf(el);
				// dbWorker.boxId = Integer.valueOf(ids.get(i));
				// dbWorker.readFromDb();
				// showDetails();
			}
		}
		// Toast.makeText(getBaseContext(), "try "+m,
		// Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		Editor ed = sPref.edit();
		ed.putString("project", p.contentEquals("") ? null : p);
		ed.commit();
		goProjects(null);
		projectMode = true;
	}

	public void goBox(View view) {
		dbWorker.boxId = selectedId;
		dbWorker.readFromDb();
		showDetails();
		projectMode = false;
	}

	public void goProjects(View view) {
		projectMode = true;
		int edgeLongTotal = 0;
		Double edgeCostTotal = 0d;
		int dspSquareTotal = 0;
		Double dspCostTotal = 0d;
		int dvpSquareTotal = 0;
		Double dvpCostTotal = 0d;
		Double furnitureCostTotal = 0d;
		Double workCostTotal = 0d;
		Double rentCostTotal = 0d;
		Double totalCostTotal = 0d;
		// prepare details table
		TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
		int count = tableLayout.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = tableLayout.getChildAt(i);
			if (child instanceof TableRow && i != 0)
				((ViewGroup) child).removeAllViews();
		}
		boxDescription = getString(R.string.project);

		for (Iterator<Integer> iterator = selectedIds.iterator(); iterator
				.hasNext();) {
			// x = iterator.next();
			dbWorker.boxId = iterator.next();
			dbWorker.readFromDb();
			// showDetails();

			// fill box by details
			box = new BoxesFactory().getBox(dbWorker.type);
			box.create(dbWorker);
			box.createDetailSummary(dbWorker);
			// fill details
			for (Detail s : box.getDetails()) {
				int materialId = getResources().getIdentifier(
						getPackageName() + ":string/" + s.getMaterial(), null,
						null);
				int id = getResources().getIdentifier(
						getPackageName() + ":string/" + s.getName().name(),
						null, null);
				Log.d(LOG_TAG, "name = " + s.getName().name());
				addRow(getString(id), getString(materialId), s.getWidth(),
						s.getLength(), s.getWidthEdge(), s.getLengthEdge(),
						s.getQuantity(), box.getDetailSummary().name);
			}

			// writeDetails();
			edgeLongTotal += box.getDetailSummary().edgeLong;
			edgeCostTotal += box.getDetailSummary().edgeCost;
			dspSquareTotal += box.getDetailSummary().dspSquare;
			dspCostTotal += box.getDetailSummary().dspCost;
			dvpSquareTotal += box.getDetailSummary().dvpSquare;
			dvpCostTotal += box.getDetailSummary().dvpCost;
			furnitureCostTotal += box.getDetailSummary().furnitureCost;
			workCostTotal += box.getDetailSummary().workCost;
			rentCostTotal += box.getDetailSummary().rentCost;
			totalCostTotal += box.getDetailSummary().totalCost;
			int typeId = getResources()
					.getIdentifier(
							getPackageName() + ":string/"
									+ box.getDetailSummary().type, null, null);
			boxDescription += "\n" + box.getDetailSummary().name + "("
					+ getString(typeId) + ") " + box.getDetailSummary().x + "x"
					+ box.getDetailSummary().y + "x" + box.getDetailSummary().z
					+ ";";

		}
		// end set box type text

		ImageView i = (ImageView) findViewById(R.id.box_res);
		int id = getResources().getIdentifier(
				getPackageName() + ":drawable/box1res", null, null);
		i.setImageResource(id);

		TextView t = (TextView) findViewById(R.id.xyz);
		t.setText(boxDescription);

		// material size and description
		detailsDescription = getString(R.string.edge_long) + "("
				+ dbWorker.boxSettings.getSettings(SettingsType.EDGE_THICK)
				+ "mm) " + getFormattedMFromMM(edgeLongTotal) + " M ($"
				+ getSumFormatted(edgeCostTotal) + ")\n"
				+ getString(R.string.space) + "(" + getString(R.string.dsp)
				+ dbWorker.boxSettings.getSettings(SettingsType.DSP_THICK)
				+ ") " + getFormattedM2FromMM2(dspSquareTotal) + " M2 ($"
				+ getSumFormatted(dspCostTotal) + ")";
		detailsDescription += "\n" + getString(R.string.FURNITURE_COST) + ""
				+ getSumFormatted(furnitureCostTotal);
		// if (dbWorker.boxSettings.getSettings(SettingsType.HOURS_MAKE)!=0)
		detailsDescription += "\n" + getString(R.string.WORK_COST) + ""
				+ getSumFormatted(workCostTotal);
		// if (dbWorker.boxSettings.getSettings(SettingsType.RENT_COST)!=0)
		detailsDescription += "\n" + getString(R.string.RENT_COST) + ""
				+ getSumFormatted(rentCostTotal);

		// if
		// (dbWorker.boxSettings.settings.containsKey(SettingsType.REAR_DEEP))
		detailsDescription += "\n" + getString(R.string.space) + "("
				+ getString(R.string.dvp)
				+ dbWorker.boxSettings.getSettings(SettingsType.REAR_DEEP)
				+ ") " + getFormattedM2FromMM2(dvpSquareTotal) + " M2 ($"
				+ getSumFormatted(dvpCostTotal) + ")";
		// double total = totalCostTotal;
		detailsDescription += "\n" + getString(R.string.total20) + "~$"
				+ getSumFormatted(totalCostTotal + (totalCostTotal * 0.2));
		detailsDescription += "\n";
		TextView c = (TextView) findViewById(R.id.common);
		c.setText(detailsDescription);

	}

	public void deleteBox(View view) {
		confirmDelete();
	}

	public void createNew(View view) {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	private void loadBox() {
		// DbWorker dbWorker = new DbWorker(this);
		dbWorker.readBoxes();
		DbWorker.Element[] el = new DbWorker.Element[dbWorker.boxes.size()];
		ids.clear();
		List<String> names = new ArrayList<String>();
		int i = 0;
		String selectedList = null;
		for (Iterator<DbWorker.Element> iterator = dbWorker.boxes.iterator(); iterator
				.hasNext();) {
			DbWorker.Element x = iterator.next();
			ids.add(Integer.toString(x.getId()));
			Log.d(LOG_TAG, "idxxxyy = " + Integer.toString(x.getId()));
			if (selectedIds.contains(x.getId())) {
				if (selectedList != null)
					selectedList += ",";
				else
					selectedList = "";
				selectedList += x.getId();
			}
			// idss.add(Integer.toString(x.getId()));
			names.add(x.getmText());
			el[i++] = x;

		}
		// /Log.d(LOG_TAG, "selectedList = " + selectedList);

		// selectedIds
		MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.boxes_multispinner);

		multiSpinner.setItems(names, ids, selectedList, "all", this);

		ArrayAdapter<DbWorker.Element> adapter = new ArrayAdapter<DbWorker.Element>(
				this, android.R.layout.simple_spinner_item, el);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		final Spinner spinner = (Spinner) findViewById(R.id.boxes_spinner);
		spinner.setAdapter(adapter);
		spinner.setPrompt(getString(R.string.element));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				DbWorker.Element st = (DbWorker.Element) spinner
						.getSelectedItem();
				Toast.makeText(getBaseContext(), st.getmText(),
						Toast.LENGTH_SHORT).show();
				selectedId = st.getId();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void showDetails() {
		// readSettings();
		// fill box by details
		box = new BoxesFactory().getBox(dbWorker.type);
		box.create(dbWorker);
		box.createDetailSummary(dbWorker);
		writeDetails();

		// set box type text
		int typeId = getResources().getIdentifier(
				getPackageName() + ":string/" + box.getDetailSummary().type,
				null, null);
		String description = getString(R.string.box_type) + ": "
				+ getString(typeId) + "\n" + getString(R.string.name) + ": "
				+ box.getDetailSummary().name + "\n" + "X="
				+ box.getDetailSummary().x + " mm\nY="
				+ box.getDetailSummary().y + " mm\nZ="
				+ box.getDetailSummary().z + " mm\n" + getString(R.string.edge)
				+ " 1 mm";
		boxDescription = description;
		// end set box type text

		ImageView i = (ImageView) findViewById(R.id.box_res);
		int id = getResources().getIdentifier(
				getPackageName() + ":drawable/" + box.getDetailSummary().type
						+ "res", null, null);
		i.setImageResource(id);

		TextView t = (TextView) findViewById(R.id.xyz);
		t.setText(boxDescription);

		// material size and description
		detailsDescription = getString(R.string.edge_long)
				+ "("
				+ getSumFormatted(getCost(dbWorker.boxSettings
						.getSettings(SettingsType.EDGE_THICK))) + "mm) "
				+ getFormattedMFromMM(box.getDetailSummary().edgeLong)
				+ " M ($" + getSumFormatted(box.getDetailSummary().edgeCost)
				+ ")\n" + getString(R.string.space) + "("
				+ getString(R.string.dsp)
				+ dbWorker.boxSettings.getSettings(SettingsType.DSP_THICK)
				+ ") "
				+ getFormattedM2FromMM2(box.getDetailSummary().dspSquare)
				+ " M2 ($" + getSumFormatted(box.getDetailSummary().dspCost)
				+ ")";
		detailsDescription += "\n" + getString(R.string.FURNITURE_COST) + ""
				+ getSumFormatted(box.getDetailSummary().furnitureCost);
		// if (dbWorker.boxSettings.getSettings(SettingsType.HOURS_MAKE)!=0)
		detailsDescription += "\n" + getString(R.string.WORK_COST) + ""
				+ getSumFormatted(box.getDetailSummary().workCost);
		// if (dbWorker.boxSettings.getSettings(SettingsType.RENT_COST)!=0)
		detailsDescription += "\n" + getString(R.string.RENT_COST) + ""
				+ getSumFormatted(box.getDetailSummary().rentCost);

		if (dbWorker.boxSettings.settings.containsKey(SettingsType.REAR_DEEP))
			detailsDescription += "\n" + getString(R.string.space) + "("
					+ getString(R.string.dvp)
					+ dbWorker.boxSettings.getSettings(SettingsType.REAR_DEEP)
					+ ") "
					+ getFormattedM2FromMM2(box.getDetailSummary().dvpSquare)
					+ " M2 ($"
					+ getSumFormatted(box.getDetailSummary().dvpCost) + ")";
		double total = box.getDetailSummary().totalCost;
		detailsDescription += "\n" + getString(R.string.total20) + "~$"
				+ getSumFormatted(total + (total * 0.2));
		TextView c = (TextView) findViewById(R.id.common);
		c.setText(detailsDescription.trim());

	}

	public void writeCsv(View view) {
		writeCsv("details.csv");

	}

	public void sendEmail(View view) {
		writeCsv("details.csv");
		final Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("text/html");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT,
				getString(R.string.email_subject));
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html
				.fromHtml(new StringBuilder().append(
						String.format(getString(R.string.email_body),
								"<a href='" + getString(R.string.market_url)
										+ "'>" + getString(R.string.app_name)
										+ "</a>")).toString()));

		Uri uri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "details.csv"));
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(Intent.createChooser(emailIntent, "Email:"));
	}

	public static String getFormattedMFromMM(int inv) {
		String res = "0";
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		res = df.format(Double.valueOf(inv) / 1000);// Double.parseDouble(df.format(in_float));
		return res;
	}

	public static String getFormattedM2FromMM2(int inv) {
		String res = "0";
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		res = df.format(Double.valueOf(inv) / 1000000);// Double.parseDouble(df.format(in_float));
		return res;
	}

	public static double getMCost(int inv, int cost) {
		return (Double.valueOf(inv) / 1000) * (Double.valueOf(cost) / 100);
	}

	public static double getCost(int cost) {
		return (Double.valueOf(cost) / 100);
	}

	public static String getSumFormatted(double in) {
		String res = "0";
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		res = df.format(in);// Double.parseDouble(df.format(in_float));
		return res;
	}

	public static double getM2Cost(int inv, int cost) {
		return (Double.valueOf(inv) / 1000000) * (Double.valueOf(cost) / 100);
	}

	private void writeDetails() {
		TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
		int count = tableLayout.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = tableLayout.getChildAt(i);
			if (child instanceof TableRow && i != 0)
				((ViewGroup) child).removeAllViews();
		}
		for (Detail s : box.getDetails()) {
			int materialId = getResources()
					.getIdentifier(
							getPackageName() + ":string/" + s.getMaterial(),
							null, null);
			int id = getResources().getIdentifier(
					getPackageName() + ":string/" + s.getName().name(), null,
					null);
			Log.d(LOG_TAG, "description = " + s.description);
			addRow(getString(id)
					+ " "
					+ (s.description != null ? getString(getResources()
							.getIdentifier(
									getPackageName() + ":string/"
											+ s.description, null, null))
							+ " " + s.additional + " mm" : ""),
					getString(materialId), s.getWidth(), s.getLength(),
					s.getWidthEdge(), s.getLengthEdge(), s.getQuantity(),
					box.getDetailSummary().name);
		}
	}

	public void addRow(String cell0, String cell1, int cell2, int cell3,
			int cell4, int cell5, int cell6, String cell7) {
		TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TableRow tr = (TableRow) inflater.inflate(R.layout.row, null);
		TextView tv = (TextView) tr.getChildAt(0);
		tv.setText(cell0);
		tv = (TextView) tr.getChildAt(1);
		tv.setText(cell1);
		tv = (TextView) tr.getChildAt(2);
		tv.setText(Integer.toString(cell2));
		tv = (TextView) tr.getChildAt(3);
		tv.setText(Integer.toString(cell3));
		tv = (TextView) tr.getChildAt(4);
		tv.setText(Integer.toString(cell4));
		tv = (TextView) tr.getChildAt(5);
		tv.setText(Integer.toString(cell5));
		tv = (TextView) tr.getChildAt(6);
		tv.setText(Integer.toString(cell6));
		tv = (TextView) tr.getChildAt(7);
		tv.setText(cell7);
		tableLayout.addView(tr);
	}

	public void writeCsv(String strFile) {

		BufferedWriter out = null;
		try {
			/* 1. declare stringBuffer */
			StringBuffer oneLineStringBuffer = new StringBuffer();
			String row = "";
			int id = getResources().getIdentifier(
					getPackageName() + ":string/name", null, null);
			row += getString(id) + ";";
			id = getResources().getIdentifier(
					getPackageName() + ":string/material", null, null);
			row += getString(id) + ";";
			id = getResources().getIdentifier(
					getPackageName() + ":string/width", null, null);
			row += getString(id) + ";";
			id = getResources().getIdentifier(
					getPackageName() + ":string/length", null, null);
			row += getString(id) + ";";
			id = getResources().getIdentifier(
					getPackageName() + ":string/widthEdge", null, null);
			row += getString(id) + ";";
			id = getResources().getIdentifier(
					getPackageName() + ":string/lengthEdge", null, null);
			row += getString(id) + ";";
			id = getResources().getIdentifier(
					getPackageName() + ":string/quantity", null, null);
			row += getString(id);
			oneLineStringBuffer.append(row);
			oneLineStringBuffer.append("\n");
			if (projectMode) {
				for (Iterator<Integer> iterator = selectedIds.iterator(); iterator
						.hasNext();) {
					// x = iterator.next();
					dbWorker.boxId = iterator.next();
					dbWorker.readFromDb();
					// fill box by details
					box = new BoxesFactory().getBox(dbWorker.type);
					box.create(dbWorker);
					box.createDetailSummary(dbWorker);
					for (Detail d : box.getDetails()) {
						row = "";
						row += d.name
								+ " "
								+ (d.description != null ? getString(getResources()
										.getIdentifier(
												getPackageName() + ":string/"
														+ d.description, null,
												null))
										+ " " + d.additional + " mm" : "")
								+ ";";
						row += d.material + ";";
						row += d.width + ";";
						row += d.length + ";";
						row += d.widthEdge + ";";
						row += d.lengthEdge + ";";
						row += Integer.toString(d.quantity) + ";";
						row += box.getDetailSummary().name;
						oneLineStringBuffer.append(row);
						oneLineStringBuffer.append("\n");
					}
				}
			} else {
				// showDetails(); /* 2. append to stringBuffer */
				for (Detail d : box.getDetails()) {
					row = "";
					row += d.name
							+ " "
							+ (d.description != null ? getString(getResources()
									.getIdentifier(
											getPackageName() + ":string/"
													+ d.description, null, null))
									+ " " + d.additional + " mm"
									: "") + ";";
					row += d.material + ";";
					row += d.width + ";";
					row += d.length + ";";
					row += d.widthEdge + ";";
					row += d.lengthEdge + ";";
					row += Integer.toString(d.quantity);
					oneLineStringBuffer.append(row);
					oneLineStringBuffer.append("\n");
				}
			}
			oneLineStringBuffer.append(boxDescription);
			oneLineStringBuffer.append("\n");
			oneLineStringBuffer.append(detailsDescription);
			Log.d(LOG_TAG, "oneLineStringBuffer.toString()="
					+ oneLineStringBuffer.toString());
			File file = new File(Environment.getExternalStorageDirectory(),
					strFile);

			out = new BufferedWriter(new FileWriter(file.getAbsolutePath(),
					false));
			out.write(oneLineStringBuffer.toString());
			Toast.makeText(
					this,
					getString(R.string.file_save) + " "
							+ file.getAbsolutePath(), Toast.LENGTH_SHORT)
					.show();

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {

			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
	}

	private void confirmDelete() throws NotFoundException {
		new AlertDialog.Builder(this)
				.setTitle(getResources().getString(R.string.delete))
				.setMessage(getResources().getString(R.string.confirm_q))
				.setIcon(
						getResources().getDrawable(
								android.R.drawable.ic_dialog_alert))
				.setPositiveButton(getResources().getString(R.string.yes),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dbWorker.boxId = selectedId;
								dbWorker.deleteBox(selectedId);
								loadBox();
								dbWorker.boxId = 0;
								if (dbWorker.boxes.size() == 0) {
									Intent intent = new Intent();
									setResult(RESULT_OK, intent);
									finish();
								} else {
									dbWorker.readFromDb();
									showDetails();
								}
								// loadBox();

							}
						})
				.setNegativeButton(getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Do Something Here
							}
						}).show();
	}

	private void about() {
		Toast.makeText(this, "Author flywolf1978@gmail.com", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.settings:
			// quit();
			return true;
		case R.id.about:
			about();
			return true;
		case R.id.quit:
			quit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void quit() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mi = menu.add(0, 1, 0, R.string.settings);
		mi.setIntent(new Intent(this, Settings.class));
		return super.onCreateOptionsMenu(menu);
	}
}
