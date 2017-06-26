package com.demo.android.mvpmodel.presenter;

import android.view.View;

import com.demo.android.mvpmodel.api.WXApiServer;
import com.demo.android.mvpmodel.base.BasePresent;
import com.demo.android.mvpmodel.bean.WXNews;
import com.demo.android.mvpmodel.model.WxModel;
import com.demo.android.mvpmodel.view.IWxAtyView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by XiongRun on 2017/5/22.
 */

public class WXPresenter extends BasePresent<IWxAtyView> {

	private WxModel wxModel ;
	private Map<String, String> map;
	private int pageSize=20;
	private int pageIndex=1;



	public WXPresenter() {
		this.wxModel = new WxModel();
		map = new HashMap<>();
		map.put("key", WXApiServer.IPIKEY);
		map.put("num",pageSize+"");
		map.put("page",pageIndex+"");
	}

	public void getNewsList() {
		checkViewAttached();
		wxModel.getNewsList(map)
				.doOnSubscribe(new Consumer<Disposable>() {
					@Override
					public void accept(Disposable disposable) throws Exception {
						getRefVew().toggleShowLoading(true,null);
					}
				})
				.subscribe(new Observer<WXNews>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(WXNews value) {
						if (value.getCode()==200){
							getRefVew().showDataSuccess(value);
						}else {
							getRefVew().showDataError(value.getMsg(),value.getCode());
						}
					}

					@Override
					public void onError(Throwable e) {
						getRefVew().toggleNetworkError(true,prepareDataClickListener);
					}

					@Override
					public void onComplete() {
						getRefVew().toggleShowLoading(false,null);
					}
				});
	}

	private View.OnClickListener prepareDataClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			getNewsList();
		}
	};
}
