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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TipOfTheDayDialog extends DialogFragment {

	public static final String GOLD_REWARD_KEY = "gold_reward";

	TextView tipTV;

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.dialog_tip_of_the_day, container, false);

		tipTV = root.findViewById(R.id.tip);
		Random rand = new Random();
		int number = rand.nextInt(10 );

		List<String> list = new ArrayList<String>();
		list.add("Jedz    Jarmuż ,     będziesz     wielki!");
		list.add("drugi tip");
		list.add("3 tip");
		list.add("4 tip");
		list.add("5 tip");
		list.add("6 tip");
		list.add("7 tip");
		list.add("8 tip");
		list.add("9 tip");
		list.add("10 tip");
		tipTV.setText(list.get(number));

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

	}
}
