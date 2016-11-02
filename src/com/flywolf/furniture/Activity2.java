package com.flywolf.furniture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flywolf.boxcreator.R;
import com.flywolf.boxcreator.Settings;
import com.flywolf.furniture.CommonStatic.BoxesType;
import com.flywolf.furniture.CommonStatic.SettingsType;

public abstract class Activity2 extends Activity {
	// int x;
	// int y;
	// int z, quantity, shelfQuantity;
	// String name;
	CommonStatic.BoxesType boxType;
	EditText etName, etX, etY, etZ, etShelfQuantity, etQuantity;
	SharedPreferences sPref;
	final String BOX_TYPE = "box_type";
	// final String BOX_DESCRIPTION = "box_description";

	final String LOG_TAG = "myLogs";
	DbWorker dbWorker;
	public String[] dspSize;
	public String[] edgeSize;
	LinearLayout LLEnterText;
	int _intMyLineCount;

	private List<EditText> editTextList = new ArrayList<EditText>();
	private List<TextView> textviewList = new ArrayList<TextView>();
	private List<LinearLayout> linearlayoutList = new ArrayList<LinearLayout>();
	private List<SettingsType> settingsList = new LinkedList<SettingsType>();

	/** Called when the activity is first created. */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		etName = (EditText) findViewById(R.id.name);
		etX = (EditText) findViewById(R.id.x);
		etY = (EditText) findViewById(R.id.y);
		etZ = (EditText) findViewById(R.id.z);
		etShelfQuantity = (EditText) findViewById(R.id.shelf_quantity);
		etQuantity = (EditText) findViewById(R.id.quantity);

		Button previous = (Button) findViewById(R.id.previous);
		previous.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}

		});

		loadBoxType();
		// TextView t=(TextView)findViewById(R.id.xyz);
		// t.setText(getString(R.string.box_type)+": "+boxType);
		ImageView i = (ImageView) findViewById(R.id.box_res);
		// создаем объект для создания и управления версиями БД
		dbWorker = new DbWorker(this);
		Bundle b = getIntent().getExtras();
		dbWorker.boxId = b.getInt("id");
		int id;
		int typeId;
		if (dbWorker.boxId != 0) {
			dbWorker.readFromDb();
			id = getResources().getIdentifier(
					getPackageName() + ":drawable/" + dbWorker.type + "res",
					null, null);
			boxType = dbWorker.type;
			typeId = getResources().getIdentifier(
					getPackageName() + ":string/" + dbWorker.type, null, null);
		} else {
			typeId = getResources().getIdentifier(
					getPackageName() + ":string/" + boxType, null, null);
			id = getResources().getIdentifier(
					getPackageName() + ":drawable/" + boxType + "res", null,
					null);
			Box box = new BoxesFactory().getBox(boxType);
			dbWorker.x = box.getDefaultX();
			dbWorker.y = box.getDefaultY();
			dbWorker.z = box.getDefaultZ();
			dbWorker.shelfQuantity = box.getDefaultRackQuantity();

		}
		TextView tv = (TextView) findViewById(R.id.box_description);
		tv.setText(getString(typeId));
		i.setImageResource(id);
		etName.setText(dbWorker.name);
		etX.setText(Integer.toString(dbWorker.x));
		etY.setText(Integer.toString(dbWorker.y));
		etZ.setText(Integer.toString(dbWorker.z));
		etQuantity.setText(Integer.toString(dbWorker.quantity));
		etShelfQuantity.setText(Integer.toString(dbWorker.shelfQuantity));
		loadBoxSettings();

	}

	private void loadBoxSettings() {
		// add dsp size spinner
		ArrayAdapter<String> adapterDspSize = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, dspSize);
		adapterDspSize
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		int defaultDspId = 4;

		Spinner spinnerDspSize = (Spinner) findViewById(R.id.dsp_spinner);
		spinnerDspSize.setAdapter(adapterDspSize);
		spinnerDspSize.setPrompt("Title");
		spinnerDspSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// end dsp size spinner
		// add edge size spinner

		ArrayAdapter<String> adapterEdgeSize = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, edgeSize);
		adapterEdgeSize
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		int defaultEdgeId = 1;

		Spinner spinnerEdgeSize = (Spinner) findViewById(R.id.edge_spinner);
		spinnerEdgeSize.setAdapter(adapterEdgeSize);
		spinnerEdgeSize.setPrompt("Title");
		spinnerEdgeSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// end edge size spinner

		if (Activity1.low) {
			// findViewById(R.id.dsp_spinner).setEnabled(false);
			Button settings = (Button) findViewById(R.id.save_settings);
			settings.setText(R.string.download_pro);
		}
		if (dbWorker.boxId == 0) {
			spinnerDspSize.setSelection(defaultDspId);
			spinnerEdgeSize.setSelection(defaultEdgeId);

			// read preferens
			dbWorker.boxSettings.ReadSettings();
			dbWorker.boxSettings.settings = new LinkedHashMap<SettingsType, Integer>();
			dbWorker.boxSettings.settings.put(SettingsType.DSP_THICK,
					BoxSettings.getBoxSettings().settingsDefault
							.get(SettingsType.DSP_THICK));
			// boolean facade = false;
			settingsList.addAll(new BoxesFactory().getBox(boxType)
					.getSettingsTypeList());

			if ((boxType == BoxesType.box17) || (boxType == BoxesType.box18)
					|| (boxType == BoxesType.box19))
				settingsList.add(SettingsType.PULL_OUT_DRAWER_HEIGHT);

			if ((boxType == BoxesType.box18) || (boxType == BoxesType.box19)
					|| (boxType == BoxesType.box20)
					|| (boxType == BoxesType.box21)) {
				settingsList.add(SettingsType.PULL_OUT_DRAWER_GUIDES_INDENT);
				settingsList.add(SettingsType.PULL_OUT_DRAWER_HEIGHT_INDENT);
			}
			// add costs
			settingsList.add(SettingsType.DSP_COST);
			settingsList.add(SettingsType.EDGE_COST);
			if (settingsList.contains(SettingsType.REAR_DEEP))
				settingsList.add(SettingsType.REAR_COST);

			settingsList.add(SettingsType.FURNITURE_COST);
			settingsList.add(SettingsType.HOUR_COST);
			settingsList.add(SettingsType.HOURS_MAKE);
			settingsList.add(SettingsType.RENT_COST);

			for (Iterator<SettingsType> iterator = settingsList.iterator(); iterator
					.hasNext();) {
				SettingsType x = iterator.next();
				dbWorker.boxSettings.settings.put(x,
						BoxSettings.getBoxSettings().settingsDefault.get(x));
				// do some stuff
			}
		}
		for (Map.Entry<SettingsType, Integer> entry : dbWorker.boxSettings.settings
				.entrySet()) {
			if (entry.getKey() == SettingsType.DSP_THICK) {
				for (int i = 0; i < dspSize.length; i++)
					if (Integer.parseInt(dspSize[i].toString()) == entry
							.getValue()) {
						spinnerDspSize.setSelection(i);
						break;
					}

			} else if (entry.getKey() == SettingsType.EDGE_THICK) {
				for (int i = 0; i < edgeSize.length; i++)

					if (((Double) (Double.parseDouble(edgeSize[i].toString()) * 100))
							.intValue() == entry.getValue()) {
						spinnerEdgeSize.setSelection(i);
						break;
					}

			} else {
				LLEnterText = (LinearLayout) findViewById(R.id.dynamic_settings_layout);
				LLEnterText.addView(linearlayout(entry.getKey(),
						entry.getValue()));
			}
		}

	}

	private EditText editText(int _intID, SettingsType type, int value) {
		EditText editText = new EditText(this);
		editText.setId(_intID);
		editText.setHint("mm");
		//editText.setTypeface(Typeface.DEFAULT_BOLD);
		
		editText.setTag(type.name());
		LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		//layoutParams.setMargins(5, 0, 5, 0);
		editText.setLayoutParams(layoutParams);
		if (type == SettingsType.DSP_COST || type == SettingsType.REAR_COST
				|| type == SettingsType.EDGE_COST
				|| type == SettingsType.FURNITURE_COST
				|| type == SettingsType.HOUR_COST
				|| type == SettingsType.RENT_COST) {
			editText.setText(Activity3.getSumFormatted(Double.valueOf(value) / 100));
			editText.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		} else {
			editText.setText(Integer.toString(value));
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		if (Activity1.low) {
			editText.setEnabled(false);
		}
		editTextList.add(editText);
		return editText;
	}

	private TextView textView(int _intID) {
		TextView txtviewAll = new TextView(this);
		txtviewAll.setId(_intID);
		txtviewAll.setText(_intID);
		txtviewAll.setTextColor(Color.BLACK);
		LayoutParams layoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(5, 0, 5, 0);
		txtviewAll.setLayoutParams(layoutParams);
		textviewList.add(txtviewAll);
		return txtviewAll;
	}

	private LinearLayout linearlayout(SettingsType type, int value) {
		int id = getResources().getIdentifier(
				getPackageName() + ":string/" + type.name(), null, null);
		LinearLayout LLMain = new LinearLayout(this);
		LLMain.setId(id);
		LLMain.addView(textView(id));
		LLMain.addView(editText(id, type, value));
		LLMain.setOrientation(LinearLayout.HORIZONTAL);
		linearlayoutList.add(LLMain);
		return LLMain;

	}

	public void saveSettings(View view) {
		if (Activity1.low) {
			Intent browse = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.flywolf.chipboardfurniture"));

			startActivity(browse);

		} else {
			next(view);
		}
	}

	public void saveSettings() {
		Spinner spinnerDspThick = (Spinner) findViewById(R.id.dsp_spinner);
		Spinner spinnerEdgeThick = (Spinner) findViewById(R.id.edge_spinner);
		dbWorker.boxSettings.saveBoxSettings(
				SettingsType.valueOf(SettingsType.DSP_THICK.toString()),
				Integer.parseInt(spinnerDspThick.getSelectedItem().toString()));
		dbWorker.boxSettings.saveBoxSettings(SettingsType
				.valueOf(SettingsType.EDGE_THICK.toString()),
				((Double) (Double.parseDouble(spinnerEdgeThick
						.getSelectedItem().toString()) * 100)).intValue());
		for (EditText editText : editTextList) {
			SettingsType type = SettingsType.valueOf(editText.getTag()
					.toString());
			if (type == SettingsType.DSP_COST || type == SettingsType.REAR_COST
					|| type == SettingsType.EDGE_COST
					|| type == SettingsType.FURNITURE_COST
					|| type == SettingsType.HOUR_COST
					|| type == SettingsType.RENT_COST)
				dbWorker.boxSettings.saveBoxSettings(type, ((Double) (Double
						.parseDouble(editText.getText().toString()) * 100))
						.intValue());
			else
				dbWorker.boxSettings.saveBoxSettings(type,
						Integer.parseInt(editText.getText().toString()));
		}
		// Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT)
		// .show();

	}

	void loadBoxType() {
		sPref = getSharedPreferences("BoxPref", MODE_PRIVATE);
		String savedText = sPref.getString(BOX_TYPE, "");
		boxType = BoxesType.valueOf(savedText);
	}

	public void next(View view) {
		try {
			saveSettings();
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return;
		}
		dbWorker.name = ((EditText) findViewById(R.id.name)).getText()
				.toString();
		dbWorker.type = boxType;
		dbWorker.x = Integer.parseInt(etX.getText().toString()
				.contentEquals("") ? "0" : etX.getText().toString());
		dbWorker.y = Integer.parseInt(etY.getText().toString()
				.contentEquals("") ? "0" : etY.getText().toString());
		dbWorker.z = Integer.parseInt(etZ.getText().toString()
				.contentEquals("") ? "0" : etZ.getText().toString());
		dbWorker.quantity = Integer.parseInt(etQuantity.getText().toString()
				.contentEquals("") ? "0" : etQuantity.getText().toString());
		dbWorker.shelfQuantity = Integer.parseInt(etShelfQuantity.getText()
				.toString().contentEquals("") ? "0" : etShelfQuantity.getText()
				.toString());
		// TextView t=(TextView)findViewById(R.id.xyz);
		if (dbWorker.quantity == 0 || dbWorker.x == 0 || dbWorker.y == 0
				|| dbWorker.z == 0 || dbWorker.name.contentEquals("")) {
			Toast.makeText(this, getString(R.string.enter_values),
					Toast.LENGTH_SHORT).show();
			return;
		}

		dbWorker.addToDb();
		Intent myIntent = new Intent(view.getContext(), Activity1.activity3);
		startActivityForResult(myIntent, 0);
		finish();
	}

	public void showSettings(View view) {
		View sv = findViewById(R.id.settings_layout);
		if (sv.getVisibility() == View.VISIBLE) {
			findViewById(R.id.settings_layout).setVisibility(View.INVISIBLE);
		} else
			findViewById(R.id.settings_layout).setVisibility(View.VISIBLE);
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
