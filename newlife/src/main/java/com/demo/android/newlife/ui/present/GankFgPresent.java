package com.demo.android.newlife.ui.present;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.android.newlife.bean.Meizhi;
import com.demo.android.newlife.ui.adapter.GankAdapter;
import com.demo.android.newlife.ui.base.BasePresent;
import com.demo.android.newlife.ui.model.GankFgModel;
import com.demo.android.newlife.ui.view.IGankFgView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by XiongRun on 2017/5/12.
 */

public class GankFgPresent extends BasePresent<IGankFgView> {

	private Context context;
	private IGankFgView gankFgView;
	private GridLayoutManager layoutManager;
	private RecyclerView recyclerView;
	private final GankFgModel gankFgModel;
	private int page = 1;
	private int lastVisibleItem;
	private boolean isLoadMore = false; // 是否加载过更多
	private List<Meizhi.ResultsBean> mData;
	private GankAdapter adapter;

	public GankFgPresent(Context mContext){
		this.context =mContext;
		gankFgModel = new GankFgModel();
		mData = new ArrayList<>();
	}

	public void getData(){
		gankFgView = getRefVew();
		if (gankFgView != null){
			layoutManager = gankFgView.getLayoutManager();
			recyclerView = gankFgView.getRecyclerView();

			if (isLoadMore){
				page+=1;
			}

			gankFgModel.getMeiZhiData(page).subscribe(new Observer<Meizhi>() {
				@Override
				public void onSubscribe(Disposable d) {

				}

				@Override
				public void onNext(Meizhi value) {
					displayMeizhi(context, value.getResults(), gankFgView, recyclerView);
				}

				@Override
				public void onError(Throwable e) {
					gankFgView.setDataRefresh(false);
					e.printStackTrace();
				}

				@Override
				public void onComplete() {
					gankFgView.setDataRefresh(false);
				}
			});
		}
	}

	private void displayMeizhi(Context context, List<Meizhi.ResultsBean> results, IGankFgView gankFgView, RecyclerView recyclerView) {

		if (!isLoadMore){
			mData.addAll(results);
			adapter = new GankAdapter(context,mData);
			recyclerView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}else {
			if (results == null){
				gankFgView.setDataRefresh(false);
				return;
			}else {
				mData.addAll(results);
			}
			adapter.notifyDataSetChanged();
		}
	}


	public void scrollRecycleView() {
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem + 1 == layoutManager.getItemCount()) {
					gankFgView.setDataRefresh(true);
					isLoadMore = true;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							getData();
						}
					},1000);

				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = layoutManager.findLastVisibleItemPosition();
			}
		});
	}
}
