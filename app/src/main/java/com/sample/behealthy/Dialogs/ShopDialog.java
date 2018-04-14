package com.sample.behealthy.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.behealthy.R;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.models.Coupon;
import com.sample.behealthy.widget.MobileArrayAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDialog extends DialogFragment {
	APIInterface apiInterface;
	ListView listView;
	ProgressBar progressBar;
	TextView textView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_shop, container, false);
		listView =  rootView.findViewById(R.id.couponsListView);
		progressBar = rootView.findViewById(R.id.progressBar);
		textView = rootView.findViewById(R.id.text);
		// Inflate the layout to use as dialog or embedded fragment
		return rootView;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		apiInterface = APIClient.getClient().create(APIInterface.class);

		return dialog;
		/*
		// Build the dialog and set up the button click handlers
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});

		return builder.create();
		*/
	}

	@Override
	public void onStart() {
		super.onStart();
		Call<List<Coupon>> call2 = apiInterface.getAvailableCoupons();
		call2.enqueue(new Callback<List<Coupon>>() {
			@Override
			public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {

				final List<Coupon> coupons = response.body();
				listView.setAdapter(new MobileArrayAdapter(getContext(), coupons));
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
					{
						Call<Coupon> buyCoupon = apiInterface.buyCoupon(coupons.get(position).getId());
						buyCoupon.enqueue(new Callback<Coupon>() {
							@Override
							public void onResponse(Call<Coupon> call, Response<Coupon> response) {
								Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

							}

							@Override
							public void onFailure(Call<Coupon> call, Throwable t) {
								Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
								call.cancel();
							}
						});
					}
				});
				textView.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);

			}

			@Override
			public void onFailure(Call<List<Coupon>> call, Throwable t) {
				call.cancel();
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

			}
		});
	}
}
