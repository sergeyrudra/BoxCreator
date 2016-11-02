package com.flywolf.furniture;

import java.util.Iterator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.flywolf.boxcreator.R;
import com.flywolf.boxcreator.Settings;
import com.flywolf.furniture.CommonStatic.BoxesType;

public abstract class Activity1 extends FragmentActivity {
	ViewPager pager;
	PagerAdapter pagerAdapter;
	SharedPreferences sPref;
	final String BOX_TYPE = "box_type";
	static final String TAG = "myLogs";
	public static int PAGE_COUNT;
	int selectedId;
	public static String DB_NAME;
	public static int DB_VERSION;
	public static String PACKAGE_NAME;
	public int pagePosition;
	int openCount;
	boolean estimated;
	final String OPEN_COUNT = "open_count";
	final String ESTIMATED = "estimated";
	public static Class<?> activity1;
	public static Class<?> activity2;
	public static Class<?> activity3;
	public static boolean low;
	public static BoxesType[] boxes;
	static Activity1 ma;

	void checkOpenCount() {
		sPref = getSharedPreferences("ChipboardFurniturePref", MODE_PRIVATE);
		// sPref = getPreferences(MODE_PRIVATE);
		estimated = sPref.getBoolean(ESTIMATED, false);
		if (!estimated) {
			openCount = sPref.getInt(OPEN_COUNT, 0);
			openCount++;
			if (openCount > 7) {
				openCount = 0;
				new AlertDialog.Builder(this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(getString(R.string.information))
						.setMessage(getString(R.string.add_estimate))
						.setPositiveButton(getString(R.string.ok_no_problem),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Editor ed = sPref.edit();
										ed.putBoolean(ESTIMATED, true);
										ed.commit();
										Intent browse = new Intent(
												Intent.ACTION_VIEW,
												Uri.parse("https://play.google.com/store/apps/details?id=com.flywolf.chipboardfurniture"));

										startActivity(browse);

										// finish();
									}

								})
						.setNegativeButton(getString(R.string.next_time), null)
						.show();
			}
			Editor ed = sPref.edit();
			ed.putInt(OPEN_COUNT, openCount);
			// ed.putBoolean(ESTIMATED, estimated);
			ed.commit();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ma=this;
		Double d= (double) (boxes.length/4);
		PAGE_COUNT = d.intValue()+(boxes.length%4>0?1:0);
		if (low)
			checkOpenCount();
		setContentView(R.layout.main1);
		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		PACKAGE_NAME = getApplicationContext().getPackageName();

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Log.d(TAG, "onPageSelected, position = " + position);
				pagePosition = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadBoxes();
		// Normal case behavior follows
	}

	// String[] dspSize = { "10", "16", "18", "22", "25" };
	private void loadBoxes() {
		DbWorker dbWorker = new DbWorker(this);
		dbWorker.readBoxes();
		DbWorker.Element[] el = new DbWorker.Element[dbWorker.boxes.size()];
		int i = 0;
		for (Iterator<DbWorker.Element> iterator = dbWorker.boxes.iterator(); iterator
				.hasNext();) {
			DbWorker.Element x = iterator.next();
			el[i++] = x;

		}
		if (i == 0) {
			findViewById(R.id.boxes_spinner).setVisibility(View.INVISIBLE);
			findViewById(R.id.go_box).setVisibility(View.INVISIBLE);
			return;
		}

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

	public void goBox(View view) {
		Intent myIntent = new Intent(view.getContext(), activity2);
		Bundle b = new Bundle();
		b.putInt("id", selectedId); // Your id
		myIntent.putExtras(b); // Put your id to your next Intent
		startActivity(myIntent);
		// finish();
	}



	public void сlickBox(View view) {
		Box box=new BoxesFactory().getBox(BoxesType.valueOf(view.getTag().toString()));
		if (box.isProElement() && low)
		{
			Intent browse = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.flywolf.chipboardfurniturepro"));

			startActivity(browse);
		}
		else
		{
			
		saveBoxType(view.getTag().toString());
		next(view);
		}
	}

	void saveBoxType(String boxType) {
		sPref = getSharedPreferences("BoxPref", MODE_PRIVATE);
		Editor ed = sPref.edit();
		ed.putString(BOX_TYPE, boxType);
		ed.commit();
	}

	private void next(View view) {
		Intent myIntent = new Intent(view.getContext(), activity2);
		myIntent.putExtra("id", 0);
		startActivity(myIntent);
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return PageFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}

	}
	private void about() {
		Toast.makeText(this,
				"Author flywolf1978@gmail.com",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.settings:
			//quit();
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
