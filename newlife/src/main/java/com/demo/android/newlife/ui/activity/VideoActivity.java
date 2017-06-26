package com.demo.android.newlife.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.base.BaseActivity;
import com.demo.android.newlife.ui.fragment.FragmentComic;
import com.demo.android.newlife.ui.fragment.FragmentHot;
import com.demo.android.newlife.ui.fragment.FragmentJok;
import com.demo.android.newlife.ui.fragment.FragmentTvShow;
import com.demo.android.newlife.utils.ToastShow;
import com.demo.android.newlife.widget.ViewPagerFgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by XiongRun on 2017/5/11.
 */

public class VideoActivity extends BaseActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tabs)
	TabLayout tabs;
	@BindView(R.id.appbar)
	AppBarLayout appbar;
	@BindView(R.id.viewpager)
	ViewPager viewpager;
	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;

	private List<Fragment> mList;

	@Override
	protected void initView() {
		mList = new ArrayList<>();
		mList.add(new FragmentHot());
		mList.add(new FragmentJok());
		mList.add(new FragmentComic());
		mList.add(new FragmentTvShow());
		viewpager.setOffscreenPageLimit(3);
		viewpager.setAdapter(new ViewPagerFgAdapter(getSupportFragmentManager(),mList));
		tabs.setupWithViewPager(viewpager);
	}

	@Override
	protected void initToolbar() {
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		toolbarTitle.setText("视频直播");
	}

	@Override
	protected void initData() {

	}

	@Override
	public int getLayoutId() {
		return R.layout.actvity_video;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.video_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.today){
			ToastShow.getInstance(mContext).toastShow("today");
			return true;
		}else if(item.getItemId() == R.id.about_me){
			ToastShow.getInstance(mContext).toastShow("关于我");
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}


}
