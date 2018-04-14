package com.sample.behealthy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.behealthy.R;

public class QuestsFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
		Bundle args = getArguments();
		((TextView) rootView.findViewById(android.R.id.text1)).setText(
			getString(R.string.quest_section));
		return rootView;
	}
}