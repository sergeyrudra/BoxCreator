package com.flywolf.furniture;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flywolf.boxcreator.R;

public class PageFragment extends Fragment {

	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

	int pageNumber;
	int backColor;
	View view;

	static PageFragment newInstance(int page) {
		PageFragment pageFragment = new PageFragment();
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
		pageFragment.setArguments(arguments);
		return pageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

		Random rnd = new Random();
		backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256),
				rnd.nextInt(256));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment, null);
		// int maxPages=13;

		for (int i = 1; i <= 4; i++) {
			int iNum = (pageNumber * 4) + i;
			int id = getResources().getIdentifier("box" + i, "id",
					Activity1.PACKAGE_NAME);
			ImageView iv = (ImageView) view.findViewById(id);
			if (Activity1.boxes.length >= iNum) {

				id = getResources().getIdentifier(
						Activity1.boxes[iNum - 1].name(), "drawable",
						Activity1.PACKAGE_NAME);
				iv.setImageResource(id);
				iv.setTag(Activity1.boxes[iNum - 1].name());
			} else {
				iv.setVisibility(View.INVISIBLE);
				// iv.setImageResource(R.drawable.delete);
				// iv.setTag("box" + iNum);
			}
		}
		TextView tvPage = (TextView) view.findViewById(R.id.page_num);
		tvPage.setText((pageNumber + 1) + "/" + Activity1.PAGE_COUNT);
		tvPage.setBackgroundColor(backColor);

		return view;
	}

}