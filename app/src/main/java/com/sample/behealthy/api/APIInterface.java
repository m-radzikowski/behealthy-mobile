package com.sample.behealthy.api;

import com.sample.behealthy.models.Coupon;
import com.sample.behealthy.models.Gold;
import com.sample.behealthy.models.Quest;
import com.sample.behealthy.models.SyncData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

	@GET("/user/{u_id}/quest/daily")
	Call<List<Quest>> getDailyQuest(@Path("u_id") Integer userId);

	@Headers({"Accept: application/json"})
	@POST("/user/{u_id}/chest/open")
	Call<Gold> getChestReward(@Path("u_id") Integer userId);

	@Headers({"Accept: application/json"})
	@GET("/user/{u_id}/coupon/available")
	Call<List<Coupon>> getAvailableCoupons(@Path("u_id") Integer userId);

	@Headers({"Accept: application/json"})
	@GET("/user/{u_id}/coupon/my")
	Call<List<Coupon>> getMyCoupons(@Path("u_id") Integer userId);

	@Headers({"Accept: application/json"})
	@POST("/user/{u_id}/coupon/{id}/buy")
	Call<Coupon> buyCoupon(@Path("id") Integer couponId, @Path("u_id") Integer userId);

	@Headers({"Accept: application/json"})
	@GET("/login")
	Call<SyncData> getUser(@Query("login") String username, @Query("password") String password);

	@Headers({"Accept: application/json"})
	@GET("/user/{u_id}/sync")
	Call<SyncData> getSyncData(@Path("u_id") Integer userId);
}