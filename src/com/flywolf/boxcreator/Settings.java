package com.flywolf.boxcreator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

public class Settings extends PreferenceActivity {

	CheckBoxPreference chb3;
	PreferenceCategory categ2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Activity1.low) {
			Intent browse = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.flywolf.chipboardfurniturepro"));

			startActivity(browse);
		} else {
			addPreferencesFromResource(R.xml.pref);
		}

	}
}
