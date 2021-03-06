package com.sample.behealthy.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.sample.behealthy.R;

public class RewardDialog extends DialogFragment {

	public static final String GOLD_REWARD_KEY = "gold_reward";

	TextView rewardTV;
	int goldReward;

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.dialog_reward, container, false);

		rewardTV = root.findViewById(R.id.gold_reward);
		rewardTV.setText(String.format("%d   sztuk   zlota", goldReward));

		return root;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		goldReward = getArguments().getInt(GOLD_REWARD_KEY, -1);
	}
}
