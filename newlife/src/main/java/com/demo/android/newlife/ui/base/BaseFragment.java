package com.demo.android.newlife.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.android.newlife.MyApplication;
import com.demo.android.newlife.R;
import com.demo.android.newlife.utils.MyLogger;
import com.demo.android.newlife.utils.UmengUtils;

import butterknife.ButterKnife;

/**
 * Created by XiongRun on 2017/5/8.
 */

public abstract class BaseFragment<T extends BasePresent<V>,V> extends Fragment {

	protected View rootView;
	protected MyLogger logger;
	protected String TAG ;
	protected T mPresenter;
	private SwipeRefreshLayout mRefreshLayout;
	protected boolean mIsRequestDataRefresh = false;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		logger = MyLogger.kLog();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			mPresenter = getPresenter();
			mPresenter.attachView((V)this);

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null){
			rootView = inflater.inflate(createViewLayoutId(),container,false);
			TAG=this.getClass().getSimpleName();
			ButterKnife.bind(this, rootView);

			initView(rootView);
			initToolbar();
			initData();
			setupSwipeRefresh(rootView);
		}else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (null != parent) {
				parent.removeView(rootView);
			}
		}
		return rootView;
	}

	protected abstract void initData();

	protected abstract void initToolbar();

	protected abstract void initView(View rootView);

	public T getPresenter() {
		return null;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPresenter != null){
			mPresenter.dettachView();
		}
	}

	public abstract int createViewLayoutId() ;

	private void setupSwipeRefresh(View view){
		mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
		if(mRefreshLayout != null){
			mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
					R.color.refresh_progress_2, R.color.refresh_progress_3);
			mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics()));
			mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					requestDataRefresh();
				}
			});
		}
	}

	public void requestDataRefresh() {
		mIsRequestDataRefresh = true;
	}

	public void setRefresh(boolean requestDataRefresh) {

		if (mRefreshLayout == null) {
			return;
		}
		if (!requestDataRefresh) {
			mIsRequestDataRefresh = false;
			mRefreshLayout.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (mRefreshLayout != null) {
						mRefreshLayout.setRefreshing(false);
					}
				}
			},1000);
		} else {
			mRefreshLayout.setRefreshing(true);
		}
	}

	public Boolean isSetRefresh(){
		return true;
	}


	@Override
	public void onPause() {
		super.onPause();
		UmengUtils.onPauseToFragment(MyApplication.getAppContext());
	}

	@Override
	public void onResume() {
		super.onResume();
		UmengUtils.onResumeToFragment(MyApplication.getAppContext());
	}
}
