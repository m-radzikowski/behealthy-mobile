package com.sample.behealthy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rd.PageIndicatorView;
import com.sample.behealthy.Fragments.HeroFragment;
import com.sample.behealthy.Fragments.QuestsFragment;
import com.sample.behealthy.Fragments.ShopFragment;

public class MainActivity extends FragmentActivity {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	PageIndicatorView pageIndicatorView;
	ViewPager mViewPager;
	public static int HERO_FRAGMENT_NUMBER = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// TODO:
				//actionBar.setSelectedNavigationItem(position);
			}
		});
		mViewPager.setCurrentItem(HERO_FRAGMENT_NUMBER);

		pageIndicatorView = findViewById(R.id.pageIndicatorView);
		pageIndicatorView.setCount(3);
		pageIndicatorView.setSelection(1);
		pageIndicatorView.setViewPager(mViewPager);
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
