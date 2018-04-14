package com.sample.behealthy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.widget.MobileArrayAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestsFragment extends Fragment {

	APIInterface apiInterface;
	private AlertDialog waitingDialog;
	ListView listview;

	private ListView list ;
	private ArrayAdapter<String> adapter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
		apiInterface = APIClient.getClient().create(APIInterface.class);
		listview = (ListView) rootView.findViewById(R.id.listView1);

		//showDownloadingDataDialog();

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		getDailyQuest(listview);
	}

	private void getDailyQuest(final ListView listView){
		Call<List<Quest>> call2 = apiInterface.getDailyQuest();
		call2.enqueue(new Callback<List<Quest>>() {
			@Override
			public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
				//String title = response.body().getTitle();
				List<Quest> quests = response.body();
				listView.setAdapter(new MobileArrayAdapter(getContext(), quests));
				Toast.makeText(getContext(), "cos " , Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onFailure(Call<List<Quest>> call, Throwable t) {
				call.cancel();
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

			}
		});
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