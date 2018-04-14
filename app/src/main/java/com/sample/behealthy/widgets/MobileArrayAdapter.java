package com.sample.behealthy.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.behealthy.R;
import com.sample.behealthy.models.Coupon;

import java.util.List;

public class MobileArrayAdapter extends ArrayAdapter<Coupon> {

	private final Context context;
	private final List<Coupon> values;

	public MobileArrayAdapter(Context context, List<Coupon> values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView textView = rowView.findViewById(R.id.firstLine);
		TextView secondLine = rowView.findViewById(R.id.secondLine);
		ImageView imageView = rowView.findViewById(R.id.icon);

		textView.setText(values.get(position).getTitle());
		secondLine.setText(Integer.toString(values.get(position).getGold()));
		imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.coin));


		return rowView;
	}
}