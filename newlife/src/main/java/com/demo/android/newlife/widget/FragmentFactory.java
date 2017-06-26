package com.demo.android.newlife.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.fragment.FragmentGank;
import com.demo.android.newlife.ui.fragment.FragmentNew;
import com.demo.android.newlife.ui.fragment.FragmentVideo;
import com.demo.android.newlife.ui.fragment.FragmentWx;

/**
 * Created by XiongRun on 2017/5/8.
 */

public class FragmentFactory extends FragmentPagerAdapter {

	private Context mContext;
	private static int NUM = 4;

	public FragmentFactory(FragmentManager fm,Context mContext) {
		super(fm);
		this.mContext = mContext;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {

			case R.id.drawer_gank:
				fragment=new FragmentGank();
				break;

			case R.id.drawer_wechat:
				fragment=new FragmentWx();
				break;

			case R.id.drawer_news:
				fragment=new FragmentNew();
				break;

			case R.id.drawer_video:
				fragment=new FragmentVideo();
				break;

			default:
				break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return NUM;
	}
}
