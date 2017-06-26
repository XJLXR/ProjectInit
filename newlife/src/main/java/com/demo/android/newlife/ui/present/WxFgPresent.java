package com.demo.android.newlife.ui.present;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.android.newlife.api.WXApiServer;
import com.demo.android.newlife.bean.WXNews;
import com.demo.android.newlife.ui.adapter.WxFgAdapter;
import com.demo.android.newlife.ui.base.BasePresent;
import com.demo.android.newlife.ui.model.WxFgModel;
import com.demo.android.newlife.ui.view.IWxFgView;
import com.demo.android.newlife.utils.ToastShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class WxFgPresent extends BasePresent<IWxFgView> {

	private WxFgModel wxFgModel;
	private IWxFgView iWxFgView;
	private List<WXNews.NewslistBean> mData = null;
	private Context mContext;
	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private int pageSize=10;
	private int pageIndex=1;
	private int lastVisibleItem;
	private boolean isLoadMore = false; // 是否加载过更多
	private WxFgAdapter adapter;
	private Map<String, String> map;
	private ProgressDialog dialog;

	public WxFgPresent(Context c){
		wxFgModel = new WxFgModel();
		this.mContext = c;
		mData = new ArrayList<>();
		dialog = new ProgressDialog(mContext);
	}

	public void getNewsLate(){
		iWxFgView = getRefVew();
		if (iWxFgView != null){
			recyclerView = iWxFgView.getRecyclerView();
			layoutManager = iWxFgView.getLayoutManager();
			if(isLoadMore){
				pageIndex = pageIndex +1;
			}
			map = new HashMap<>();
			map.put("key", WXApiServer.IPIKEY);
			map.put("num",pageSize+"");
			map.put("page",pageIndex+"");

			wxFgModel.getNewsList(map).subscribe(new Observer<WXNews>() {
				Disposable disposable;
				@Override
				public void onSubscribe(Disposable d) {
					disposable =d;
				}

				@Override
				public void onNext(WXNews value) {
					loadData(mContext,value.getNewslist(),iWxFgView,recyclerView);
				}

				@Override
				public void onError(Throwable e) {
					e.printStackTrace();
					iWxFgView.setDataRefresh(false);
					ToastShow.getInstance(mContext).toastShow("没有网络...");
				}

				@Override
				public void onComplete() {
					iWxFgView.setDataRefresh(false);

				}
			});
		}

	}

	private void loadData(Context mContext, List<WXNews.NewslistBean> newslist, IWxFgView iWxFgView, RecyclerView recyclerView) {
		if (!isLoadMore){
			mData.addAll(newslist);
			adapter = new WxFgAdapter(mContext,mData);
			recyclerView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}else {
			if (newslist ==null){
				iWxFgView.setDataRefresh(false);
				return;
			}else {
				mData.addAll(newslist);
			}
		}
		adapter.notifyDataSetChanged();
		iWxFgView.setDataRefresh(false);
	}

	public void scrollRecycleView(){
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if(adapter.getItemCount()>0){
					if (newState==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1==adapter.getItemCount()){
						isLoadMore =true;
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								getNewsLate();
							}
						},2000);

					}
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
