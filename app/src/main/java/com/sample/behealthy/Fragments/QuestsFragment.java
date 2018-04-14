package com.sample.behealthy.Fragments;


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
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.widget.QuestArrayAdapter;

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
	}

	private void getDailyQuest(final ListView listView) {
		Call<List<Quest>> dailQuestCall = apiInterface.getDailyQuest();
		dailQuestCall.enqueue(new Callback<List<Quest>>() {
			@Override
			public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
				List<Quest> quests = response.body();
				listView.setAdapter(new QuestArrayAdapter(getContext(), quests));
				//listView.notifyAll();
			}

			@Override
			public void onFailure(Call<List<Quest>> call, Throwable t) {
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
				call.cancel();
			}
		});
	}
}