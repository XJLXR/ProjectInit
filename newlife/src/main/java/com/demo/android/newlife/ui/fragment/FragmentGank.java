package com.demo.android.newlife.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.base.BaseFragment;
import com.demo.android.newlife.ui.present.GankFgPresent;
import com.demo.android.newlife.ui.view.IGankFgView;

import butterknife.BindView;

/**
 * Created by XiongRun on 2017/5/8.
 */

public class FragmentGank extends BaseFragment<GankFgPresent, IGankFgView> implements IGankFgView {


	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	private GridLayoutManager layoutManager;

	@Override
	protected void initData() {

	}

	@Override
	protected void initToolbar() {

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setDataRefresh(true);
		mPresenter.getData();
		mPresenter.scrollRecycleView();
	}

	@Override
	public void requestDataRefresh() {
		super.requestDataRefresh();
		setDataRefresh(true);
		mPresenter.getData();
	}

	@Override
	protected void initView(View rootView) {
		layoutManager = new GridLayoutManager(getContext(),2);
		recycler.setLayoutManager(layoutManager);
	}

	@Override
	public int createViewLayoutId() {
		return R.layout.fragment_gank;
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
	public GridLayoutManager getLayoutManager() {
		return layoutManager;
	}

	@Override
	public GankFgPresent getPresenter() {

		return new GankFgPresent(getContext());
	}



	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
