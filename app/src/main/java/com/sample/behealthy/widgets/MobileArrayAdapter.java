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

import org.w3c.dom.Text;

import java.util.List;

public class MobileArrayAdapter extends ArrayAdapter<Coupon> {

	private final Context context;
	private final List<Coupon> values;

	public MobileArrayAdapter(Context context, List<Coupon> values) {
		super(context, R.layout.list_coupon, values);
		this.context = context;
		this.values = values;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Coupon coupon = values.get(position);

		View rowView = inflater.inflate(R.layout.list_coupon, parent, false);
		TextView textView = rowView.findViewById(R.id.firstLine);
		TextView secondLine = rowView.findViewById(R.id.secondLine);
		TextView thirdLine = rowView.findViewById(R.id.thirdLine);
		ImageView imageView = rowView.findViewById(R.id.icon);

		textView.setText(coupon.getTitle());
		secondLine.setText(coupon.getDescription());
		thirdLine.setText(Integer.toString(coupon.getGold()));

		switch (coupon.getType()) {
			case "FOOD":
				imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.cabage));
				break;
			case "DRINK":
				imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.juice));
				break;
			case "SPORT":
				imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.weights));
				break;
		}

		return rowView;
	}
}