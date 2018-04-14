package com.sample.behealthy.api;


import com.sample.behealthy.models.Coupon;
import com.sample.behealthy.models.Gold;
import com.sample.behealthy.models.Mission;
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

	@GET("/api/quests")
	Call<Quest> getQuests();

	@GET("/user/1/quest/daily")
	Call<List<Quest>> getDailyQuest();

	@GET("/api/missions")
	Call<Mission> getMissions();

	@Headers({"Accept: application/json"})
	@POST("/user/1/chest/open")
	Call<Gold> getChestReward();

	@Headers({"Accept: application/json"})
	@GET("/user/1/coupon/available")
	Call<List<Coupon>> getAvailableCoupons();

	@Headers({"Accept: application/json"})
	@GET("/user/1/coupon/my")
	Call<List<Coupon>> getMyCoupons();

	@Headers({"Accept: application/json"})
	@POST("/user/1/coupon/{id}/buy")
	Call<Coupon> buyCoupon(@Path("id") Integer couponId);

	@Headers("Content-Type: application/json")
	@GET("/login")
	Call<User> getUser(@Query("login") String username, @Query("password") String password);
}