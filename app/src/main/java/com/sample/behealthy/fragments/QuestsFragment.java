package com.sample.behealthy.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestsFragment extends Fragment {

	APIInterface apiInterface;
	ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_quests, container, false);
		apiInterface = APIClient.getClient().create(APIInterface.class);
		listview = rootView.findViewById(R.id.listView1);

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		getDailyQuest(listview);
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