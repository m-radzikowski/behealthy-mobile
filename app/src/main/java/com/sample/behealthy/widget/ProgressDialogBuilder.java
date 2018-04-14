package com.sample.behealthy.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.sample.behealthy.R;

public class ProgressDialogBuilder extends AlertDialog.Builder {

	private CharSequence message;

	public ProgressDialogBuilder(Context context) {
		super(context);
	}

	@SuppressWarnings("unused")
	public ProgressDialogBuilder(Context context, int theme) {
		super(context, theme);
	}

	@Override
	public AlertDialog.Builder setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public AlertDialog.Builder setMessage(int messageId) {
		this.message = getContext().getString(messageId);
		return this;
	}

	@Override
	public AlertDialog create() {
		AlertDialog dialog = super.create();

		@SuppressLint("InflateParams") View v = dialog.getLayoutInflater().inflate(R.layout.d_progress, null);
		((TextView) v.findViewById(android.R.id.text1)).setText(message);

		dialog.setView(v);
		return dialog;
	}
}