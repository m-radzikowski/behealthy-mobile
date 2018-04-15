package com.sample.behealthy.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.sample.behealthy.models.User;
import com.sample.behealthy.widgets.MobileArrayAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketDialog extends DialogFragment {

	APIInterface apiInterface;
	ListView listView;
	ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_shop, container, false);
		listView = rootView.findViewById(R.id.couponsListView);
		progressBar = rootView.findViewById(R.id.progressBar);

		return rootView;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		apiInterface = APIClient.getClient().create(APIInterface.class);
		return dialog;
	}

	@Override
	public void onStart() {
		super.onStart();

		Call<List<Coupon>> getCouponsCall = apiInterface.getMyCoupons(User.Companion.getInstance(getActivity()).getId());
		getCouponsCall.enqueue(new Callback<List<Coupon>>() {
			@Override
			public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
				couponsObtained(response.body());
			}

			@Override
			public void onFailure(Call<List<Coupon>> call, Throwable t) {
				call.cancel();
				Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void couponsObtained(final List<Coupon> coupons) {
		listView.setAdapter(new MobileArrayAdapter(getContext(), coupons));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				couponClicked(coupons.get(position));
			}
		});

		progressBar.setVisibility(View.GONE);
	}

	private void couponClicked(Coupon coupon) {
		Call<Coupon> buyCouponCall = apiInterface.buyCoupon(coupon.getId(), User.Companion.getInstance(getActivity()).getId());
		buyCouponCall.enqueue(new Callback<Coupon>() {
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
}
