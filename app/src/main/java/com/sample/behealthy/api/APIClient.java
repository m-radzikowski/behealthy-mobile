package com.sample.behealthy.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

	public static Retrofit getClient() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		return new Retrofit.Builder()
			.baseUrl("http://10.239.232.170")
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build();
	}

}