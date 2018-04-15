package com.sample.behealthy.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.behealthy.dialogs.RewardDialog;
import com.sample.behealthy.dialogs.ShopDialog;
import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.dialogs.TicketDialog;
import com.sample.behealthy.events.UpdateEvent;
import com.sample.behealthy.models.Gold;
import com.sample.behealthy.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {

	final String REWARD_DIALOGTAG = "reward_dialog";
	final String SHOP_DIALOGTAG = "shop_dialog";
	final String TICKET_DIALOGTAG = "ticket_dialog";

	LinearLayout goldLL;
	TextView goldTV;
	ImageView chestIV;
	View chestView;
	TextView chestCountTV;
	ImageView ticketIV;

	APIInterface apiInterface;

	private boolean chestOpened = false;

	@SuppressLint("SetTextI18n")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

		// Setting up retrofit
		apiInterface = APIClient.getClient().create(APIInterface.class);

		// Setting up views
		goldLL = rootView.findViewById(R.id.gold_layout);
		ticketIV = rootView.findViewById(R.id.ticket_icon);

		goldLL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onGoldLayoutClick();
			}
		});

		ticketIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTicketClick();
			}
		});

		goldTV = rootView.findViewById(R.id.gold_amount);
		chestIV = rootView.findViewById(R.id.chest_icon);
		chestView = rootView.findViewById(R.id.chest_amount_shape);
		chestCountTV = rootView.findViewById(R.id.chest_amount);

		update();

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();

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

	@SuppressLint("SetTextI18n")
	public void update() {
		User user = User.Companion.getInstance(getActivity());

		goldTV.setText(Integer.toString(user.getGold()));
		chestCountTV.setText(Integer.toString(user.getAvailableChests()));

		if (user.getAvailableChests() > 0) {
			chestIV.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onChestClick();
				}
			});
			chestCountTV.setVisibility(View.VISIBLE);
			chestView.setVisibility(View.VISIBLE);
		} else {
			chestIV.setImageDrawable(getResources().getDrawable(R.drawable.no_chest));
			chestIV.setOnClickListener(null);
			chestCountTV.setVisibility(View.INVISIBLE);
			chestView.setVisibility(View.INVISIBLE);
		}
	}

	private void changeChestState(boolean state) {
		chestOpened = state;
		User user = User.Companion.getInstance(getContext());
		if (user.getAvailableChests() > 0) {
			chestIV.setImageDrawable(getResources().getDrawable(chestOpened ?
				R.drawable.chest_open : R.drawable.chest_closed));
		} else {
			chestIV.setImageDrawable(getResources().getDrawable(R.drawable.no_chest));
			chestIV.setOnClickListener(null);
		}
	}

	private void onGoldLayoutClick() {
		DialogFragment newFragment = new ShopDialog();
		newFragment.show(getFragmentManager(), SHOP_DIALOGTAG);
	}

	private void onTicketClick() {
		DialogFragment newFragment = new TicketDialog();
		newFragment.show(getFragmentManager(), TICKET_DIALOGTAG);
	}

	private void showRewardDialog(int gold) {
		Bundle args = new Bundle();
		args.putInt(RewardDialog.GOLD_REWARD_KEY, gold);

		DialogFragment newFragment = new RewardDialog();
		newFragment.setArguments(args);
		newFragment.show(getFragmentManager(), REWARD_DIALOGTAG);
	}

	private void onChestClick() {
		if (chestOpened) {
			Toast.makeText(getContext(), "All chests already opened", Toast.LENGTH_SHORT).show();
			return;
		}

		changeChestState(true);

		Call<Gold> chestOpenCall = apiInterface.getChestReward(User.Companion.getInstance(getActivity()).getId());
		chestOpenCall.enqueue(new Callback<Gold>() {
			@Override
			public void onResponse(Call<Gold> call, Response<Gold> response) {
				User user = User.Companion.getInstance(getContext());
				Gold gold = response.body();

				if (gold != null) {
					//TODO
					// Brak monet, dorobić odpowiednie sprawdzanie
					showRewardDialog(gold.getGold());
					user.setGold(user.getGold() + gold.getGold());
					user.setAvailableChests(user.getAvailableChests() - 1);
				}
				changeChestState(false);
				update();
			}

			@Override
			public void onFailure(Call<Gold> call, Throwable t) {
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
				call.cancel();

				// we failed to open chest so the option should be available
				changeChestState(false);
			}
		});
	}
}
