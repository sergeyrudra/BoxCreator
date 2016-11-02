package com.flywolf.boxcreator;

import com.flywolf.boxcreator.Activity1;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.os.Bundle;
import android.view.View;

public class Activity2 extends com.flywolf.furniture.Activity2 {
	final String LOG_TAG = "myLogs";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dspSize = new String[] { "16", "18" };
		edgeSize = new String[]  { "0.45", "1"};
		Activity1.displayInterstitial();
		super.onCreate(savedInstanceState);
		
	}
	
	
}
