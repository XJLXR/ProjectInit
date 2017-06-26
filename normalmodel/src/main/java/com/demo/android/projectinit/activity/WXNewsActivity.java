package com.demo.android.projectinit.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.blankj.utilcode.util.LogUtils;
import com.demo.android.projectinit.R;
import com.demo.android.projectinit.adapter.WxNewsAdapter;
import com.demo.android.projectinit.api.WXApiServer;
import com.demo.android.projectinit.base.BaseActivity;
import com.demo.android.projectinit.bean.WXNews;
import com.demo.android.projectinit.http.HttpMethod;
import com.demo.android.projectinit.utils.NetWorkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class WXNewsActivity extends BaseActivity {

	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	@BindView(R.id.main_content)
	CoordinatorLayout mainContent;
	private LinearLayoutManager manager;
	private int pageSize = 20;
	private int pageIndex = 10;
	private Map<String, String> map;
	private WxNewsAdapter adapter;
	private List<WXNews.NewslistBean> newslist;
	private List<WXNews.NewslistBean> mData = null;

	@Override
	protected void initToolBar() {

	}

	@Override
	protected void initView() {
		mData = new ArrayList<>();
		manager = new LinearLayoutManager(this);
		recycler.setLayoutManager(manager);
		recycler.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
		recycler.getItemAnimator().setAddDuration(800);
		adapter = new WxNewsAdapter(this, R.layout.item_wx_news, mData);
		recycler.setAdapter(adapter);
	}

	@Override
	protected void initData() {
		prepareData();
	}

	private void prepareData() {
		toggleShowLoading(true, null);
		if (!NetWorkUtil.isAvailable(this)) {
			showNetWorkError();
			toggleNetworkError(true, prepareDataClickListener);
		}

		map = new HashMap<>();
		map.put("key", WXApiServer.IPIKEY);
		map.put("num", pageSize + "");
		map.put("page", pageIndex + "");

		HttpMethod.getInstance().getWxApiServer().getNewsList(map)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<WXNews>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(WXNews value) {

						newslist = value.getNewslist();
						LogUtils.i(newslist);
						if (newslist.size() == 0) {
							toggleShowEmpty(true, "数据为空", null);
						} else {

							mData.addAll(newslist);
							adapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onError(Throwable e) {
						e.printStackTrace();
						//showToast("网络数据加载错误");
						toggleShowError(true, "重新加载", prepareDataClickListener);
					}

					@Override
					public void onComplete() {
						toggleShowLoading(false, null);
					}
				});


	}


	@Override
	protected int getContentView() {
		return R.layout.activity_wx_news;
	}

	@Override
	protected View getLoadingTargetView() {
		return mainContent;
	}

	private View.OnClickListener prepareDataClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			prepareData();
		}
	};

}
