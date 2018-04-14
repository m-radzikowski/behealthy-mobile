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
import com.sample.behealthy.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends FragmentActivity {

	EditText usernameET;
	EditText passwordET;

	APIInterface apiInterface;

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

				// TODO:
				// some basic null checks etc

				login(username, password);
			}
		});
	}

	private void login(String username, String password) {
		Call<User> chestOpenCall = apiInterface.getUser(username, password);
		chestOpenCall.enqueue(new Callback<User>() {
			@Override
			public void onResponse(Call<User> call, Response<User> response) {
				if (response.body() != null) {
					onSuccessfulLogin(response.body());
				} else {
					onFailedLogin("jakis tam");
				}
			}

			@Override
			public void onFailure(Call<User> call, Throwable t) {
				call.cancel();
				onFailedLogin(t.getMessage());
			}
		});
	}

	private void onSuccessfulLogin(User user) {
		User.Companion.setInitialUser(user);

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void onFailedLogin(String reason) {
		Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
	}
}
