package com.sample.behealthy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.sample.behealthy.Fragments.HeroFragment;
import com.sample.behealthy.Fragments.QuestsFragment;
import com.sample.behealthy.Fragments.ShopFragment;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.models.SyncData;
import com.sample.behealthy.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity {

	public static int HERO_FRAGMENT_NUMBER = 1;

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	PageIndicatorView pageIndicatorView;
	SwipeRefreshLayout mySwipeRefreshLayout;
	ViewPager mViewPager;
	APIInterface apiInterface;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Setting up retrofit
		apiInterface = APIClient.getClient().create(APIInterface.class);

		// Setting up view
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setCurrentItem(HERO_FRAGMENT_NUMBER);

		mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
		mySwipeRefreshLayout.setOnRefreshListener(
			new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					refreshUserData();
				}
			}
		);

		pageIndicatorView = findViewById(R.id.pageIndicatorView);
		pageIndicatorView.setCount(3);
		pageIndicatorView.setSelection(1);
		pageIndicatorView.setViewPager(mViewPager);
	}

	private void refreshUserData() {
		Call<SyncData> syncDataCall = apiInterface.getSyncData(User.Companion.getInstance(getApplication()).getId());
		syncDataCall.enqueue(new Callback<SyncData>() {
			@Override
			public void onResponse(Call<SyncData> call, Response<SyncData> response) {
				// TODO
				// show response data and update views
				mySwipeRefreshLayout.setRefreshing(false);

				// TODO:
				// add eventbuss event

				//((ShopFragment)mAppSectionsPagerAdapter.getItem(0)).update(getApplicationContext());
				//((HeroFragment)mAppSectionsPagerAdapter.getItem(1)).update(getApplicationContext());
				//((QuestsFragment)mAppSectionsPagerAdapter.getItem(2)).update();

				Toast.makeText(getApplication(), "Sync succeded", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(Call<SyncData> call, Throwable t) {
				call.cancel();
				mySwipeRefreshLayout.setRefreshing(false);
				Toast.makeText(getApplication(), "Sync failed", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
		AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
				case 0:
					return new ShopFragment();
				case 1:
					return new HeroFragment();
				case 2:
					return new QuestsFragment();
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Sklep";
				case 1:
					return "Bohater";
				case 2:
					return "Misje";
				default:
					return "";
			}
		}
	}
}
