package com.demo.android.newlife.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.android.newlife.R;
import com.demo.android.newlife.bean.WXNews;
import com.demo.android.newlife.ui.base.BaseActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;




/**
 * Created by XiongRun on 2017/5/12.
 */

public class WxDetailActivity extends BaseActivity {

	@BindView(R.id.backdrop)
	ImageView backdrop;
	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.appbar)
	AppBarLayout appbar;
	@BindView(R.id.wv_tech_content)
	WebView wvTechContent;
	@BindView(R.id.collapsing)
	CollapsingToolbarLayout collapsing;
	private WXNews.NewslistBean wxNews;
	private WebSettings webView;
	private String url;

	@Override
	protected void initView() {

		wxNews = (WXNews.NewslistBean) getIntent().getSerializableExtra("WxNews");
		url = wxNews.getUrl();
	}

	@Override
	protected void initToolbar() {
		toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
		toolbar.setTitle(wxNews.getTitle());

		setSupportActionBar(toolbar);
		Glide.with(mContext)
				.load(wxNews.getPicUrl())
				.asBitmap()
				.into(backdrop);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void initData() {
		webView = wvTechContent.getSettings();
		webView.setJavaScriptEnabled(true);

		wvTechContent.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		wvTechContent.loadUrl(url);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_wx_detail;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wvTechContent.canGoBack()) {
			wvTechContent.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
