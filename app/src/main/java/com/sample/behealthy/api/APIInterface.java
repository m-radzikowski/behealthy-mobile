package com.sample.behealthy.api;


import com.sample.behealthy.models.Mission;
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

	@GET("/api/quests")
	Call<Quest> getQuests();

	@GET("/api/missions")
	Call<Mission> getMissions();

	@POST("/user/1/chest/open")
	Call<User> getChestReward();

	@Headers("Content-Type: application/json")
	@GET("/login")
	Call<User> getUser(@Query("login") String username, @Query("password") String password);
}