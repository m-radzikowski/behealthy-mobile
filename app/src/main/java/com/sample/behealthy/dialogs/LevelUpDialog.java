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
import com.sample.behealthy.models.User;

public class LevelUpDialog extends DialogFragment {

	public static final String EXP_GAINED_KEY = "exp_gained";
	public static final String LVL_GAINED_KEY = "lvl_gained";

	int gainedLvl;
	int gainedExp;

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.dialog_lvlup, container, false);

		TextView rewardTV = root.findViewById(R.id.reward);
		rewardTV.setText(String.format("zdobyles   %d   exp   i    awansowales    na     %d    poziom", gainedExp, User.Companion.getInstance(getActivity()).getLvl()));

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

		gainedLvl = getArguments().getInt(LVL_GAINED_KEY, -1);
		gainedExp = getArguments().getInt(EXP_GAINED_KEY, -1);
	}
}
