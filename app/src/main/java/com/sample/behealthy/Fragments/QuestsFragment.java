package com.sample.behealthy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.models.Mission;
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.widget.MobileArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestsFragment extends Fragment {

	APIInterface apiInterface;
	private AlertDialog waitingDialog;

	private ListView list ;
	private ArrayAdapter<String> adapter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
		apiInterface = APIClient.getClient().create(APIInterface.class);
		final ListView listview = (ListView) rootView.findViewById(R.id.listView1);




		Quest quest = new Quest("jakis","opis", "typ1", new Date(2018,4,14),12, true);
		Quest quest1 = new Quest("jakis","opis", "typ1", new Date(2018,4,14),12, true);
		Quest quest2 = new Quest("jakis","opis", "typ1", new Date(2018,4,14),12, true);
		List<Quest> quests = new ArrayList<>();
		quests.add(quest);
		quests.add(quest1);
		quests.add(quest2);


		listview.setAdapter(new MobileArrayAdapter(getContext(), quests));
		//showDownloadingDataDialog();
		Call<Mission> call2 = apiInterface.getMissions();
		call2.enqueue(new Callback<Mission>() {
			@Override
			public void onResponse(Call<Mission> call, Response<Mission> response) {
				Mission userList = response.body();


			}

			@Override
			public void onFailure(Call<Mission> call, Throwable t) {
				call.cancel();
			}
		});


		return rootView;
	}
	/*protected void showDownloadingDataDialog() {
		waitingDialog = new ProgressDialogBuilder(getContext())
			.setMessage(R.string.downloading)
			.setCancelable(false)
			.setPositiveButton(R.string.abort_downloading, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			})
			.create();
		waitingDialog.show();
	}*/
}