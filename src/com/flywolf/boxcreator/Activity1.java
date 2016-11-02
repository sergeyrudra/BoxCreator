package com.flywolf.boxcreator;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flywolf.furniture.CommonStatic.BoxesType;
import com.flywolf.boxcreator.R;
import com.flywolf.boxcreator.Activity1;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.analytics.tracking.android.EasyTracker;

public class Activity1 extends com.flywolf.furniture.Activity1 {
	private AdView adView;
	public static InterstitialAd interstitial;
	public static boolean showInterstitial;
	final String LOG_TAG = "myLogs";

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		low = true;
		DB_NAME = "chipboard_furniture";
		DB_VERSION = 3;
		boxes = new BoxesType[] { BoxesType.box13, BoxesType.box14,
				BoxesType.box18, BoxesType.box19, BoxesType.box20,
				BoxesType.box21, BoxesType.box1, BoxesType.box2,
				BoxesType.box3, BoxesType.box4, BoxesType.box5, BoxesType.box6,
				BoxesType.box7, BoxesType.box8, BoxesType.box9, BoxesType.box10, BoxesType.box11 };
		activity1 = com.flywolf.boxcreator.Activity1.class;
		activity2 = com.flywolf.boxcreator.Activity2.class;
		activity3 = com.flywolf.boxcreator.Activity3.class;
		super.onCreate(savedInstanceState);
		if (checkInternetConnection()) {

			// Создание adView
			adView = new AdView(this);
			adView.setAdUnitId("ca-app-pub-5241900636168877/6769659947");
			adView.setAdSize(AdSize.BANNER);// SMART_BANNER);
			AdRequest adRequest = new AdRequest.Builder().build();

			LinearLayout layout = (LinearLayout) findViewById(R.id.top_banner);
			layout.addView(adView);
			adView.loadAd(adRequest);
			if (showInterstitial) 
			{
			interstitial = new InterstitialAd(this);
			interstitial
					.setAdUnitId("ca-app-pub-5241900636168877/3350442348");
			interstitial.loadAd(adRequest);
			}

		} else {
			LinearLayout layout = (LinearLayout) findViewById(R.id.top_banner);
			ImageView proBanner = new ImageView(this);
			// proBanner.setText(R.string.app_name);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER_HORIZONTAL;
			int id = getResources().getIdentifier(
					Activity1.PACKAGE_NAME + ":string/banner_name", null, null);
			id = getResources().getIdentifier(
					Activity1.PACKAGE_NAME + ":drawable/"+getString(id), null, null);
			proBanner.setImageResource(id);
			proBanner.setLayoutParams(params);
			proBanner.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	Intent browse = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("https://play.google.com/store/apps/details?id=com.flywolf.chipboardfurniturepro"));

					startActivity(browse);
			     }
			 });
			layout.addView(proBanner);
		}

	}
	public  boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			// Log.v(TAG, "Internet Connection Not Present");
			return false;
		}
	}
	public static void displayInterstitial() {
		
		if (showInterstitial&&interstitial != null && interstitial.isLoaded()) {
			interstitial.show();
		}
	}	
	
	@Override
	public void goBox(View view) {
		displayInterstitial();
		super.goBox(view);
	}	
	
	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}

	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}

		super.onPause();
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}

		super.onDestroy();
	}
}