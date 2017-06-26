package com.demo.android.mvpmodel.activity;

import android.view.View;
import android.widget.Button;

import com.demo.android.mvpmodel.R;
import com.demo.android.mvpmodel.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

	@BindView(R.id.btn)
	Button btn;

	@Override
	protected View getLoadingTargetView() {
		return null;
	}

	@Override
	protected void initToolBar() {

	}

	@Override
	protected void initView() {
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				readyGo(WXNewsActivity.class);
			}
		});
	}

	@Override
	protected void initData() {

	}

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

}
