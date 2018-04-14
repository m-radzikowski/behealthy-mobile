package com.sample.behealthy.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.behealthy.R;
import com.sample.behealthy.models.Quest;

import java.util.List;

public class QuestArrayAdapter extends ArrayAdapter<Quest> {
	private final Context context;
	private final List<Quest> values;

	public QuestArrayAdapter(Context context, List<Quest> values) {
		super(context, R.layout.list_quest, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_quest, parent, false);
		TextView textView = rowView.findViewById(R.id.firstLine);
		TextView secondLine = rowView.findViewById(R.id.secondLine);
		TextView valueView = rowView.findViewById(R.id.value);
		TextView stateView = rowView.findViewById(R.id.state);
		ImageView imageView = rowView.findViewById(R.id.icon);

		for (Quest value : values) {
			textView.setText(value.getTitle());
			secondLine.setText(value.getDescription());
			valueView.setText("Target KM: " + Integer.toString(value.getValue()));

			switch (value.getType()) {
				case "BIKE":
					imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.bike));
					break;
				case "RUN":
					imageView.setImageDrawable(rowView.getResources().getDrawable(R.drawable.run));
					break;
			}

			if (value.getDone()) {
				stateView.setText("ZADANIE WYKONANE");
				stateView.setTextColor(Color.rgb(65, 152, 67));
			} else {
				stateView.setText("W TRAKCIE WYKONYWANIA");
				stateView.setTextColor(Color.rgb(255, 0, 0));
			}
		}
		return rowView;
	}
}