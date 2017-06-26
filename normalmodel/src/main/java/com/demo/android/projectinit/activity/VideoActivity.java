package com.demo.android.projectinit.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.demo.android.projectinit.R;
import com.demo.android.projectinit.adapter.VideoAdapter;
import com.demo.android.projectinit.base.BaseActivity;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class VideoActivity extends BaseActivity {

	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	@BindView(R.id.main_content)
	CoordinatorLayout mainContent;
	private LinearLayoutManager manager;
	private VideoAdapter adapter;


	@Override
	protected void initToolBar() {

	}

	@Override
	protected void initView() {
		manager = new LinearLayoutManager(this);
		recycler.setLayoutManager(manager);
		recycler.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
		recycler.getItemAnimator().setAddDuration(800);
		adapter = new VideoAdapter(this);
		recycler.setAdapter(adapter);
	}

	@Override
	protected void initData() {
		loadData();
	}

	private void loadData() {

	}

	@Override
	protected int getContentView() {
		return R.layout.activity_video;
	}

	@Override
	protected View getLoadingTargetView() {
		return null;
	}


	@Override
	public void onBackPressed() {
		if (JCVideoPlayer.backPress()) {
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		JCVideoPlayer.releaseAllVideos();
	}

}
