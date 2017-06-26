package com.demo.android.newlife.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.base.BaseFragment;
import com.demo.android.newlife.ui.present.WxFgPresent;
import com.demo.android.newlife.ui.view.IWxFgView;

import butterknife.BindView;

/**
 * Created by XiongRun on 2017/5/8.
 */

public class FragmentWx extends BaseFragment<WxFgPresent, IWxFgView> implements IWxFgView {


	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	@BindView(R.id.main_content)
	CoordinatorLayout mainContent;

	private LinearLayoutManager layoutManager;

	@Override
	protected void initToolbar() {

	}

	@Override
	public WxFgPresent getPresenter() {

		return new WxFgPresent(getContext());
	}

	@Override
	protected void initView(View rootView) {
		layoutManager = new LinearLayoutManager(getContext());
		recycler.setLayoutManager(layoutManager);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setDataRefresh(true);
		mPresenter.getNewsLate();
		mPresenter.scrollRecycleView();
	}

	@Override
	public void requestDataRefresh() {
		super.requestDataRefresh();
		setDataRefresh(true);
		mPresenter.getNewsLate();
	}

	@Override
	public int createViewLayoutId() {
		return R.layout.fragment_wx;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void setDataRefresh(Boolean refresh) {
		setRefresh(refresh);
	}

	@Override
	public RecyclerView getRecyclerView() {
		return recycler;
	}

	@Override
	public LinearLayoutManager getLayoutManager() {
		return layoutManager;
	}

}
