package com.demo.android.newlife.ui.model;

import com.demo.android.newlife.bean.Meizhi;
import com.demo.android.newlife.http.HttpMethod;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/5/12.
 */

public class GankFgModel{

	public Observable<Meizhi> getMeiZhiData(int page ){
		return HttpMethod.getInstance().getGankApiServer().getMeizhiData(page)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
