package com.sample.behealthy.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sample.behealthy.Dialogs.ShopDialog;
import com.sample.behealthy.R;

public class ShopFragment extends Fragment {

	LinearLayout goldLL;
	ImageView chestIV;

	private boolean chestOpened = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

		goldLL = rootView.findViewById(R.id.gold_layout);
		goldLL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onGoldLayoutClick();
			}
		});

		chestIV = rootView.findViewById(R.id.chest_icon);
		chestIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onChestClick();
			}
		});

		return rootView;
	}

	private void onGoldLayoutClick() {
		DialogFragment newFragment = new ShopDialog();
		newFragment.show(getFragmentManager(),"ShopDialog");
	}

	private void onChestClick() {
		chestOpened = !chestOpened;
		chestIV.setImageDrawable(getResources().getDrawable(chestOpened ?
			R.drawable.chest_open : R.drawable.chest_closed));
	}
}
