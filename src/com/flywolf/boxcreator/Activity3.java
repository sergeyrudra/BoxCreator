package com.flywolf.boxcreator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.flywolf.boxcreator.Activity1;
import com.flywolf.boxcreator.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class Activity3 extends com.flywolf.furniture.Activity3 {

	private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Создание adView
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-5241900636168877/6769659947");
		adView.setAdSize(AdSize.BANNER);// SMART_BANNER);
		AdRequest adRequest = new AdRequest.Builder().build();

		LinearLayout layout = (LinearLayout) findViewById(R.id.top_banner);
		layout.addView(adView);
		adView.loadAd(adRequest);
		Activity1.displayInterstitial();
		
		layout = (LinearLayout) findViewById(R.id.project);
		layout.setVisibility(View.INVISIBLE);
		LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,
				0);
		//layoutParams.setMargins(5, 0, 5, 0);
		layout.setLayoutParams(layoutParams);
		ImageButton ib= (ImageButton) findViewById(R.id.go_box);
		ib.setVisibility(View.INVISIBLE);
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