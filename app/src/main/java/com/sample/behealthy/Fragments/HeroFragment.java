package com.sample.behealthy.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sample.behealthy.R;
import com.sample.behealthy.models.User;

public class HeroFragment extends Fragment {

	TextView nameTV;
	TextView lvlTV;
	TextView lvlProgressTV;
	ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero, container, false);

		nameTV = rootView.findViewById(R.id.name);
		lvlTV = rootView.findViewById(R.id.level_title);
		lvlProgressTV = rootView.findViewById(R.id.level_progres);
		progressBar = rootView.findViewById(R.id.progressBar);

		update();

		return rootView;
	}

	@SuppressLint("DefaultLocale")
	public void update() {
		User currentData = User.Companion.getInstance(getActivity());
		
		int until_level = (currentData.getLvl() + 1) * 1000;
		int remaining = until_level - currentData.getExp();
		int perecnt = 100 - (remaining == 0 ? 0 : remaining / 10);

		nameTV.setText(currentData.getLogin());
		lvlTV.setText(String.format("%d   LVL", currentData.getLvl()));
		lvlProgressTV.setText(String.format("brakuje   %d   exp   do   awansu", remaining));
		progressBar.setProgress(perecnt);
	}
}

