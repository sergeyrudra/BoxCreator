package com.flywolf.furniture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.flywolf.furniture.CommonStatic.BoxSetting;
import com.flywolf.furniture.CommonStatic.SettingsType;

public class BoxSettings {

	final String LOG_TAG = "myLogs";
	private static BoxSettings boxSettings;
	LinkedHashMap<SettingsType, Integer> settings;
	HashMap<SettingsType, Integer> settingsDefault;


	// singletone
	public static BoxSettings getBoxSettings() {
		if (boxSettings == null) {
			boxSettings = new BoxSettings();
		}
		return boxSettings;

	}

	public Integer getSettings(SettingsType key) {
		if (settings.containsKey(key))
			return settings.get(key);
		else
			return settingsDefault.get(key);
	}

	public BoxSettings() {
		ReadSettings();
	}

	public void ReadSettings() {
		SharedPreferences sp;
		sp = PreferenceManager.getDefaultSharedPreferences(Activity1.ma.getApplicationContext());
	    // полная очистка настроек
	    // sp.edit().clear().commit();
		settingsDefault = new HashMap<SettingsType, Integer>();
		settingsDefault.put(SettingsType.DSP_THICK, Integer.parseInt(sp.getString("dsp_thick", "16").toString()));
		settingsDefault.put(SettingsType.EDGE_THICK,  ((Double) (Double
				.parseDouble(sp.getString("EDGE_THICK", "0").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.SOCLE_Y, Integer.parseInt(sp.getString("SOCLE_Y", "60").toString()));
		settingsDefault.put(SettingsType.FACADE_CLEARANCE, Integer.parseInt(sp.getString("FACADE_CLEARANCE", "4").toString()));
		settingsDefault.put(SettingsType.REAR_DEEP, Integer.parseInt(sp.getString("REAR_DEEP", "4").toString()));
		settingsDefault.put(SettingsType.DSP_COST,  ((Double) (Double
				.parseDouble(sp.getString("DSP_COST", "10").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.REAR_COST, ((Double) (Double
				.parseDouble(sp.getString("REAR_COST", "1.20").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.EDGE_COST, ((Double) (Double
				.parseDouble(sp.getString("EDGE_COST", "0.25").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.MARGIN_COST, Integer.parseInt(sp.getString("MARGIN_COST", "20").toString()));
		settingsDefault.put(SettingsType.LEFT_PART_X, 600);
		settingsDefault.put(SettingsType.RIGHT_PART_X, 600);
		settingsDefault.put(SettingsType.RACK_DEEP, 20);
		settingsDefault.put(SettingsType.REAR_MARG, 5);
		settingsDefault.put(SettingsType.TABLE_LEDGE_X, 20);
		settingsDefault.put(SettingsType.TABLE_LEDGE_Z, 40);
		settingsDefault.put(SettingsType.COMPUTER_X, 240);
		settingsDefault.put(SettingsType.STRUT_WIDTH, 200);
		settingsDefault.put(SettingsType.CLOSET_DEEP, 100);
		settingsDefault.put(SettingsType.MEZZANINE_Y, 350);
		settingsDefault.put(SettingsType.PANEL_Y, 100);
		settingsDefault.put(SettingsType.FURNITURE_COST,  ((Double) (Double
				.parseDouble(sp.getString("FURNITURE_COST", "0").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.HOUR_COST,  ((Double) (Double
				.parseDouble(sp.getString("HOUR_COST", "0").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.RENT_COST,  ((Double) (Double
				.parseDouble(sp.getString("RENT_COST", "0").toString()) * 100))
				.intValue());
		settingsDefault.put(SettingsType.HOURS_MAKE, Integer.parseInt(sp.getString("HOURS_MAKE", "1").toString()));
		settingsDefault.put(SettingsType.PULL_OUT_DRAWER_GUIDES_INDENT, 13);
		settingsDefault.put(SettingsType.PULL_OUT_DRAWER_HEIGHT_INDENT, 40);
		settingsDefault.put(SettingsType.PULL_OUT_DRAWER_HEIGHT, 150);
		}
	public void readSettings(ArrayList<BoxSetting> bss) {
		if (settings != null)
			settings.clear();
		else
			settings = new LinkedHashMap<SettingsType, Integer>();
		Iterator<BoxSetting> it = bss.iterator();
		while (it.hasNext()) {
			BoxSetting bs = it.next();
			settings.put(bs.type, bs.value);
		}
	}

	public LinkedHashMap<SettingsType, Integer> getSettings() {
		return settings;
	}

	public void setSettings(LinkedHashMap<SettingsType, Integer> settings) {
		this.settings = settings;
	}
	
	public void saveBoxSettings(SettingsType type, int value) {
		settings.put(type, value);
//		for (Iterator<BoxSetting> iterator = settings.iterator(); iterator
//				.hasNext();) {
//
//			BoxSetting x = iterator.next();
//			if (x.type == type) {
//				x.value = value;
//			}
//		}
	}
}
