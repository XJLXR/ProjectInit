package com.demo.android.projectinit.activity;

import android.view.View;
import android.widget.Button;

import com.demo.android.projectinit.R;
import com.demo.android.projectinit.base.BaseActivity;
import com.demo.android.projectinit.utils.ToastShow;

import static com.demo.android.projectinit.MyApplication.mContext;

public class MainActivity extends BaseActivity implements View.OnClickListener {


	private Button news;
	private Button video;

	@Override
	protected void initToolBar() {

	}

	@Override
	protected void initView() {
		news = (Button) findViewById(R.id.news);
		news.setOnClickListener(this);
		video = (Button) findViewById(R.id.video);
		video.setOnClickListener(this);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected View getLoadingTargetView() {
		return null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.news:
				readyGo(WXNewsActivity.class);
				break;

			case R.id.video:
				readyGo(VideoActivity.class);
				break;

			default:
				break;

		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		_exit();
	}

	long mExitTime=0;

	/**
	 * 退出
	 */
	private void _exit() {

		if (System.currentTimeMillis() - mExitTime > 2000) {
			ToastShow.getInstance(mContext).toastShow("再按一次退出程序");
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}
}
