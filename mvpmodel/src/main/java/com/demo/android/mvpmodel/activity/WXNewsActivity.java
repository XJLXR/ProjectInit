package com.demo.android.mvpmodel.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.android.mvpmodel.R;
import com.demo.android.mvpmodel.adapter.WxNewsAdapter;
import com.demo.android.mvpmodel.base.BaseActivity;
import com.demo.android.mvpmodel.bean.WXNews;
import com.demo.android.mvpmodel.presenter.WXPresenter;
import com.demo.android.mvpmodel.view.IWxAtyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by XiongRun on 2017/5/15.
 */

public class WXNewsActivity extends BaseActivity<WXPresenter,WXNews> implements IWxAtyView  {
	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	@BindView(R.id.main_content)
	CoordinatorLayout mainContent;


	private List<WXNews.NewslistBean> newslist;
	private List<WXNews.NewslistBean> mData;
	private WxNewsAdapter adapter;

	@Override
	public WXPresenter getPresenter() {
		return new WXPresenter();
	}

	@Override
	protected View getLoadingTargetView() {
		return mainContent;
	}

	@Override
	protected void initToolBar() {

	}

	@Override
	protected void initView() {
		mPresenter.attachView(this);
		mData = new ArrayList<>();
		adapter = new WxNewsAdapter(mContext, R.layout.item_wx_news,mData);
		LinearLayoutManager manager=new LinearLayoutManager(this);
		recycler.setLayoutManager(manager);
		recycler.setAdapter(adapter);


	}

	@Override
	protected void initData() {
		prepareData();
	}


	/**
	 * 初始化页面数据
	 */
	private void prepareData() {

		mPresenter.getNewsList();
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_wx_news;
	}

	@Override
	public void showDataSuccess(WXNews datas) {
		super.showDataSuccess(datas);

		if (datas!= null){
			newslist = datas.getNewslist();
			mData.addAll(newslist);
		}
	}

	@Override
	public void onDestroy() {
		mPresenter.dettachView();
		super.onDestroy();
	}
}
