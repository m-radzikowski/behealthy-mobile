package com.sample.behealthy.api;


import com.sample.behealthy.models.Coupon;
import com.sample.behealthy.models.Gold;
import com.sample.behealthy.models.Mission;
import com.sample.behealthy.models.Quest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

	@GET("/api/quests")
	Call<Quest> getQuests();

	@GET("/user/1/quest/daily")
	Call<List<Quest>> getDailyQuest();

	@GET("/api/missions")
	Call<Mission> getMissions();

	@POST("/user/1/chest/open")
	Call<Gold> getChestReward();

	@GET("/user/1/coupon")
	Call<List<Coupon>> getCoupons();
}