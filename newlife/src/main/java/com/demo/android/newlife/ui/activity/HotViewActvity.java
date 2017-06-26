package com.demo.android.newlife.ui.activity;


import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.base.BaseActivity;

import butterknife.BindView;
import media.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by XiongRun on 2017/5/14.
 */

public class HotViewActvity extends BaseActivity {


	@BindView(R.id.video_view)
	IjkVideoView videoView;
	private boolean mBackPressed;

	@Override
	protected void initView() {
		String addr = getIntent().getStringExtra("addr");
		logger.d(addr);
		IjkMediaPlayer.loadLibrariesOnce(null);
		IjkMediaPlayer.native_profileBegin("libijkplayer.so");
		if (addr != null) {
			videoView.setVideoPath(addr);
		}
		videoView.start();
	}

	@Override
	public void onBackPressed() {
		mBackPressed = true;
		super.onBackPressed();
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (mBackPressed || !videoView.isBackgroundPlayEnabled()) {
			videoView.stopPlayback();
			videoView.release(true);
			videoView.stopBackgroundPlay();
		} else {
			videoView.enterBackground();
		}
		IjkMediaPlayer.native_profileEnd();
	}


	@Override
	protected void initToolbar() {

	}

	@Override
	protected void initData() {

	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_hot_view;
	}


}
