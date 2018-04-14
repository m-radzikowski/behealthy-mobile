package com.sample.behealthy.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sample.behealthy.R;
import com.sample.behealthy.events.UpdateEvent;
import com.sample.behealthy.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HeroFragment extends Fragment {

	TextView nameTV;
	TextView lvlTV;
	TextView lvlProgressTV;
	ProgressBar progressBar;
	ImageView playerIV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero, container, false);

		nameTV = rootView.findViewById(R.id.name);
		lvlTV = rootView.findViewById(R.id.level_title);
		lvlProgressTV = rootView.findViewById(R.id.level_progres);
		progressBar = rootView.findViewById(R.id.progressBar);
		playerIV = rootView.findViewById(R.id.hero_icon);

		update();

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUpdateEvent(UpdateEvent event) {
		update();
	}

	@SuppressLint("DefaultLocale")
	public void update() {
		User currentData = User.Companion.getInstance(getActivity());

		int until_level = (currentData.getLvl() + 1) * 1000;
		int remaining = until_level - currentData.getExp();
		int percent = 100 - (remaining == 0 ? 0 : remaining / 10);

		nameTV.setText(currentData.getLogin());
		lvlTV.setText(String.format("%d   LVL", currentData.getLvl()));
		lvlProgressTV.setText(String.format("brakuje   %d   exp   do   awansu", remaining));
		progressBar.setProgress(percent);

		switch (currentData.getLvl()) {
			case 0:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_fat));
				break;
			case 1:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_semifat));
				break;
			case 2:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_lean));
				break;
			case 3:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_strong));
				break;
			default:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_stronger));
				break;
		}
	}
}

