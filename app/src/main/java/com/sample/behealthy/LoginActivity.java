package com.sample.behealthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.dialogs.ProgressDialog;
import com.sample.behealthy.models.SyncData;
import com.sample.behealthy.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends FragmentActivity {

	EditText usernameET;
	EditText passwordET;

	APIInterface apiInterface;

	ProgressDialog barProgressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Setting up retrofit
		apiInterface = APIClient.getClient().create(APIInterface.class);

		// Setting up view
		usernameET = findViewById(R.id.username_edit);
		passwordET = findViewById(R.id.password_edit);

		Button loginBN = findViewById(R.id.login_button);
		loginBN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameET.getText().toString();
				String password = passwordET.getText().toString();

				barProgressDialog = new ProgressDialog();
				barProgressDialog.show(getSupportFragmentManager(), "tag");

				// TODO:
				// some basic null checks etc
				login(username, password);
			}
		});
	}

	private void login(String username, String password) {
		Call<SyncData> loginCall = apiInterface.getUser(username, password);
		loginCall.enqueue(new Callback<SyncData>() {
			@Override
			public void onResponse(Call<SyncData> call, Response<SyncData> response) {
				if (barProgressDialog != null && barProgressDialog.isVisible()) {
					barProgressDialog.dismiss();
				}

				if (response.body() != null) {
					onSuccessfulLogin(response.body().getUser());
				} else {
					onFailedLogin("Logowanie nieudane. Sprawdź dane i spróbuj ponownie.");
				}
			}

			@Override
			public void onFailure(Call<SyncData> call, Throwable t) {
				call.cancel();
				onFailedLogin("Logowanie nieudane. Sprawdź połączenie z internetem i spróbuj ponownie.");
			}
		});
	}

	private void onSuccessfulLogin(User user) {
		if (user == null)
			return;

		User.Companion.setInitialUser(user);

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void onFailedLogin(String reason) {
		Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
	}
}
