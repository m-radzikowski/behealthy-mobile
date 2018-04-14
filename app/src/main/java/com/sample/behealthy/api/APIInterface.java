package com.sample.behealthy.api;


import com.sample.behealthy.models.Mission;
import com.sample.behealthy.models.Quest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

	@GET("/api/quests")
	Call<Quest> getQuests();

	@GET("/api/missions")
	Call<Mission> getMissions();

	@POST("/user/1/chest/open")
	Call<String> getChestReward();
}