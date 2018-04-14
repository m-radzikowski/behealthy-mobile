package com.sample.behealthy.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.behealthy.Dialogs.RewardDialog;
import com.sample.behealthy.Dialogs.ShopDialog;
import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {

	final String REWARD_DIALOGTAG = "reward_dialog";
	final String SHOP_DIALOGTAG = "shop_dialog";

	LinearLayout goldLL;
	TextView goldTV;
	ImageView chestIV;

	APIInterface apiInterface;

	private boolean chestOpened = false;

	@SuppressLint("SetTextI18n")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

		// Setting up retrofit
		apiInterface = APIClient.getClient().create(APIInterface.class);

		// Setting up views
		goldLL = rootView.findViewById(R.id.gold_layout);
		goldLL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onGoldLayoutClick();
			}
		});

		goldTV = rootView.findViewById(R.id.gold_amount);
		goldTV.setText(Integer.toString(User.Companion.getInstance(getActivity()).getGold()));

		chestIV = rootView.findViewById(R.id.chest_icon);
		chestIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onChestClick();
			}
		});

		// TODO:
		// add checking if user has any chests
		// if not set chest to open state

		return rootView;
	}

	private void updateChestState(boolean state) {
		chestOpened = state;
		chestIV.setImageDrawable(getResources().getDrawable(chestOpened ?
			R.drawable.chest_open : R.drawable.chest_closed));

	}

	private void onGoldLayoutClick() {
		DialogFragment newFragment = new ShopDialog();
		newFragment.show(getFragmentManager(), SHOP_DIALOGTAG);
	}

	private void showRewardDialog() {
		DialogFragment newFragment = new RewardDialog();
		newFragment.show(getFragmentManager(), REWARD_DIALOGTAG);

		getFragmentManager().executePendingTransactions();
		newFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				updateChestState(false);
			}
		});
	}

	private void onChestClick() {
		if (chestOpened) {
			Toast.makeText(getContext(), "All chests already opened", Toast.LENGTH_SHORT).show();
			return;
		}

		updateChestState(true);

		// TODO:
		// add progressbar and add some delay to show functionality?

		Call<User> chestOpenCall = apiInterface.getChestReward();
		chestOpenCall.enqueue(new Callback<User>() {
			@Override
			public void onResponse(Call<User> call, Response<User> response) {
				Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
				showRewardDialog();

				// TODO:
				// add checking if user has any chests
				// if yes set chest to closed state
			}

			@Override
			public void onFailure(Call<User> call, Throwable t) {
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
				call.cancel();

				// we failed to open chest so the option should be available
				updateChestState(false);
			}
		});
	}
}
