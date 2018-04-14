package com.sample.behealthy.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.behealthy.R;
import com.sample.behealthy.models.Quest;

import java.util.List;

public class MobileArrayAdapter extends ArrayAdapter<Quest> {
	private final Context context;
	private final List<Quest> values;

	public MobileArrayAdapter(Context context, List<Quest> values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
		TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		for (Quest value: values) {
			textView.setText(value.getTitle());
			secondLine.setText(value.getDescription());
			if (value.getDone()){
			imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.coin));
			}
		}

		return rowView;
	}
}