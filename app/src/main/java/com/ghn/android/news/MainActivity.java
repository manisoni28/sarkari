package com.ghn.android.news;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;


import com.ghn.android.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

	MaterialTabHost tabHost;
	ViewPager pager;
	ViewPagerAdapter adapter;
	private String[] tabs = {"LATEST","ALL NEWS","MY FAVORITES"};
	Toolbar toolbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
		pager = (ViewPager) this.findViewById(R.id.pager );
		adapter = new ViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);


		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// when user do a swipe the selected tab change
				tabHost.setSelectedNavigationItem(position);

			}
		});

		for(String tab_name:tabs)
		{
			tabHost.addTab(
					tabHost.newTab()
					.setText(tab_name)
					.setTabListener(MainActivity.this)
					);
		}

	}

	@Override
	public void onTabSelected(MaterialTab tab) {
		// TODO Auto-generated method stub
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);

		}

		public Fragment getItem(int num) {

			switch (num) {
			case 0:
				return new LatestFragment();
			case 1:
				return new AllNewsFragment();
			case 2:
				return new FavoriteFragment();

			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

	}
	 
	 
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
//
//
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch (item.getItemId()) 
//		{
//		case R.id.menu_about:
//
//			Intent about=new Intent(getApplicationContext(),AboutActivity.class);
//			startActivity(about);
//
//			return true;
//
//		case R.id.menu_overflow:
//			return true;
//
//		case R.id.menu_moreapp:
//
//			startActivity(new Intent(
//					Intent.ACTION_VIEW,
//					Uri.parse(getString(R.string.play_more_apps))));
//
//			return true;
//
//		case R.id.menu_rateapp:
//
//			final String appName = getPackageName();//your application package name i.e play store application url
//			try {
//				startActivity(new Intent(Intent.ACTION_VIEW,
//						Uri.parse("market://details?id="
//								+ appName)));
//			} catch (android.content.ActivityNotFoundException anfe) {
//				startActivity(new Intent(
//						Intent.ACTION_VIEW,
//						Uri.parse("http://play.google.com/store/apps/details?id="
//								+ appName)));
//			}
//			return true;
//
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//}
}
