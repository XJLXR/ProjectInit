package com.demo.android.newlife.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by XiongRun on 2017/5/14.
 */
public class ViewPagerFgAdapter extends FragmentPagerAdapter {

	private List<Fragment> mList;

	public ViewPagerFgAdapter(FragmentManager fm,List<Fragment> mList) {
		super(fm);
		this.mList = mList;
	}

	@Override
	public Fragment getItem(int position) {
		return mList.get(position);
	}

	@Override
	public int getCount() {
		return mList==null?0:mList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {


		switch (position) {
			case 0:
				return "热门";
			case 1:
				return "搞笑";
			case 2:
				return "动漫";

			case 3:
				return "综艺";

			default:
				break;
		}

		return null;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//super.destroyItem(container, position, object);
	}
}
