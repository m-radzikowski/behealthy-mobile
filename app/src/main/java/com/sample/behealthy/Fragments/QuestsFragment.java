package com.sample.behealthy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.models.Mission;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestsFragment extends Fragment {

	APIInterface apiInterface;
	private AlertDialog waitingDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
		Bundle args = getArguments();
		((TextView) rootView.findViewById(android.R.id.text1)).setText(
			getString(R.string.quest_section));
		apiInterface = APIClient.getClient().create(APIInterface.class);

		//showDownloadingDataDialog();
		Call<Mission> call2 = apiInterface.getMissions();
		call2.enqueue(new Callback<Mission>() {
			@Override
			public void onResponse(Call<Mission> call, Response<Mission> response) {

				Mission userList = response.body();

				Toast.makeText(getContext(),"aaa", Toast.LENGTH_SHORT).show();

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