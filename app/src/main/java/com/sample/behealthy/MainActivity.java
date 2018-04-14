package com.sample.behealthy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.sample.behealthy.fragments.HeroFragment;
import com.sample.behealthy.fragments.QuestsFragment;
import com.sample.behealthy.fragments.ShopFragment;
import com.sample.behealthy.api.APIClient;
import com.sample.behealthy.api.APIInterface;
import com.sample.behealthy.events.UpdateEvent;
import com.sample.behealthy.models.SyncData;
import com.sample.behealthy.models.User;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity {

	static final int HERO_FRAGMENT_NUMBER = 1;
	static final int SHOP_FRAGMENT_NUMBER = 0;
	static final int QUEST_FRAGMENT_NUMBER = 2;

	static final int NB_OF_VIEWS = 3;

	AppSectionsPagerAdapter sectionsPagerAdapter;
	PageIndicatorView pageIndicatorView;
	SwipeRefreshLayout swipeRefreshLayout;
	ViewPager viewPager;
	APIInterface apiInterface;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Setting up retrofit
		apiInterface = APIClient.getClient().create(APIInterface.class);

		// Setting up view
		sectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		viewPager = findViewById(R.id.pager);
		viewPager.setAdapter(sectionsPagerAdapter);
		viewPager.setCurrentItem(HERO_FRAGMENT_NUMBER);

		swipeRefreshLayout = findViewById(R.id.swiperefresh);
		swipeRefreshLayout.setOnRefreshListener(
			new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					refreshUserData();
				}
			}
		);

		pageIndicatorView = findViewById(R.id.pageIndicatorView);
		pageIndicatorView.setCount(NB_OF_VIEWS);
		pageIndicatorView.setSelection(HERO_FRAGMENT_NUMBER);
		pageIndicatorView.setViewPager(viewPager);
	}

	private void refreshUserData() {
		Call<SyncData> syncDataCall = apiInterface.getSyncData(User.Companion.getInstance(getApplication()).getId());
		syncDataCall.enqueue(new Callback<SyncData>() {
			@Override
			public void onResponse(Call<SyncData> call, Response<SyncData> response) {
				swipeRefreshLayout.setRefreshing(false);
				Toast.makeText(getApplication(), "Sync succeded", Toast.LENGTH_SHORT).show();

				User.Companion.setInitialUser(response.body().getUser());
				EventBus.getDefault().post(new UpdateEvent());
			}

			@Override
			public void onFailure(Call<SyncData> call, Throwable t) {
				call.cancel();
				swipeRefreshLayout.setRefreshing(false);
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
				case SHOP_FRAGMENT_NUMBER:
					return new ShopFragment();
				case HERO_FRAGMENT_NUMBER:
					return new HeroFragment();
				case QUEST_FRAGMENT_NUMBER:
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
				case SHOP_FRAGMENT_NUMBER:
					return "Sklep";
				case HERO_FRAGMENT_NUMBER:
					return "Bohater";
				case QUEST_FRAGMENT_NUMBER:
					return "Misje";
				default:
					return "";
			}
		}
	}
}
