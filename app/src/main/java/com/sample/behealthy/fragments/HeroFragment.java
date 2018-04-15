package com.sample.behealthy.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sample.behealthy.R;
import com.sample.behealthy.dialogs.TipOfTheDayDialog;
import com.sample.behealthy.events.RefreshEvent;
import com.sample.behealthy.events.UpdateEvent;
import com.sample.behealthy.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HeroFragment extends Fragment {

	final String TIP_DIALOGTAG = "tip_dialog";

	TextView nameTV;
	TextView titleTV;
	TextView lvlTV;
	TextView lvlProgressTV;
	ProgressBar progressBar;
	ImageView playerIV;
	ImageView tipOfTheDayIV;

	boolean animationStarted = false;

	int imageCounter = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero, container, false);

		nameTV = rootView.findViewById(R.id.name);
		titleTV = rootView.findViewById(R.id.title);
		lvlTV = rootView.findViewById(R.id.level_title);
		lvlProgressTV = rootView.findViewById(R.id.level_progres);
		progressBar = rootView.findViewById(R.id.progressBar);
		playerIV = rootView.findViewById(R.id.hero_icon);
		tipOfTheDayIV = rootView.findViewById(R.id.tip_of_the_day);

		nameTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new RefreshEvent());
			}
		});

		update();

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!animationStarted) {
			animationStarted = true;
			startAnimation();
		}
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this);

			UpdateEvent stickyEvent = EventBus.getDefault().getStickyEvent(UpdateEvent.class);
			if (stickyEvent != null) {
				update();
			}
		}
	}

	@Override
	public void onStop() {
		EventBus.getDefault().unregister(this);
		super.onStop();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUpdateEvent(UpdateEvent event) {
		update();
	}

	private void showTipOfTheDay() {
		DialogFragment newFragment = new TipOfTheDayDialog();
		newFragment.show(getFragmentManager(), TIP_DIALOGTAG);
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

		tipOfTheDayIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTipOfTheDay();
			}
		});

		switch (currentData.getLvl()) {
			case 0:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_fat));
				titleTV.setText("obibok");
				break;
			case 1:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_semifat));
				titleTV.setText("nowicjusz");
				break;
			case 2:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_lean));
				titleTV.setText("atleta");
				break;
			case 3:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_strong));
				titleTV.setText("sportowiec");
				break;
			default:
				playerIV.setImageDrawable(getResources().getDrawable(R.drawable.character_stronger));
				titleTV.setText("olimpijczyk");
				break;
		}
	}

	private void startAnimation() {
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				tipOfTheDayIV.setImageLevel(imageCounter);
				if (imageCounter != 2) {
					imageCounter++;
					handler.postDelayed(this, 300);
				} else {
					imageCounter = 0;
					handler.postDelayed(this, 2000);
				}
			}
		};
		handler.postDelayed(runnable, 0);
	}
}

