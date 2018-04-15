package com.sample.behealthy.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.events.UpdateEvent;
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.models.User;
import com.sample.behealthy.widgets.QuestArrayAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestsFragment extends Fragment {

	APIInterface apiInterface;
	ListView listview;

	TextView textTimer;

	Handler handler = new Handler();
	int delay = 1000; //milliseconds

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_quests, container, false);
		apiInterface = APIClient.getClient().create(APIInterface.class);
		listview = rootView.findViewById(R.id.listView1);
		textTimer = rootView.findViewById(R.id.timer);

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		getDailyQuest(listview);

		handler.postDelayed(new Runnable() {
			@SuppressLint("DefaultLocale")
			public void run() {
				Calendar rightNow = Calendar.getInstance();
				int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
				int currentMinute = rightNow.get(Calendar.MINUTE);

				textTimer.setText(String.format("Pozostalo   %02d : %02d",
					23 - currentHour,
					59 - currentMinute
				));

				handler.postDelayed(this, delay);
			}
		}, delay);

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

	public void update() {
		getDailyQuest(listview);
	}

	private void getDailyQuest(final ListView listView) {
		Call<List<Quest>> dailyQuestCall = apiInterface.getDailyQuest(User.Companion.getInstance(getActivity()).getId());
		dailyQuestCall.enqueue(new Callback<List<Quest>>() {
			@Override
			public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
				List<Quest> quests = response.body();

				if (quests != null && quests.size() > 0)
					listView.setAdapter(new QuestArrayAdapter(getContext(), quests));
			}

			@Override
			public void onFailure(Call<List<Quest>> call, Throwable t) {
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
				call.cancel();
			}
		});
	}
}